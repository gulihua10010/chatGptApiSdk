package cn.jianwoo.openai.chatgptapi.stream;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.StrUtil;
import cn.jianwoo.openai.chatgptapi.auth.OpenAiAuth;
import cn.jianwoo.openai.chatgptapi.bo.HttpFailedBO;
import lombok.extern.log4j.Log4j2;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

/**
 * @author gulihua
 * @Description
 * @date 2023-02-23 12:12
 */
@Log4j2
public class HttpAsyncClientUtil
{
    private static OkHttpClient client;

    public static OkHttpClient initHttpClient(Proxy proxy, OpenAiAuth config)
    {
        if (client != null)
        {
            return client;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS);
        builder.readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS);
        if (null != proxy)
        {
            builder.proxy(proxy);
        }
        if (StrUtil.isNotBlank(config.getUsername()))
        {
            Authenticator proxyAuthenticator = new Authenticator() {

                public Request authenticate(Route route, Response response) throws IOException
                {
                    String credential = Credentials.basic(config.getUsername(), config.getPassword());
                    return response.request().newBuilder().header("Proxy-Authorization", credential).build();
                }
            };
            builder.proxyAuthenticator(proxyAuthenticator);
        }

        client = builder.build();
        client.dispatcher().setMaxRequestsPerHost(config.getMaxRequestsPerHost());
        client.dispatcher().setMaxRequests(config.getMaxRequests());
        return client;

    }


    public static void execute(Request request, Callback<String> succCallback)
    {
        execute(request, succCallback, null);
    }


    public static void execute(Request request, Callback<String> succCallback, Callback<HttpFailedBO> failCallback)
    {
        EventSource.Factory factory = EventSources.createFactory(client);
        factory.newEventSource(request, new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response)
            {
                log.debug("AsyncClient opens connection.");
            }


            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data)
            {
                log.debug("AsyncClient.onEvent, id={}, type={}, data= {}", id, type, data);
                if (null != succCallback)
                {
                    succCallback.call(data);
                }

            }


            @Override
            public void onClosed(EventSource eventSource)
            {
                log.debug("AsyncClient closes connection. ");

            }


            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response)
            {
                if (null == response)
                {
                    if (null != failCallback && null != t)
                    {
                        failCallback.call(new HttpFailedBO(t.getMessage(), t));
                    }
                    log.error(" asyncClient.execute failed, e: ", t);
                    return;
                }
                ResponseBody body = response.body();
                if (body != null)
                {
                    try
                    {
                        String content = body.string();
                        if (null != failCallback)
                        {
                            failCallback.call(new HttpFailedBO(content, t));
                        }
                        log.error(" asyncClient.execute failed, body: {}, e: {}", content, t);
                    }
                    catch (IOException e)
                    {
                        log.error(">>>>onFailure.exec failed, e: ", e);
                    }

                }
                else
                {
                    if (null != failCallback && null != t)
                    {
                        failCallback.call(new HttpFailedBO(t.getMessage(), t));
                    }
                    log.error(" asyncClient.execute failed, e: ", t);

                }
                eventSource.cancel();

            }
        });

    }

}
