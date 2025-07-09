package dcsn.sso.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * @author UASD-CUIHONGDA
 * @className HttpClientUtil
 * @description
 * @date 2024/3/4
 */
@Slf4j
public class HttpClientUtil {

    public static final int CODE = 200;

    public static String doGet(String httpUrl) {
        return doGet(httpUrl,null);
    }

    public static String doGet(String httpUrl, Map<String, Object> headerMap) {
        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("================doGet=================");
        log.info("httpUrl:" + httpUrl);
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        // Return result string
        String result = null;
        try {
            // Create a remote URL connection object
            URL url = new URL(httpUrl);
            // Open a connection through a remote URL connection object and forcefully convert it to an HTTP URL Connection class
            connection = (HttpURLConnection) url.openConnection();
            // Set connection method: get
            connection.setRequestMethod("GET");
            // Set the timeout for connecting to the host server: 15000 milliseconds
            connection.setConnectTimeout(15000);
            // Set the time to read remotely returned data: 60000 milliseconds
            connection.setReadTimeout(60000);
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    connection.setRequestProperty(key, "" + headerMap.get(key));
                }
            }
            // 发送请求
            connection.connect();
            //Obtain input stream through connection connection
            if (connection.getResponseCode() == CODE) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            // Wrapping the input stream object: charset is set according to the requirements of the work project team
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String temp = null;
            StringBuffer sbf = new StringBuffer();
            // LoopThroughLineByLineToReadData
            while ((temp = br.readLine()) != null) {
                sbf.append(temp);
                sbf.append("\r\n");
            }
            result = sbf.toString();
        } catch (MalformedURLException e) {
            log.error("MalformedURLException error" + e.getMessage());
        } catch (IOException e) {
            log.error("IOException error" + e.getMessage());
        } catch (Exception e) {
            log.error("Exception error" + e.getMessage());
        } finally {
            // CLOSE RESOURCE
            close(br);
            close(is);
            // Close remote connection
            connection.disconnect();
        }
        log.debug("================result=================");
        log.info("================result=================" + result);
        return result;
    }

    public static String doPost(String httpUrl, String param) {
        return doPost(httpUrl, param, null);
    }


    public static String doPost(String httpUrl, String param, Map<String, Object> headerMap) {
        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("================doPost=================");
        log.info("httpUrl:" + httpUrl);
        log.info("param:" + param);
        log.info("headerMap:" + JSON.toJSONString(headerMap));
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            //Opening a connection through a remote URL connection object
            connection = (HttpURLConnection) url.openConnection();
            //Set connection request method
            connection.setRequestMethod("POST");
            // Set the timeout time for connecting to the host server: 15000 milliseconds
            connection.setConnectTimeout(15000);
            // Set the timeout for reading host server return data: 60000 milliseconds
            connection.setReadTimeout(60000);
            // The default value is: false. When sending data to a remote server for writing data, it needs to be set to true
            connection.setDoOutput(true);
            // The default value is: true. When reading data from a remote service, it is set to true, and this parameter can be optional
            connection.setDoInput(true);
            // Format the incoming parameters: The request parameters should be in the form of name1=value1&name2=value2.
            if (headerMap != null) {
                connection.setRequestProperty("Content-Type", "application/json");
                for (String key : headerMap.keySet()) {
                    connection.setRequestProperty(key, "" + headerMap.get(key));
                }
            } else {
                connection.setRequestProperty("Content-Type", "application/json");
            }

            // Obtain an output stream by connecting objects
            os = connection.getOutputStream();
            // Write parameters out and transmit them through the output stream object, which is written out through a byte array
            os.write(param.getBytes());
            // Obtain an input stream through a connection object and read it remotely
            if (connection.getResponseCode() == CODE) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            // Wrapping the input stream object: charset is set according to the requirements of the work project team
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String temp = null;
            StringBuffer sbf = new StringBuffer();
            // LoopThroughLineByLineToReadData
            while ((temp = br.readLine()) != null) {
                sbf.append(temp);
                sbf.append("\r\n");
            }
            result = sbf.toString();
        } catch (MalformedURLException e) {
            log.error("MalformedURLException error" + e.getMessage());
        } catch (IOException e) {
            log.error("IOException error" + e.getMessage());
        } catch (Exception e) {
            log.error("Exception error" + e.getMessage());
        } finally {
            // closeResource
            close(br);
            close(os);
            close(is);
            // Disconnect from remote address URL
            connection.disconnect();
        }
        log.debug("================result=================");
        log.info("================result=================" + result);
        return result;
    }

    public static String doPostApplication(String httpUrl, String param) {
        log.debug("================doPostApplication=================");
        log.info("httpUrl:" + httpUrl);
        log.info("param:" + param);
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            //Opening a connection through a remote URL connection object
            connection = (HttpURLConnection) url.openConnection();
            //Set connection request method
            connection.setRequestMethod("POST");
            // Set the timeout time for connecting to the host server: 15000 milliseconds
            connection.setConnectTimeout(15000);
            // Set the timeout for reading host server return data: 60000 milliseconds
            connection.setReadTimeout(60000);
            // The default value is: false. When sending data to a remote server for writing data, it needs to be set to true
            connection.setDoOutput(true);
            // The default value is: true. When reading data from a remote service, it is set to true, and this parameter can be optional
            connection.setDoInput(true);
            // Format the incoming parameters: The request parameters should be in the form of name1=value1&name2=value2.
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(param.getBytes());
            outputStream.flush();
            outputStream.close();

            // Obtain an output stream by connecting objects
            os = connection.getOutputStream();
            // Obtain an input stream through a connection object and read it remotely
            if (connection.getResponseCode() == CODE || connection.getResponseCode() == 201) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            // Wrapping the input stream object: charset is set according to the requirements of the work project team
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String temp = null;
            StringBuffer sbf = new StringBuffer();
            // LoopThroughLineByLineToReadData
            while ((temp = br.readLine()) != null) {
                sbf.append(temp);
                sbf.append("\r\n");
            }
            result = sbf.toString();
        } catch (MalformedURLException e) {
            log.error("MalformedURLException error" + e.getMessage());
        } catch (IOException e) {
            log.error("IOException error" + e.getMessage());
        } catch (Exception e) {
            log.error("Exception error" + e.getMessage());
        } finally {
            // closeResource
            close(br);
            close(os);
            close(is);
            // Disconnect from remote address URL
            connection.disconnect();
        }
        log.debug("================result=================");
        log.info("================result=================" + result);
        return result;
    }

    private static void close(Reader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                e.getMessage();
            }
        } else {
            reader = null;
        }
    }

    private static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.getMessage();
            }
        } else {
            closeable = null;
        }
    }
}

