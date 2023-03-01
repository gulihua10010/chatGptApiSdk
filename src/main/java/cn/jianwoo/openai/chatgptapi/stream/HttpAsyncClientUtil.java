package cn.jianwoo.openai.chatgptapi.stream;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.CharBuffer;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import cn.jianwoo.openai.chatgptapi.exception.ApiException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HttpContext;

import lombok.extern.log4j.Log4j2;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-23 12:12
 */
@Log4j2
public class HttpAsyncClientUtil
{

    private static DefaultProxyRoutePlanner proxy(String hostOrIP, int port)
    {
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost(hostOrIP, port, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return (routePlanner);
    }


    private static SSLContext getSSLContext()
    {
        final TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        try
        {
            final SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy).build();
            sslContext.getServerSessionContext().setSessionCacheSize(1000);
            return sslContext;
        }
        catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e)
        {}
        return null;
    }


    private static Registry<SchemeIOSessionStrategy> getSSLRegistryAsync()
    {
        return RegistryBuilder.<SchemeIOSessionStrategy> create().register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(getSSLContext(), null, null,
                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                .build();
    }


    private static PoolingNHttpClientConnectionManager getPoolingNHttpClientConnectionManager()
    {
        try
        {
            final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(
                    new DefaultConnectingIOReactor(IOReactorConfig.DEFAULT), getSSLRegistryAsync());

            return connectionManager;
        }
        catch (IOReactorException e)
        {}
        return null;
    }


    private static RequestConfig getRequestConfig()
    {
        return RequestConfig.custom().setConnectTimeout(300000).setSocketTimeout(300000)
                .setConnectionRequestTimeout(60000).build();
    }


    public static CloseableHttpAsyncClient getHttpAsyncClient(Proxy proxy)
    {
        HttpAsyncClientBuilder builder = HttpAsyncClients.custom()
                .setConnectionManager(getPoolingNHttpClientConnectionManager());

        if (null != proxy)
        {
            InetSocketAddress address = (InetSocketAddress) proxy.address();
            builder = builder.setRoutePlanner(proxy(address.getHostName(), address.getPort()));
        }
        return builder.build();
    }


    public static void execute(CloseableHttpAsyncClient asyncClient, HttpUriRequest request, Callback<String> callback)
            throws ApiException
    {
        log.info(">>>>ApacheHttpClient.exec...");

        AsyncCharConsumer<HttpResponse> charConsumer = new AsyncCharConsumer<HttpResponse>() {
            HttpResponse response;

            protected void onCharReceived(CharBuffer buf, IOControl ioctrl) throws IOException
            {
                log.info(">>>>AsyncCharConsumer.onCharReceived...");
                StringBuilder sb = new StringBuilder();
                while (buf.hasRemaining())
                {
                    sb.append(buf.get());
                }
                if (null != callback)
                {
                    callback.call(sb.toString());
                }
            }


            protected void onResponseReceived(HttpResponse response)
            {
                log.info("response.StatusCode{}", response.getStatusLine().getStatusCode());
                this.response = response;
            }


            @Override
            protected HttpResponse buildResult(HttpContext httpContext)
            {
                return this.response;
            }

        };

        asyncClient.execute(HttpAsyncMethods.create(request), charConsumer, new FutureCallback<HttpResponse>() {

            @Override
            public void failed(Exception ex)
            {
                log.error(" asyncClient.execute failed, e:", ex);
                close(asyncClient);
            }


            @Override
            public void completed(HttpResponse resp)
            {
                log.info(" asyncClient.completed");
                close(asyncClient);
            }


            @Override
            public void cancelled()
            {
                log.info(" asyncClient.cancelled");
                close(asyncClient);
            }
        });

    }


    /**
     * 关闭client对象
     *
     * @param client
     */
    private static void close(CloseableHttpAsyncClient client)
    {
        try
        {
            client.close();
        }
        catch (IOException e)
        {
            log.error(">>>>HttpAsyncClientUtil closed fail, e:", e);

        }
    }

}
