package core.sys;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpCilentUtil {

    private static Header[] allHeaders;

    public static void main(String[] args) {
        HttpCilentUtil.initHeaders("https://www.zhihu.com");
        HttpCilentUtil.doGet("https://www.zhihu.com/api/v4/search_v3?t=general&q=%E8%A5%BF%E5%AE%89%E7%90%86%E5%B7%A5%E5%A4%A7%E5%AD%A6&correction=1&offset=0&limit=20&lc_idx=0&show_all_topics=0",null);
    }

    public static void initHeaders(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            HttpGet httpGet = new HttpGet(builder.build()); // 执行请求
            response = httpclient.execute(httpGet);
            allHeaders = response.getAllHeaders();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String doGet(String url, Map<String, String> param) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(builder.build()); // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream in = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder strber= new StringBuilder();
                String line = null;
                while((line = br.readLine())!=null){
                    strber.append(line+'\n');
                }
                br.close();
                in.close();
                resultString = strber.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}
