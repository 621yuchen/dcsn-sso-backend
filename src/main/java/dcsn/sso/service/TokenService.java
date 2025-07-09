package dcsn.sso.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import dcsn.sso.config.RSA256Key;
import dcsn.sso.config.exception.ResultConstants;
import dcsn.sso.config.exception.SSOException;
import dcsn.sso.entity.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author UASD-Zhangjiuwei
 * @className TokenService
 * @description 生成access_token、id_token
 * @date 2024/3/12
 */
@Slf4j
@Service
public class TokenService {

    private static ResourceLoader resourceLoader;

    @Autowired
    public TokenService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    /**
     * 从文件中读取密钥对
     * @return
     */
    public static RSA256Key getRSAKey() {
        RSA256Key rsa256Key =null;
        Resource resource = resourceLoader.getResource("classpath:rsa256Key.ser");
        try (InputStream inputStream = resource.getInputStream();
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            rsa256Key = (RSA256Key) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SSOException(ResultConstants.ERR100001,"从文件中读取密钥对失败");
        }
        return rsa256Key;
    }
    /**
     * access_token
     * @param userInfo,clientId
     * @return
     */
    public static String getAccessToken(UserInfo userInfo, String clientId){
        // 读取 rsa256Key
        RSA256Key rsa256Key = getRSAKey();
        List<String> list = new ArrayList<>();
        list.add("openid");
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(userInfo.getUserId())
                .setIssuer("https://snowauth.chnds.com.cn")
                .claim("scope", list)
                .claim("userid",userInfo.getUserId())
                .claim("gender",userInfo.getGender())
                .claim("avatar",userInfo.getAvatar())
                .claim("qr_code",userInfo.getQrCode())
                .claim("mobile",userInfo.getMobile())
                .claim("email",userInfo.getEmail())
                .claim("biz_mail",userInfo.getBizMail())
                .claim("address",userInfo.getAddress())
                .setHeaderParam("kid", "max-rsa")
                .setAudience(clientId)
                .signWith(rsa256Key.getPrivateKey(), SignatureAlgorithm.RS256)
                .setNotBefore(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();
    }

    /**
     * id_token
     * @param userInfo,clientId
     * @return
     */
    public static String getIdToken(UserInfo userInfo,String clientId){
        // 读取 rsa256Key
        RSA256Key rsa256Key = getRSAKey();
        List<String> list = new ArrayList<>();
        list.add("openid");
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(userInfo.getUserId())
                .setIssuer("https://snowauth.chnds.com.cn")
                .claim("scope", list)
                .claim("userid",userInfo.getUserId())
                .claim("gender",userInfo.getGender())
                .claim("avatar",userInfo.getAvatar())
                .claim("qr_code",userInfo.getQrCode())
                .claim("mobile",userInfo.getMobile())
                .claim("email",userInfo.getEmail())
                .claim("biz_mail",userInfo.getBizMail())
                .claim("address",userInfo.getAddress())
                .setHeaderParam("kid", "max-rsa")
                .setAudience(clientId)
                .signWith(rsa256Key.getPrivateKey(), SignatureAlgorithm.RS256)
                .setNotBefore(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .compact();
    }

    /**
     * 解密
     * @param token
     * @description 暂时没用
     * @return
     */
    public static DecodedJWT deToken(String token){

        // 读取 rsa256Key
        RSA256Key rsa256Key = getRSAKey();
        Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPublicKey());
        DecodedJWT jwt = null;
        try {

            JWTVerifier verifier = JWT.require(algorithm).withIssuer("https://snowauth.chnds.com.cn").build();// Reusable

            jwt = verifier.verify(token);
        } catch (Exception exception) {
            throw new SSOException(ResultConstants.ERR100009,exception.getMessage());
        }
        return jwt;
    }

}
