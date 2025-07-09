package dcsn.sso.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author UASD-Zhangjiuwei
 * @className UserInfo
 * @description
 * @date 2024/3/12
 */
@Data
@Accessors(chain = true)

public class UserInfo {

    /**
     *返回码
     */
    private String errCode;

    /**
     *对返回码的文本描述内容
     */
    private String errMsg;

    /**
     *成员UserID
     */
    private String userId;

    /**
     *性别。0表示未定义，1表示男性，2表示女性。仅在用户同意snsapi_privateinfo授权时返回真实值，否则返回0.
     */
    private String gender;

    /**
     *头像url。仅在用户同意snsapi_privateinfo授权时返回真实头像，否则返回默认头像
     */
    private String avatar;

    /**
     *	员工个人二维码（扫描可添加为外部联系人），仅在用户同意snsapi_privateinfo授权时返回
     */
    private String qrCode;

    /**
     *手机，仅在用户同意snsapi_privateinfo授权时返回，第三方应用不可获取
     */
    private String mobile;

    /**
     *邮箱，仅在用户同意snsapi_privateinfo授权时返回，第三方应用不可获取
     */
    private String email;

    /**
     *企业邮箱，仅在用户同意snsapi_privateinfo授权时返回，第三方应用不可获取
     */
    private String bizMail;

    /**
     *仅在用户同意snsapi_privateinfo授权时返回，第三方应用不可获取
     */
    private String address;
}
