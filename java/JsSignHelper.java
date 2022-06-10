import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.UUID;
import java.net.*;

// !用来根据url生成js签名信息的类
public class JsSignHelper {

    public static HashMap<String, String> getJsSignInfo(String appId, String urlString) {
        // !此值通过从redis中读取获取js_ticket
        // String jsTicket = "a4dcdk";
        String noncestr = UUID.randomUUID().toString(); // 此为随机生成的字符串,最长为128个字符串;
        // noncestr = "1234";
        long timestamp = System.currentTimeMillis() / 1000L;
        // timestamp = 1654850924;
        try {
            // !此为当前页面的url,为去除掉锚点和参数的部分,如果获取的url最后为/结尾,去除
            URL url = new URL(urlString);
            String protocol = url.getProtocol();
            String authority = url.getAuthority();
            String path = url.getPath();
            String pureUrlString = String.format("%s://%s%s", protocol, authority, path);
            if (pureUrlString.endsWith("/")) {
                // !如果最后为/结尾则去掉
                pureUrlString = pureUrlString.substring(0, pureUrlString.length() - 1);
            }
            String signString = String.format("js_ticket=%s&nonce_str=%s&timestamp=%d&url=%s",
                    jsTicket, noncestr, timestamp, pureUrlString);
            String sha1 = "";
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(signString.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
            HashMap<String, String> returnMap = new HashMap();
            returnMap.put("app_id", appId);
            returnMap.put("timestamp", String.format("%d", timestamp));
            returnMap.put("nonce_str", noncestr);
            returnMap.put("signature", sha1);
            return returnMap;
        } catch (Exception e) {
            return null;
        }
    }
    // !调试
    public static void main(String[] args) {
        String urlString = "https://yuanzhibang.com/a/b/?x=1&b=2#2"; //
        HashMap<String, String> jsSignInfo = HelloWorld.getJsSignInfo("100029", urlString);
    }
}