package dcsn.sso.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;


import dcsn.sso.config.Result;
import dcsn.sso.config.exception.ResultConstants;
import dcsn.sso.entity.OpenIdConfiguration;
import dcsn.sso.entity.TokenParam;
import dcsn.sso.entity.TokenResponse;
import dcsn.sso.entity.UserInfo;
import dcsn.sso.entity.common.CommonConstants;
import dcsn.sso.service.JwksService;
import dcsn.sso.service.TokenService;
import dcsn.sso.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.util.*;

/**
 * @author UASD-Zhangjiuwei
 * @className AuthController
 * @description
 * @date 2024/3/4
 */

@Slf4j
@RequestMapping("/api")
@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final Long EXPIRED_TIME = 3600000L;

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Value("${corp-id}")
    private String corpId;

    @Value("${corp-secret}")
    private String corpSecret;

    @Value("${redirect-uri}")
    private String redirectUri;

    @Value("${base-uri}")
    private String baseUri;

    @Value("${weixinOauth2-url}")
    private String weixinOauth2Url;

    @Value("${login-type}")
    private String loginType;

    @Value("${agent-id}")
    private String agentId;

    @Value("${getToken-url}")
    private String getTokenUrl;

    @Value("${getUserinfo-url}")
    private String getUserinfoUrl;

    @RequestMapping(value = "/oauth2/token", method = RequestMethod.POST)
    public Object oauth2Token(TokenParam tokenParam) {
        logger.info("=== oauth2Token Start!");
        logger.info("=== oauth2Token tokenParam: " + tokenParam.toString());
        if (!StringUtils.hasText(tokenParam.getClient_id()) || !StringUtils.hasText(tokenParam.getClient_secret())) {
            return Result.error(ResultConstants.ERR100002,"clientid和clientcecret不能为空");
        }
        if (!clientId.equals(tokenParam.getClient_id()) || !clientSecret.equals(tokenParam.getClient_secret())) {
            return Result.error(ResultConstants.ERR100003,"用户身份认证失败");
        }
        logger.info("=== oauth2Token 1!");

        // 获取用户信息
        UserInfo userInfo = getUser(tokenParam.getCode());

        if (!"0".equals(userInfo.getErrCode())) {
            return Result.error(userInfo.getErrCode(),userInfo.getErrMsg());
        }
        logger.info("=== oauth2Token 2!");

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setId_token(TokenService.getIdToken(userInfo,clientId))
                .setAccess_token(TokenService.getAccessToken(userInfo,clientId))
                .setScope("openid")
                .setRefresh_token(UUID.randomUUID().toString())
                .setToken_type("Bearer")
                .setExpires_in(EXPIRED_TIME);
        logger.info("=== oauth2Token End!");
        return tokenResponse;
    }

    /**
     * 保留接口
     * @param token
     * @decription 目前没有用到
     * @return
     */
    @PostMapping("/userinfo")
    public UserInfo getUserInfo(String token){
        DecodedJWT decodedJWT = TokenService.deToken(token);
        String subject = decodedJWT.getSubject();
        UserInfo user = new UserInfo();
        user.setUserId(subject);
        return user;
    }


    @GetMapping ("/oauth2/authorize")
    public Object redirect(@RequestParam String client_id, @RequestParam String redirect_uri,
                                 @RequestParam String response_type,
                           @RequestParam(value = "scope", defaultValue = "openid") String scope,
                            @RequestParam String state) throws Exception{
        if (!StringUtils.hasText(client_id)) {
            return Result.error(ResultConstants.ERR100004,"client_id不能为空");
        }
        if (!StringUtils.hasText(redirect_uri)) {
            return Result.error(ResultConstants.ERR100005,"redirect_uri不能为空");
        }
        if (!StringUtils.hasText(response_type)) {
            return Result.error(ResultConstants.ERR100006,"response_type不能为空");
        }

        if (!clientId.equals(client_id)) {
            return Result.error(ResultConstants.ERR100007,"client_id不合法");
        }
        String encodeUrl = URLEncoder.encode(redirect_uri, "UTF-8");
        if (!redirectUri.equals(redirect_uri) && !redirectUri.equals(encodeUrl)) {
            return Result.error(ResultConstants.ERR100008,"重定向的uri不符合要求");
        }
        // 重定向地址
//        String url = "https://login.work.weixin.qq.com/wwlogin/sso/login?login_type=CorpApp&appid=ww50cb7c2642dd00f9&redirect_uri=https%3A%2F%2Fdxctechnologyhongkonglimiteddemo4.service-now.com%2Fnavpage.do&agentid=1000060&state=" + state;
//        String url = weixinOauth2Url+"login_type="+loginType+"&appid="+corpId+"&redirect_uri="+encodeUrl+"&agentid="+agentId+"&state=" + state;

//        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+corpId+"&redirect_uri="+encodeUrl+"&response_type=code&scope=snsapi_privateinfo&state="+state+"&agentid="+agentId+"#wechat_redirect";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+corpId+"&redirect_uri="+baseUri+"/api/navpage"+"&response_type=code&scope=snsapi_privateinfo&state="+state+"&agentid="+agentId+"#wechat_redirect";
        return new RedirectView(url);
    }

    /**
     * 重定向
     * @param code
     * @return
     */
    @GetMapping ("/navpage")
    public Object navpage(@RequestParam String code ,@RequestParam String state){
        String url = redirectUri + "?code="+code +"&state="+state;
        return new RedirectView(url);
    }
    /**
     * jwks
     */
    @RequestMapping("/oauth2/jwks")
    public Map<String, Object> Jwks() {
        logger.info("==================jwks success ==========================");
        return JwksService.getJwks().toPublicJWKSet().toJSONObject();
    }

    @GetMapping("/oidc/redirect_url")
    public String getRedirectUrl(){
        return redirectUri;
    }


    @GetMapping("/oidc/.well-known/openid-configuration")
    public Object getConfiguration() {
        logger.info("=== getConfiguration Start!");

        OpenIdConfiguration openIdConfiguration = new OpenIdConfiguration();
        List<String> grantType = new ArrayList<>();
        grantType.add("authorization_code");
        List<String> tokenSignAlg = new ArrayList<>();
        tokenSignAlg.add("RS256");
        List<String> response = new ArrayList<>();
        response.add("code");
        logger.info("=== getConfiguration End!");

        return openIdConfiguration.setIssuer(baseUri)
                .setJwks_uri(baseUri + CommonConstants.JWKS_URI)
                .setAuthorization_endpoint(baseUri + CommonConstants.AUTHORIZATION_ENDPOINT)
                .setUserinfo_endpoint(baseUri + CommonConstants.USERINFO_ENDPOINT)
                .setToken_endpoint(baseUri + CommonConstants.TOKEN_ENDPOINT)
                .setGrant_types_supported(grantType)
                .setId_token_signing_alg_values_supported(tokenSignAlg)
                .setResponse_types_supported(response)
                .setScopes_supported(null);
    }


    /**
     * 通过code获取企业微信用户的userId
     * @param code
     * @return accessToken
     */
    private UserInfo getUser(String code) {

        UserInfo user = new UserInfo();
        //获取企业微信的AccessToken
        String accessTokenRes = HttpClientUtil.doGet(getTokenUrl+"corpid=" + corpId + "&corpsecret=" + corpSecret);
        JSONObject accessTokenJson = JSON.parseObject(accessTokenRes);
        if (!"0".equals(accessTokenJson.getString("errcode"))) {
            return user.setErrCode(accessTokenJson.getString("errcode")).setErrMsg(accessTokenJson.getString("errmsg"));
        }
        String access_token = accessTokenJson.getString("access_token");

        // 获取用户身份
        String userIdRes = HttpClientUtil.doGet(getUserinfoUrl+"access_token=" + access_token +"&code=" + code);
        JSONObject userIdJson = JSON.parseObject(userIdRes);
        if (!"0".equals(userIdJson.getString("errcode"))) {
            return user.setErrCode(userIdJson.getString("errcode")).setErrMsg(userIdJson.getString("errmsg"));
        }


        // 获取user_ticket
        String userTicket = userIdJson.getString("user_ticket");
        //根据user_ticket得到企业微信用户敏感信息
        Map<String,Object> map = new HashMap<>();
        map.put("user_ticket",userTicket);
        // 获取访问用户敏感信息
        String s = HttpClientUtil.doPost("https://qyapi.weixin.qq.com/cgi-bin/auth/getuserdetail?access_token=" + access_token, JSON.toJSONString(map));
//        UserInfo userInfo = JSON.parseObject(userIdRes, UserInfo.class);
        UserInfo userInfo = JSON.parseObject(s, UserInfo.class);
        return userInfo;
    }

}
