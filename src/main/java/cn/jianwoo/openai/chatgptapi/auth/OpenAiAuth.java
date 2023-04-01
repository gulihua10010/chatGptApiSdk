package cn.jianwoo.openai.chatgptapi.auth;

import cn.hutool.core.util.StrUtil;
import cn.jianwoo.openai.chatgptapi.service.PostApiService;
import cn.jianwoo.openai.chatgptapi.service.impl.ChatGptApiPost;

import java.net.Proxy;

/**
 * OpenAiAuth授权实体类
 * 
 * @blog https://jianwoo.cn
 * @author gulihua
 * @github https://github.com/gulihua10010/
 * @bilibili 顾咕咕了
 * @date 2023-02-10 22:10
 */
public class OpenAiAuth
{
    public static final String BASE_URL = "https://api.openai.com/v1";

    /** apiKey,获取地址:https://platform.openai.com/account/api-keys */
    private String apiKey;
    /** 代理 */
    private Proxy proxy;

    /** 自定义端点 */
    private String baseUrl;

    /** 超时时间(单位：秒) */
    private int timeout;

    /** 连接超时(单位：秒) */
    private int connectionTimeout;

    /** 读取超时(单位：秒) */
    private int readTimeout;
    /** 是否重试，只对completions/completionsStream/completionsChat/completionsChatStream有效 */
    private boolean isReTry;

    private OpenAiAuth()
    {
    }


    public OpenAiAuth(String apiKey)
    {
        this.apiKey = apiKey;
    }


    public OpenAiAuth(String apiKey, Proxy proxy)
    {
        this.apiKey = apiKey;
        this.proxy = proxy;
    }


    public OpenAiAuth(String apiKey, Proxy proxy, String baseUrl)
    {
        this.apiKey = apiKey;
        this.proxy = proxy;
        this.baseUrl = baseUrl;
    }


    public static OpenAiAuth builder()
    {
        return new OpenAiAuth();
    }


    public OpenAiAuth apiKey(String apiKey)
    {
        this.apiKey = apiKey;
        return this;
    }


    public OpenAiAuth proxy(Proxy proxy)
    {
        this.proxy = proxy;
        return this;
    }


    public OpenAiAuth baseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
        return this;
    }


    public OpenAiAuth timeout(int timeout)
    {
        this.timeout = timeout;
        this.connectionTimeout = timeout;
        this.readTimeout = timeout;
        return this;
    }


    public OpenAiAuth connectionTimeout(int connectionTimeout)
    {
        this.connectionTimeout = connectionTimeout;
        return this;
    }


    public OpenAiAuth readTimeout(int readTimeout)
    {
        this.readTimeout = readTimeout;
        return this;
    }


    public String getBaseUrl()
    {
        if (StrUtil.isBlank(baseUrl))
        {
            this.baseUrl = BASE_URL;
        }
        return this.baseUrl;
    }


    public int getTimeout()
    {
        if (this.timeout == 0)
        {
            this.timeout = 60;
        }
        return this.timeout * 1000;
    }


    public int getConnectionTimeout()
    {
        if (this.connectionTimeout == 0)
        {
            this.connectionTimeout = 60;
        }
        return this.connectionTimeout * 1000;
    }


    public int getReadTimeout()
    {
        if (this.readTimeout == 0)
        {
            this.readTimeout = 60;
        }
        return this.readTimeout * 1000;
    }


    public OpenAiAuth isReTry(boolean isReTry)
    {
        this.isReTry = isReTry;
        return this;
    }


    public boolean getIsReTry()
    {
        return this.isReTry;
    }


    public String getApiKey()
    {
        return "Bearer " + this.apiKey;
    }


    public Proxy getProxy()
    {
        return this.proxy;
    }


    public PostApiService post()
    {
        return new ChatGptApiPost(this);
    }

}
