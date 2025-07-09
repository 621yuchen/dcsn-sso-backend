package dcsn.sso.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author xiong.yb
 */
public class SslUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class miTM implements TrustManager,X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

    /**
     *Ignore the SSL certificate requested by HTTPS. It must be called before opening Connection
     * @throws Exception
     */
    public static void ignoreSsl() throws Exception{
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String urlHostName, SSLSession session) {
                logger.debug("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

}