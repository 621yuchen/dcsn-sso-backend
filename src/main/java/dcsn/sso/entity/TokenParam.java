package dcsn.sso.entity;

import lombok.Data;

/**
 * @author UASD-Zhangjiuwei
 * @className A
 * @description
 * @date 2024/3/4
 */
@Data
public class TokenParam {

    /**
     * 固定为authorization_code
     */
    private String grant_type;

    /**
     * 授权回调地址, 必须和申请应用是填写的一致(参数部分可不一致)
     */
    private String redirect_uri;

    /**
     * 企业微信拿到的授权码
     */
    private String code;

    /**
     * 申请应用时分配的APPID
     */
    private String client_id;

    /**
     * 申请应用时分配的AppSecret
     */
    private String client_secret;

}
