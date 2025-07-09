package dcsn.sso.service;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import dcsn.sso.config.RSA256Key;
import dcsn.sso.config.exception.SSOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * @author UASD-Zhangjiuwei
 * @className JwksService
 * @description 生成Jwks
 * @date 2024/3/12
 */
@Slf4j
@Service
public class JwksService {

    public static JWKSet getJwks(){
        log.info("=== getJwks start!");

        RSA256Key rsa256Key = TokenService.getRSAKey();
        KeyUse keyUse = new KeyUse("sig");
        Algorithm algorithm = Algorithm.parse("RS256");

        RSAKey rsaKey = new RSAKey.Builder(rsa256Key.getPublicKey())
                .privateKey(rsa256Key.getPrivateKey())
                .keyID("max-rsa")
                .keyUse(keyUse)
                .algorithm(algorithm)
                .build();
        log.info("=== getJwks end!");

        return new JWKSet(rsaKey);
    }


    public static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();


        } catch (Exception ex) {
            throw new SSOException(ex.getMessage());
        }
        return keyPair;
    }

}
