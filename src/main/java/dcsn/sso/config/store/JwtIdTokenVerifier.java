package dcsn.sso.config.store;



import dcsn.sso.config.RSA256Key;
import dcsn.sso.config.SecretKeyUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;

/**
 * 保留接口
 * 生成公钥私钥-保存文件
 */

public class JwtIdTokenVerifier {



    public static void main(String[] args) throws NoSuchAlgorithmException {
        RSA256Key rsa256Key = SecretKeyUtil.generateRSA256Key();
// 文件路径
        String filePath = "./rsa256Key.ser";

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            // 将 RSA256Key 对象写入文件
            objectOut.writeObject(rsa256Key);
            System.out.println("RSA256Key 已成功保存到文件：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }


//
//        // 读取 rsa256Key
//        String filePath = "./rsa256Key.ser";
//        RSA256Key rsa256Key =null;
//
//        try (FileInputStream fileIn = new FileInputStream(filePath);
//             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
//            // 从文件中读取 RSA256Key 对象
//            rsa256Key = (RSA256Key) objectIn.readObject();
//
//            // 现在你可以使用读取到的 RSA256Key 对象进行后续操作
//            System.out.println("成功从文件中读取 RSA256Key 对象：" + rsa256Key);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        List<String> list = new ArrayList<>();
//        list.add("openid");
//        list.add("message.read");
//        //加密时，使用私钥生成RS算法对象
//        Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPrivateKey());
//        Algorithm algorithm1 = Algorithm.RSA256(rsa256Key.getPublicKey());
//
//        String token = JWT.create()
//                //签发人
//                .withIssuer("https://passport-test.lotuscars.com.cn/api/oide1")
//                //接收者
//                .withAudience("admin")
//                //签发时间
//                .withIssuedAt(new Date())
//                .withSubject("admin")
//                .withKeyId("max-rsa")
//                //过期时间
//                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
//                .withIssuedAt(new Date(System.currentTimeMillis()))
//                .withNotBefore(new Date(System.currentTimeMillis()))
//                .withClaim("scope", list)
//                //签入
//                .sign(algorithm);
//
//        // 解密
//        DecodedJWT jwt = null;
//        try {
//
//            JWTVerifier verifier = JWT.require(algorithm1).withIssuer("https://passport-test.lotuscars.com.cn/api/oide1").build();// Reusable
//
//            jwt = verifier.verify(token);
//        } catch (JWTVerificationException exception) {
//            exception.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        System.out.println(jwt.getSubject());
//    }

    }
}