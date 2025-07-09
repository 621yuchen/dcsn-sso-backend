package dcsn.sso.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author UASD-Zhangjiuwei
 * @className OpenIdConfigurationModel
 * @description 服务发现
 * @date 2024/3/12
 */
@Data
@Accessors(chain = true)
public class OpenIdConfiguration {

    private String issuer;

    private String authorization_endpoint;

    private String token_endpoint;

    private String jwks_uri;

    private String userinfo_endpoint;

    private List<String> response_types_supported;

    private List<String> grant_types_supported;

    private List<String> id_token_signing_alg_values_supported;

    private String scopes_supported;

}
