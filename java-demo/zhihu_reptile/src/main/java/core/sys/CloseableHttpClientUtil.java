package core.sys;

import com.sun.deploy.util.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class CloseableHttpClientUtil {

    @Bean("createHttpClient")
    public CloseableHttpClient createHttpClient(){
     /*   SSLContext sslContext = null;
        HostnameVerifier hostnameVerifier = ((s, sslSession) -> s.equals(sslSession.getPeerHost()));
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslsf).build();
        // 创建连接池管理器
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置默认的scoket参数
        manager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());*/
        // 路由市对MaxTotal细分
        // 每个路由实际最大连接数默认值是由DefaultMaxPerRoute控制
        // MaxPerRoute设置的过小，无法支持大并发：ConnectionPoolTimeoutExceptuon :timeoutwaiting for connection from pool
        // 默认的请求参数
        RequestConfig config = RequestConfig.custom().setConnectTimeout(2).setConnectionRequestTimeout(5)
                .build();
        return HttpClients.custom()/*.setConnectionManager(manager)*/.setConnectionManagerShared(false)
                .evictExpiredConnections().setConnectionTimeToLive(2, TimeUnit.SECONDS)
                .setConnectionReuseStrategy(DefaultClientConnectionReuseStrategy.INSTANCE)
                .setDefaultRequestConfig(config).evictIdleConnections(5,TimeUnit.SECONDS).build();
    }


    /**
     * @param sessionId         会话ID
     * @return CloseableHttpClient
     */
    private static CloseableHttpClient createHttpClient(String sessionId) {
        //忽略校验HTTPS服务器证书, 将hostname校验和CA证书校验同时关闭
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom()
                    .loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        //设置http和https协议对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        //构建请求配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2) //获取数据的超时时间
                .setConnectTimeout(2) //建立连接的超时时间
                .setConnectionRequestTimeout(2) //连接池获取到连接的超时时间
                .build();
        //创建自定义httpclient对象
        HttpClientBuilder builder = HttpClients.custom();
        builder.setDefaultRequestConfig(requestConfig); //设置默认请求配置
        builder.setConnectionManager(connectionManager); //设置连接管理器
        //设置自动重定向配置
        // builder.disableRedirectHandling();
        //配置CookieStore
        /*CookieStore cookieStore = new BasicCookieStore();
        builder.setDefaultCookieStore(cookieStore);*/
        CloseableHttpClient httpClient = builder.build();
        return httpClient;
    }
}
