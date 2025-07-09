package dcsn.sso.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author UASD-Zhangjiuwei
 * @className TokenResponse
 * @description token返回值
 * @date 2024/3/7
 */
@Data
@Accessors(chain = true)
public class TokenResponse {


    private String access_token;

    private String refresh_token;

    private String scope;

    private String id_token;

    private String token_type;

    private Long expires_in;

}
