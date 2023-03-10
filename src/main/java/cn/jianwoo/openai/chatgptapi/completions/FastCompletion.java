package cn.jianwoo.openai.chatgptapi.completions;

import cn.jianwoo.openai.chatgptapi.auth.OpenAiAuth;
import cn.jianwoo.openai.chatgptapi.bo.Choices;
import cn.jianwoo.openai.chatgptapi.bo.CompletionReq;
import cn.jianwoo.openai.chatgptapi.bo.CompletionRes;
import cn.jianwoo.openai.chatgptapi.bo.ImageReq;
import cn.jianwoo.openai.chatgptapi.bo.ImageRes;
import cn.jianwoo.openai.chatgptapi.bo.MessageReq;
import cn.jianwoo.openai.chatgptapi.constants.Model;
import cn.jianwoo.openai.chatgptapi.constants.Role;
import cn.jianwoo.openai.chatgptapi.exception.ApiException;
import com.alibaba.fastjson2.JSONObject;
import sun.misc.resources.Messages;

import java.net.Proxy;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Completion封装
 *
 * @blog https://jianwoo.cn
 * @author gulihua
 * @github https://github.com/gulihua10010/
 * @bilibili 顾咕咕了
 */
public class FastCompletion
{
    /***
     * 快速提问
     * 
     * @param apiKey API KEY
     * @param prompt 问题
     * @return 答案
     */
    public static String ask(String apiKey, String prompt) throws ApiException
    {
        CompletionRes res = OpenAiAuth.builder().apiKey(apiKey).post()
                .completions(CompletionReq.builder().model(Model.TEXT_DAVINCI_003.getName()).prompt(prompt).build());
        return res.getAnswer();
    }


    /***
     * 快速提问
     *
     * @param apiKey API KEY
     * @param prompt 问题
     * @param proxy 代理
     * @return 答案
     */
    public static String ask(String apiKey, Proxy proxy, String prompt) throws ApiException
    {
        CompletionRes res = OpenAiAuth.builder().apiKey(apiKey).proxy(proxy).post()
                .completions(CompletionReq.builder().model(Model.TEXT_DAVINCI_003.getName()).prompt(prompt).build());
        return res.getChoices().stream().map(Choices::getText).collect(Collectors.joining());
    }


    /***
     * 快速聊天
     *
     * @param apiKey API KEY
     * @param content 内容
     * @return 答案
     */
    public static String chat(String apiKey, String content) throws ApiException
    {
        CompletionRes res = OpenAiAuth.builder().apiKey(apiKey).post()
                .completionsChat(CompletionReq.builder().model(Model.GPT_35_TURBO.getName())
                        .messages(Collections
                                .singletonList(MessageReq.builder().role(Role.USER.getName()).content(content).build()))
                        .build());
        return res.getChatContent();
    }


    /***
     * 快速聊天
     *
     * @param apiKey API KEY
     * @param content 内容
     * @param proxy 代理
     * @return 答案
     */
    public static String chat(String apiKey, Proxy proxy, String content) throws ApiException
    {
        CompletionRes res = OpenAiAuth.builder().apiKey(apiKey).proxy(proxy).post()
                .completionsChat(CompletionReq.builder().model(Model.GPT_35_TURBO.getName())
                        .messages(Collections
                                .singletonList(MessageReq.builder().role(Role.USER.getName()).content(content).build()))
                        .build());
        return res.getChoices().stream().map(Choices::getText).collect(Collectors.joining());
    }


    /***
     * 请求图片
     *
     * @param apiKey API KEY
     * @param prompt 问题
     * @return 图片 url
     */
    public static String ask4Image(String apiKey, String prompt) throws ApiException
    {
        ImageRes res = OpenAiAuth.builder().apiKey(apiKey).post()
                .imageCreate(ImageReq.builder().prompt(prompt).build());
        return res.getData().get(0).getUrl();
    }


    /***
     * 请求图片
     *
     * @param apiKey API KEY
     * @param prompt 问题
     * @param proxy 代理
     * @return 答案
     */
    public static String ask4Image(String apiKey, Proxy proxy, String prompt) throws ApiException
    {
        CompletionRes res = OpenAiAuth.builder().apiKey(apiKey).proxy(proxy).post()
                .completions(CompletionReq.builder().model("text-davinci-003").prompt(prompt).build());
        return res.getChoices().stream().map(Choices::getText).collect(Collectors.joining());
    }
}
