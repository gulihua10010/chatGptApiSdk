package cn.jianwoo.openai.chatgptapi.stream;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import cn.jianwoo.openai.chatgptapi.bo.HttpFailedBO;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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

    public static OkHttpClient createHttpClient(Proxy proxy, int connectTimeout, int readTimeout)
    {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        client.writeTimeout(readTimeout, TimeUnit.MILLISECONDS);
        client.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        if (null != proxy)
        {
            client.proxy(proxy);
        }
        return client.build();

    }


    public static void execute(OkHttpClient httpClient, Request request, Callback<String> succCallback)
    {
        execute(httpClient, request, succCallback, null);
    }

    public static void execute(OkHttpClient httpClient, Request request, Callback<String> succCallback,
            Callback<HttpFailedBO> failCallback)
    {
        EventSource.Factory factory = EventSources.createFactory(httpClient);
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
