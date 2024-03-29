package cn.jianwoo.openai;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.jianwoo.openai.chatgptapi.bo.BillingUsage;
import cn.jianwoo.openai.chatgptapi.bo.FunctionsReq;
import cn.jianwoo.openai.chatgptapi.bo.ParametersReq;
import cn.jianwoo.openai.chatgptapi.bo.PropertyReq;
import cn.jianwoo.openai.chatgptapi.bo.Subscription;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSONObject;

import cn.jianwoo.openai.chatgptapi.auth.OpenAiAuth;
import cn.jianwoo.openai.chatgptapi.bo.AudioReq;
import cn.jianwoo.openai.chatgptapi.bo.AudioRes;
import cn.jianwoo.openai.chatgptapi.bo.CompletionReq;
import cn.jianwoo.openai.chatgptapi.bo.CompletionRes;
import cn.jianwoo.openai.chatgptapi.bo.EmbeddingsReq;
import cn.jianwoo.openai.chatgptapi.bo.EmbeddingsRes;
import cn.jianwoo.openai.chatgptapi.bo.EnginesDataRes;
import cn.jianwoo.openai.chatgptapi.bo.EnginesListRes;
import cn.jianwoo.openai.chatgptapi.bo.EventListRes;
import cn.jianwoo.openai.chatgptapi.bo.FileDetRes;
import cn.jianwoo.openai.chatgptapi.bo.FileListRes;
import cn.jianwoo.openai.chatgptapi.bo.FileReq;
import cn.jianwoo.openai.chatgptapi.bo.FineTuneListRes;
import cn.jianwoo.openai.chatgptapi.bo.FineTunesReq;
import cn.jianwoo.openai.chatgptapi.bo.FineTunesRes;
import cn.jianwoo.openai.chatgptapi.bo.ImageReq;
import cn.jianwoo.openai.chatgptapi.bo.ImageRes;
import cn.jianwoo.openai.chatgptapi.bo.MessageReq;
import cn.jianwoo.openai.chatgptapi.bo.ModelDataRes;
import cn.jianwoo.openai.chatgptapi.bo.ModelRes;
import cn.jianwoo.openai.chatgptapi.bo.ModerationsReq;
import cn.jianwoo.openai.chatgptapi.bo.ModerationsRes;
import cn.jianwoo.openai.chatgptapi.bo.ObjDelRes;
import cn.jianwoo.openai.chatgptapi.completions.FastCompletion;
import cn.jianwoo.openai.chatgptapi.constants.Model;
import cn.jianwoo.openai.chatgptapi.constants.Role;
import cn.jianwoo.openai.chatgptapi.exception.ApiException;
import cn.jianwoo.openai.chatgptapi.service.PostApiService;
import cn.jianwoo.openai.chatgptapi.service.impl.ChatGptApiPost;

/**
 * @author gulihua
 * @Description
 * @date 2023-03-01 09:54
 */
