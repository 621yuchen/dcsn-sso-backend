package dcsn.sso.entity;

import lombok.Data;

@Data
public class Jwks {
    private String kty;
    private String e;
    private String use;
    private String kid;
    private String alg;
    private String n;
}
