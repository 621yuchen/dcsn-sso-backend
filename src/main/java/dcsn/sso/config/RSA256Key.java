package dcsn.sso.config;

import java.io.Serializable;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author UASD-Zhangjiuwei
 * @className RSA256Key
 * @description  RSA256Key 实体类
 * @date 2024/3/6
 */
public class RSA256Key implements Serializable {


    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;


    public RSA256Key() {
    }

    public RSA256Key(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

}