public class DemoTest
{
    static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));

    static String apiKey = "sk-M****************************************************I";
    static PostApiService service = new ChatGptApiPost(new OpenAiAuth(apiKey, proxy));

    /**
     *
     * 列出模型
     *
     * @author gulihua
     */
    @Test
    public void models() throws ApiException
    {
        ModelRes res = service.models();
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 检索模型
     *
     * @author gulihua
     */
    @Test
    public void model() throws ApiException
    {
        ModelDataRes res = service.model("text-davinci-001");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 创建AI对话
     *
     * @author gulihua
     */
    @Test
    public void completions() throws ApiException
    {
        CompletionReq req = CompletionReq.builder().model(Model.TEXT_DAVINCI_003.getName()).prompt("你好").build();
        CompletionRes res = service.completions(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 连续对话
     *
     * @author gulihua
     */
    @Test
    public void completionsContext() throws ApiException
    {
        CompletionReq req = CompletionReq.builder().model(Model.TEXT_DAVINCI_003.getName())
                .stop("[\" Human:\", \" Bot:\"]").prompt("Human: 你好").build();
        CompletionRes res = service.completions(req);
        System.out.println(JSONObject.toJSONString(res));
        req.setPrompt(res.getAnswer() + "\n" + "Human: 你叫什么");
        res = service.completions(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 创建异步对话(流式)
     *
     * @author gulihua
     */
    public static void completionsStream() throws Exception
    {
        CompletionReq req = CompletionReq.builder().model(Model.TEXT_DAVINCI_003.getName()).prompt("你是什么模型").build();
        service.completionsStream(req, res -> {
            // 回调方法
            if (res != null)
            {
                System.out.println("isSuccess:" + res.getIsSuccess() + ", Done:" + res.getDone() + ", 接收到的数据:  "
                        + res.getAnswer());

            }
        });
    }


    /**
     *
     * 使用gpt-3.5-turbo模型聊天
     *
     * @author gulihua
     */
    @Test
    public void completionsChat() throws ApiException
    {
        CompletionReq req = CompletionReq.builder().model(Model.GPT_35_TURBO.getName())
                .messages(
                        Collections.singletonList(MessageReq.builder().role(Role.USER.getName()).content("你好").build()))
                .build();
        CompletionRes res = service.completionsChat(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 使用gpt-3.5-turbo模型聊天
     *
     * @author gulihua
     */
    @Test
    public void completionsChatContext() throws ApiException
    {
        long start = System.currentTimeMillis();
        CompletionReq req = CompletionReq.builder().model(Model.GPT_35_TURBO.getName())
                .messages(Collections
                        .singletonList(MessageReq.builder().role(Role.USER.getName()).content("请重复我的话").build()))
                .build();
        CompletionRes res = service.completionsChat(req);
        System.out.println(JSONObject.toJSONString(res));
        long end = System.currentTimeMillis();
        System.out.println("cost1=" + (end - start));
        start = System.currentTimeMillis();
        List<MessageReq> messages = new ArrayList<>();
        messages.add(res.getChoices().get(0).getMessage());
        messages.add(MessageReq.builder().role(Role.USER.getName()).content("我是中国人").build());
        req.setMessages(messages);
        res = service.completionsChat(req);
        System.out.println(JSONObject.toJSONString(res));
        end = System.currentTimeMillis();
        System.out.println("cost2=" + (end - start));

    }


    /**
     *
     * 使用gpt-3.5-turbo模型聊天(流式)
     *
     * @author gulihua
     */
    public static void completionsChatStream() throws Exception
    {
        CompletionReq req = CompletionReq.builder().model(Model.GPT_35_TURBO.getName())
                .messages(
                        Collections.singletonList(MessageReq.builder().role(Role.USER.getName()).content("你好").build()))
                .build();
        service.completionsChatStream(req, res -> {
            // 回调方法
            if (res != null)
            {
                System.out.println("isSuccess:" + res.getIsSuccess() + ", Done:" + res.getDone() + ", 接收到的数据:  "
                        + res.getChatContent());
            }
        });
    }


    /**
     *
     * 使用gpt-3.5-turbo模型聊天(使用函数)
     *
     * @author gulihua
     */
    @Test
    public void completionsChatFunctions() throws ApiException
    {
        PropertyReq location = PropertyReq.builder().name("location").type("string").description("城市，比如：上海").build();
        PropertyReq format = PropertyReq.builder().name("format").type("string").description("温度单位")
                .enums(Arrays.asList("摄氏度", "华氏度")).build();
        JSONObject properties = new JSONObject();
        properties.putAll(location.toJSON());
        properties.putAll(format.toJSON());
        ParametersReq parametersReq = ParametersReq.builder().required(Arrays.asList("properties", "format"))
                .properties(properties).build();
        FunctionsReq functions = FunctionsReq.builder().name("getCurrentWeather").description("查询天气")
                .parameters(parametersReq).build();
        MessageReq messageReq = MessageReq.builder().role(Role.USER.getName()).content("南京天气怎么样,并给出穿衣建议").build();

        CompletionReq req = CompletionReq.builder().model(Model.GPT_35_TURBO_16K_0613.getName())
                .messages(Collections.singletonList(messageReq)).functions(Collections.singletonList(functions))
                .build();
        CompletionRes res = service.completionsChat(req);
//        System.out.println(JSONObject.toJSONString(res));
//        System.out.println(res.getFunctionArgs());
        MessageReq messageReq1 = MessageReq.builder().role(Role.ASSISTANT.getName())
                .content(queryWeather(res.getFunctionArgs().getString("location"))).build();

        req = CompletionReq.builder().model(Model.GPT_35_TURBO_16K_0613.getName())
                .messages(Arrays.asList(messageReq, messageReq1)).build();
        res = service.completionsChat(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 使用gpt-3.5-turbo模型聊天(流式, 使用函数)
     *
     * @author gulihua
     */
    public static void completionsChatStreamFunctions() throws Exception
    {
        PropertyReq location = PropertyReq.builder().name("location").type("string").description("城市，比如：上海").build();
        PropertyReq format = PropertyReq.builder().name("format").type("string").description("温度单位")
                .enums(Arrays.asList("摄氏度", "华氏度")).build();
        JSONObject properties = new JSONObject();
        properties.putAll(location.toJSON());
        properties.putAll(format.toJSON());
        ParametersReq parametersReq = ParametersReq.builder().required(Arrays.asList("properties", "format"))
                .properties(properties).build();
        FunctionsReq functions = FunctionsReq.builder().name("getCurrentWeather").description("查询天气")
                .parameters(parametersReq).build();
        MessageReq messageReq = MessageReq.builder().role(Role.USER.getName()).content("南京天气怎么样,并给出穿衣建议").build();

        CompletionReq req = CompletionReq.builder().model(Model.GPT_35_TURBO_16K_0613.getName())
                .messages(Collections.singletonList(messageReq)).functions(Collections.singletonList(functions))
                .build();
        StringBuilder sb = new StringBuilder();
        service.completionsChatStream(req, res -> {
            // 回调方法
            if (res != null)
            {
                sb.append(res.getChatContent());
                // 接收结束
                if (res.getDone())
                {
                    JSONObject args = JSONObject.parseObject(sb.toString());
                    MessageReq messageReq1 = MessageReq.builder().role(Role.ASSISTANT.getName())
                            .content(queryWeather(args.getString("location"))).build();
                    CompletionReq req1 = CompletionReq.builder().model(Model.GPT_35_TURBO_16K_0613.getName())
                            .messages(Arrays.asList(messageReq, messageReq1)).build();
                    StringBuilder sb1 = new StringBuilder();
                    service.completionsChatStream(req1, res1 -> {
                        // 回调方法
                        if (res1 != null)
                        {
                            System.out.println("isSuccess:" + res1.getIsSuccess() + ", Done:" + res1.getDone()
                                    + ", 接收到的数据:  " + res1.getChatContent());
                            sb1.append(res1.getChatContent());
                            if (res1.getDone())
                            {
                                System.out.println(sb1);
                            }

                        }
                    });
                }
            }
        });

    }


    public static void main(String[] args) throws Exception
    {
//        completionsStream();
//        completionsChatStream();
        completionsChatStreamFunctions();
    }


    private static String queryWeather(String city)
    {
        city = city.replaceAll("市", "");
        String url = "https://v0.yiketianqi.com/api?unescape=1&version=v61&appid=35229654&appsecret=o1KeF9bn&city="
                + city;
        HttpResponse response = HttpRequest.get(url).execute();
        JSONObject res = JSONObject.parse(response.body());
        StringBuilder sb = new StringBuilder();
        sb.append(res.getString("wea"));
        sb.append("，");
        sb.append("温度：");
        sb.append(res.getString("tem2")).append("~").append(res.getString("tem1"));
        sb.append("风力：");
        sb.append(res.getString("win")).append(" ").append(res.getString("win_speed"));
        sb.append("空气质量：");
        sb.append(res.getString("air_level"));
        return sb.toString();

    }


    /**
     *
     * 编辑会话文本
     *
     * @author gulihua
     */
    @Test
    public void completionsEdit() throws ApiException
    {
        CompletionReq req = CompletionReq.builder().model("text-davinci-edit-001").input("What day of the wek is it?")
                .instruction("Fix the spelling mistakes").build();
        CompletionRes res = service.completionsEdit(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 根据描述生成图片
     *
     * @author gulihua
     */
    @Test
    public void imageCreate() throws ApiException
    {
        ImageReq req = ImageReq.builder().prompt("cat").build();
        ImageRes res = service.imageCreate(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 编辑图片
     *
     * @author gulihua
     */
    @Test
    public void imageEdit() throws ApiException
    {
        File cat = new File("/Users/gulihua/Downloads/cat.png");
        ImageReq req = ImageReq.builder().prompt("cat").image(cat).build();
        ImageRes res = service.imageEdit(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 图像更改
     *
     * @author gulihua
     */
    @Test
    public void imageVariate() throws ApiException
    {
        File cat = new File("/Users/gulihua/Downloads/cat.png");
        ImageReq req = ImageReq.builder().prompt("cat").image(cat).build();
        ImageRes res = service.imageVariate(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 创建embeddings
     *
     * @author gulihua
     */
    @Test
    public void embeddingsCreate() throws ApiException
    {
        EmbeddingsReq req = EmbeddingsReq.builder().model(Model.TEXT_EMBEDDING_ADA_002.getName())
                .input(Collections.singletonList("he food was delicious and the waiter...")).build();
        EmbeddingsRes res = service.embeddingsCreate(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 语音转录
     *
     * @author gulihua
     */
    @Test
    public void audioTranscribes() throws ApiException
    {
        File audio = new File("/Users/gulihua/Downloads/audio.mp3");
        AudioReq req = AudioReq.builder().file(audio).build();
        AudioRes res = service.audioTranscribes(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 语音翻译
     *
     * @author gulihua
     */
    @Test
    public void audioTranslates() throws ApiException
    {
        File audio = new File("/Users/gulihua/Downloads/audio.mp3");
        AudioReq req = AudioReq.builder().file(audio).build();
        AudioRes res = service.audioTranslates(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 列出所有用户上传的文件
     *
     * @author gulihua
     */
    @Test
    public void fileList() throws ApiException
    {
        FileListRes res = service.fileList();
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 文件上传
     *
     * @author gulihua
     */
    @Test
    public void fileUpload() throws ApiException
    {
        File cat = new File("/Users/gulihua/tmp/chatGptApiSdk/src/main/resources/json_prepared.jsonl");
        FileReq req = FileReq.builder().purpose("fine-tune").file(cat).build();
        FileDetRes res = service.fileUpload(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 文件删除
     *
     * @author gulihua
     */
    @Test
    public void fileDelete() throws ApiException
    {
        ObjDelRes res = service.fileDelete("file-jPU5wZkSxqkWJr32HlvnEou0");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 文件检索
     *
     * @author gulihua
     */
    @Test
    public void fileRetrieve() throws ApiException
    {
        FileDetRes res = service.fileRetrieve("file-2HvmtSTWMTatG5mvRTPerMkn");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 文件检索内容
     *
     * @author gulihua
     */
    @Test
    public void fileRetrieveContent() throws ApiException
    {
        String res = service.fileRetrieveContent("file-2HvmtSTWMTatG5mvRTPerMkn");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 创建Fine-tunes
     *
     * @author gulihua
     */
    @Test
    public void fineTuneCreate() throws ApiException
    {
        FineTunesReq req = FineTunesReq.builder().trainingFile("file-Cj4zi8QGZQAaVhKzMvL6NsGq")
                .model(Model.DAVINCI.getName()).suffix("davinci model test01").build();
        FineTunesRes res = service.fineTuneCreate(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 微调之后测试
     *
     * @author gulihua
     */
    @Test
    public void completionsAfterFineTunes() throws ApiException
    {
        CompletionReq req = CompletionReq.builder().maxTokens(100).stop("END").frequencyPenalty(new BigDecimal(2))
                .model("davinci:ft-personal:davinci-model-test01-2023-05-15-09-07-39")
                .prompt("小明的妈妈有三个儿子，大儿子叫大明，二儿子叫二明，三儿子叫什么。").build();
        CompletionRes res = service.completions(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 列出Fine-tunes
     *
     * @author gulihua
     */
    @Test
    public void fineTuneList() throws ApiException
    {
        FineTuneListRes res = service.fineTuneList();
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 检索Fine-tunes
     *
     * @author gulihua
     */
    @Test
    public void fineTuneRetrieve() throws ApiException
    {
        FineTunesRes res = service.fineTuneRetrieve("ft-XyXOimCsBXnRc2Djpf52ghwl");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 取消Fine-tunes
     *
     * @author gulihua
     */
    @Test
    public void fineTuneCancel() throws ApiException
    {
        FineTunesRes res = service.fineTuneCancel("ft-XyXOimCsBXnRc2Djpf52ghwl");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 列出Fine-tunes 事件
     *
     * @author gulihua
     */
    @Test
    public void fineTuneEventList() throws ApiException
    {
        EventListRes res = service.fineTuneEventList("ft-XyXOimCsBXnRc2Djpf52ghwl");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 删除Fine-tunes
     *
     * @author gulihua
     */
    @Test
    public void fineTuneDelete() throws ApiException
    {
        ObjDelRes res = service.fineTuneDelete("curie:ft-acmeco-2021-03-03-21-44-20");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 创建moderations
     *
     * @author gulihua
     */
    @Test
    public void moderationsCreate() throws ApiException
    {
        ModerationsReq req = ModerationsReq.builder().input("I want to kill them.").build();
        ModerationsRes res = service.moderationsCreate(req);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 列出所有引擎
     *
     * @author gulihua
     */
    @Test
    public void enginesList() throws ApiException
    {
        EnginesListRes res = service.enginesList();
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 检索引擎
     *
     * @author gulihua
     */

    @Test
    public void enginesRetrieve() throws ApiException
    {
        EnginesDataRes res = service.enginesRetrieve("text-davinci-003");
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 账户信息查询：里面包含总金额等信息
     *
     * @author gulihua
     */

    @Test
    public void subscription() throws ApiException
    {
        Subscription res = service.subscription();
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 账户调用接口消耗金额信息查询, 最多查询100天
     *
     * @author gulihua
     */

    @Test
    public void billingUsage() throws ApiException
    {
        Date startDate = DateUtil.parse("2023-03-01");
        Date endDate = DateUtil.parse("2023-05-11");
        BillingUsage res = service.billingUsage(startDate, endDate);
        System.out.println(JSONObject.toJSONString(res));
    }


    /**
     *
     * 快速创建会话
     *
     * @author gulihua
     */
    @Test
    public void fastCompletionAsk() throws ApiException
    {
        System.out.println(FastCompletion.ask(apiKey, "介绍一下《三国演义》这本书"));
    }


    /**
     *
     * 快速创建聊天
     *
     * @author gulihua
     */
    @Test
    public void fastCompletionChat() throws ApiException
    {
        System.out.println(FastCompletion.chat(apiKey, "介绍一下《三国演义》这本书"));
    }


    /**
     *
     * 快速生成图像
     *
     * @author gulihua
     */
    @Test
    public void fastCompletionAsk4Image() throws ApiException
    {
        System.out.println(FastCompletion.ask4Image(apiKey, "猫"));
    }
}
