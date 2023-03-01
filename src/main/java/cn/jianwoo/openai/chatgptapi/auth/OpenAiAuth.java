package cn.jianwoo.openai.chatgptapi.auth;

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
    /** apiKey,获取地址:https://platform.openai.com/account/api-keys */
    private String apiKey;
    /** 代理 */
    private Proxy proxy;

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
