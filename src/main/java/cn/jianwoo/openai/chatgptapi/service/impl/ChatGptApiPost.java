package cn.jianwoo.openai.chatgptapi.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.jianwoo.openai.chatgptapi.auth.OpenAiAuth;
import cn.jianwoo.openai.chatgptapi.bo.AudioReq;
import cn.jianwoo.openai.chatgptapi.bo.AudioRes;
import cn.jianwoo.openai.chatgptapi.bo.Choices;
import cn.jianwoo.openai.chatgptapi.bo.CompletionReq;
import cn.jianwoo.openai.chatgptapi.bo.CompletionRes;
import cn.jianwoo.openai.chatgptapi.bo.EmbeddingsReq;
import cn.jianwoo.openai.chatgptapi.bo.EmbeddingsRes;
import cn.jianwoo.openai.chatgptapi.bo.EnginesDataRes;
import cn.jianwoo.openai.chatgptapi.bo.EnginesListRes;
import cn.jianwoo.openai.chatgptapi.bo.ErrorRes;
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
import cn.jianwoo.openai.chatgptapi.exception.ApiException;
import cn.jianwoo.openai.chatgptapi.service.PostApiService;
import cn.jianwoo.openai.chatgptapi.stream.Callback;
import cn.jianwoo.openai.chatgptapi.stream.HttpAsyncClientUtil;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ChatGpt API封装<br>
 * (官网:<a href="https://platform.openai.com/docs/introduction">https://platform.openai.com/docs/introduction</a>)<br>
 *
 * @blog https://jianwoo.cn
 * @author gulihua
 * @github https://github.com/gulihua10010/
 * @bilibili 顾咕咕了
 * @date 2023-02-28 17:34
 */
@Log4j2
public class ChatGptApiPost implements PostApiService
{

    private OpenAiAuth auth;

    public static final String BASE_URL = "https://api.openai.com/v1";

    public static final String AUTHORIZATION_ERROR = "400001";
    public static final String JSON_ERROR = "500001";
    public static final String AUTHORIZATION_ERROR_MSG = "未授权的操作!ApiKey错误";
    public static final String JSON_ERROR_MSG = "JSON 解析异常";
    public static final String OTHER_ERROR = "900001";
    public static final String BIZ_ERROR = "800001";

    private ChatGptApiPost()
    {

    }


    public ChatGptApiPost(OpenAiAuth openAiAuth)
    {
        this.auth = openAiAuth;

    }


    @Override
    public ModelRes models() throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/models").headerMap(headers, true).setProxy(auth.getProxy())
                .execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>models.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>models.response:{}", response.body());
            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ModelRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>models.response:{}", response.body());
            log.error(">>>>models.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }

    }


    @Override
    public ModelDataRes model(String modelName) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/models/" + modelName).headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>models.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() == 404)
        {
            log.error(">>>>models.response:{}", response.body());
            throw new ApiException(BIZ_ERROR, "model 不存在");
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>models.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ModelDataRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>models.response:{}", response.body());
            log.error(">>>>models.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public CompletionRes completions(CompletionReq req) throws ApiException
    {
        req.setStream(false);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/completions").headerMap(headers, true)
                .body(JSONObject.toJSONString(req)).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>completions.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>completions.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), CompletionRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>completions.response:{}", response.body());
            log.error(">>>>completions.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public void completionsStream(CompletionReq req, Callback<CompletionRes> callback) throws ApiException
    {
        req.setStream(true);
        Request request = new Request.Builder().url(BASE_URL + "/completions")
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), JSONObject.toJSONString(req)))
                .header("Authorization", auth.getApiKey()).header("Accept", "text/event-stream").build();

        OkHttpClient httpClient = HttpAsyncClientUtil.createHttpClient(auth.getProxy());

        HttpAsyncClientUtil.execute(httpClient, request, param -> {
            try
            {
                CompletionRes resBO = parseConversation(param);
                callback.call(resBO);
            }
            catch (Exception e)
            {
                log.error(">>>>completionsStream.parseConversation.exec.exception, e:", e);
                throw new RuntimeException(e);
            }

        }, param -> {
            String msg = "Call Api 'https://api.openai.com/v1/completions' error";
            try
            {
                ErrorRes errorRes = JSON.parseObject(param, ErrorRes.class);
                msg = errorRes.getError().getMessage();
            }
            catch (Exception e)
            {
                log.error("completionsStream.parse error response failed, body:{}", param);
            }
            throw new RuntimeException(msg);

        });
    }


    @Override
    public CompletionRes completionsChat(CompletionReq req) throws ApiException
    {

        req.setStream(false);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/chat/completions").headerMap(headers, true)
                .body(JSONObject.toJSONString(req)).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>completionsChat.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>completionsChat.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), CompletionRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>completionsChat.response:{}", response.body());
            log.error(">>>>completionsChat.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public void completionsChatStream(CompletionReq req, Callback<CompletionRes> callback) throws ApiException
    {

        req.setStream(true);
        Request request = new Request.Builder().url(BASE_URL + "/chat/completions")
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), JSONObject.toJSONString(req)))
                .header("Authorization", auth.getApiKey()).header("Accept", "text/event-stream").build();

        OkHttpClient asyncClient = HttpAsyncClientUtil.createHttpClient(auth.getProxy());
        HttpAsyncClientUtil.execute(asyncClient, request, param -> {
            try
            {
                CompletionRes resBO = parseConversation(param);
                callback.call(resBO);
            }
            catch (Exception e)
            {
                log.error(">>>>completionsChatStream.parseConversation.exec.exception, e:", e);
                throw new RuntimeException(e);
            }

        }, param -> {
            String msg = "Call Api 'https://api.openai.com/v1/chat/completions' error";
            try
            {
                ErrorRes errorRes = JSON.parseObject(param, ErrorRes.class);
                msg = errorRes.getError().getMessage();
            }
            catch (Exception e)
            {
                log.error("completionsChatStream.parse error response failed, body:{}", param);
            }
            throw new RuntimeException(msg);

        });
    }


    @Override
    public CompletionRes completionsEdit(CompletionReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/edits").headerMap(headers, true)
                .body(JSONObject.toJSONString(req)).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>completionsEdit.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>completionsEdit.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), CompletionRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>completionsEdit.response:{}", response.body());
            log.error(">>>>completionsEdit.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public ImageRes imageCreate(ImageReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/images/generations").headerMap(headers, true)
                .body(JSONObject.toJSONString(req)).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>imageCreate.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>imageCreate.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ImageRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>imageCreate.response:{}", response.body());
            log.error(">>>>imageCreate.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public ImageRes imageEdit(ImageReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("image", req.getImage());
        paramMap.put("mask", req.getMask());
        paramMap.put("prompt", req.getPrompt());
        paramMap.put("n", req.getN());
        paramMap.put("size", req.getSize());
        paramMap.put("response_format", req.getResponseFormat());
        paramMap.put("user", req.getUser());

        HttpResponse response = HttpRequest.post(BASE_URL + "/images/edits").headerMap(headers, true).form(paramMap)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>imageEdit.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>imageEdit.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ImageRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>imageEdit.response:{}", response.body());
            log.error(">>>>imageEdit.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public ImageRes imageVariate(ImageReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("image", req.getImage());
        paramMap.put("n", req.getN());
        paramMap.put("size", req.getSize());
        paramMap.put("response_format", req.getResponseFormat());
        paramMap.put("user", req.getUser());

        HttpResponse response = HttpRequest.post(BASE_URL + "/images/variations").headerMap(headers, true)
                .form(paramMap).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>imageVariate.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>imageVariate.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ImageRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>imageVariate.response:{}", response.body());
            log.error(">>>>imageVariate.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public EmbeddingsRes embeddingsCreate(EmbeddingsReq req) throws ApiException
    {

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/embeddings").headerMap(headers, true)
                .body(JSONObject.toJSONString(req)).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>embeddingsCreate.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>embeddingsCreate.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), EmbeddingsRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>embeddingsCreate.response:{}", response.body());
            log.error(">>>>embeddingsCreate.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public AudioRes audioTranscribes(AudioReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", req.getFile());
        paramMap.put("model", req.getModel());

        HttpResponse response = HttpRequest.post(BASE_URL + "/audio/transcriptions").headerMap(headers, true)
                .form(paramMap).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>audioTranscribes.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>audioTranscribes.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), AudioRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>audioTranscribes.response:{}", response.body());
            log.error(">>>>audioTranscribes.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public AudioRes audioTranslates(AudioReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", req.getFile());
        paramMap.put("model", req.getModel());

        HttpResponse response = HttpRequest.post(BASE_URL + "/audio/translations").headerMap(headers, true)
                .form(paramMap).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>audioTranslates.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>audioTranslates.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), AudioRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>audioTranslates.response:{}", response.body());
            log.error(">>>>audioTranslates.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public FileListRes fileList() throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/files").headerMap(headers, true).setProxy(auth.getProxy())
                .execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fileList.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fileList.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), FileListRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fileList.response:{}", response.body());
            log.error(">>>>fileList.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public FileDetRes fileUpload(FileReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("purpose", req.getPurpose());
        paramMap.put("file", req.getFile());

        HttpResponse response = HttpRequest.post(BASE_URL + "/files").headerMap(headers, true).form(paramMap)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fileUplaod.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fileUplaod.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), FileDetRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fileUplaod.response:{}", response.body());
            log.error(">>>>fileUplaod.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public ObjDelRes fileDelete(String fileId) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.delete(BASE_URL + "/files/" + fileId).headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fileDelete.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fileDelete.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ObjDelRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fileDelete.response:{}", response.body());
            log.error(">>>>fileDelete.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public FileDetRes fileRetrieve(String fileId) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/files/" + fileId).headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fileRetrieve.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fileRetrieve.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), FileDetRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fileRetrieve.response:{}", response.body());
            log.error(">>>>fileRetrieve.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public String fileRetrieveContent(String fileId) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/files/" + fileId + "/content").headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fileRetrieveContent.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fileRetrieveContent.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return response.body();
        }
        catch (Exception e)
        {
            log.error(">>>>fileRetrieveContent.response:{}", response.body());
            log.error(">>>>fileRetrieveContent.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public FineTunesRes fineTuneCreate(FineTunesReq req) throws ApiException
    {

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/fine-tunes").headerMap(headers, true)
                .body(JSONObject.toJSONString(req)).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fineTuneCreate.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fineTuneCreate.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), FineTunesRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fineTuneCreate.response:{}", response.body());
            log.error(">>>>fineTuneCreate.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public FineTuneListRes fineTuneList() throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/fine-tunes").headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fineTuneList.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fineTuneList.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), FineTuneListRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fineTuneList.response:{}", response.body());
            log.error(">>>>fineTuneList.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public FineTunesRes fineTuneRetrieve(String fineTuneId) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/fine-tunes/" + fineTuneId).headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fineTuneRetrieve.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fineTuneRetrieve.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), FineTunesRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fineTuneRetrieve.response:{}", response.body());
            log.error(">>>>fineTuneRetrieve.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public FineTunesRes fineTuneCancel(String fineTuneId) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/fine-tunes/" + fineTuneId + "/cancel")
                .headerMap(headers, true).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fineTuneCancel.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fineTuneCancel.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), FineTunesRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fineTuneCancel.response:{}", response.body());
            log.error(">>>>fineTuneCancel.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public EventListRes fineTuneEventList(String fineTuneId) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/fine-tunes/" + fineTuneId + "/events")
                .headerMap(headers, true).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fineTuneEventList.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fineTuneEventList.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), EventListRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fineTuneEventList.response:{}", response.body());
            log.error(">>>>fineTuneEventList.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public ObjDelRes fineTuneDelete(String model) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.delete(BASE_URL + "/models/" + model).headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>fineTuneDelete.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>fineTuneDelete.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ObjDelRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>fineTuneDelete.response:{}", response.body());
            log.error(">>>>fineTuneDelete.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public ModerationsRes moderationsCreate(ModerationsReq req) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.post(BASE_URL + "/moderations").headerMap(headers, true)
                .body(JSONObject.toJSONString(req)).setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>moderationsCreate.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>moderationsCreate.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), ModerationsRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>moderationsCreate.response:{}", response.body());
            log.error(">>>>moderationsCreate.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public EnginesListRes enginesList() throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/engines").headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>enginesList.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>enginesList.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), EnginesListRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>enginesList.response:{}", response.body());
            log.error(">>>>enginesList.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    @Override
    public EnginesDataRes enginesRetrieve(String engineId) throws ApiException
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", auth.getApiKey());
        HttpResponse response = HttpRequest.get(BASE_URL + "/engines/" + engineId).headerMap(headers, true)
                .setProxy(auth.getProxy()).execute();

        if (response.getStatus() == 401)
        {
            log.error(">>>>enginesRetrieve.response:{}", response.body());
            throw new ApiException(AUTHORIZATION_ERROR, AUTHORIZATION_ERROR_MSG);
        }
        if (response.getStatus() != 200)
        {
            log.error(">>>>enginesRetrieve.response:{}", response.body());

            ErrorRes error = JSON.parseObject(response.body(), ErrorRes.class);
            throw new ApiException(OTHER_ERROR, error.getError().getMessage());

        }
        try
        {
            return JSON.parseObject(response.body(), EnginesDataRes.class);
        }
        catch (Exception e)
        {
            log.error(">>>>enginesRetrieve.response:{}", response.body());
            log.error(">>>>enginesRetrieve.response.parse.exception, e:", e);
            throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

        }
    }


    private static CompletionRes parseConversation(String res) throws ApiException
    {
        CompletionRes completionRes = null;
        List<String> resArr = StrUtil.splitTrim(res, "\n");
        StringBuilder sb = new StringBuilder();
        int type = 0;
        if (CollUtil.isNotEmpty(resArr))
        {
            for (String data : resArr)
            {
                if (data.contains("[DONE]"))
                {
                    break;
                }

                try
                {
                    if (StrUtil.isBlank(data) || !com.alibaba.fastjson.JSONObject.isValidObject(data))
                    {
                        continue;
                    }
                }
                catch (Exception e)
                {
                    continue;
                }
                try
                {
                    completionRes = JSON.parseObject(data, CompletionRes.class);
                    if (completionRes != null && CollUtil.isNotEmpty(completionRes.getChoices()))
                    {
                        for (Choices choice : completionRes.getChoices())
                        {
                            if (null != choice.getDelta())
                            {
                                sb.append(Optional.ofNullable(choice.getDelta().getContent()).orElse(""));
                                type = 1;
                            }
                            else if (null != choice.getMessage())
                            {
                                sb.append(Optional.ofNullable(choice.getMessage().getContent()).orElse(""));
                                type = 2;
                            }
                            else
                            {
                                sb.append(choice.getText());
                                type = 3;
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    log.error(">>>>completionsStream.response:{}", data);
                    log.error(">>>>completionsStream.response.parse.exception, e:", e);
                    throw new ApiException(JSON_ERROR, JSON_ERROR_MSG);

                }

            }
            if (completionRes != null)
            {

                Choices c = Choices.builder().finishReason(completionRes.getChoices().get(0).getFinishReason())
                        .index(completionRes.getChoices().get(0).getIndex())
                        .logprobs(completionRes.getChoices().get(0).getLogprobs()).build();
                if (type == 1)
                {
                    MessageReq msg = completionRes.getChoices().get(0).getDelta();
                    msg.setContent(sb.toString());
                    c.setDelta(msg);
                }
                else if (type == 2)
                {
                    MessageReq msg = completionRes.getChoices().get(0).getMessage();
                    msg.setContent(sb.toString());
                    c.setMessage(msg);
                }
                else if (type == 3)
                {
                    c.setText(sb.toString());
                }
                completionRes.setChoices(Collections.singletonList(c));
            }
        }
        return completionRes;
    }

}
