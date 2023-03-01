package cn.jianwoo.openai.chatgptapi.service;

import cn.jianwoo.openai.chatgptapi.bo.CompletionReq;
import cn.jianwoo.openai.chatgptapi.bo.CompletionRes;
import cn.jianwoo.openai.chatgptapi.bo.EmbeddingsReq;
import cn.jianwoo.openai.chatgptapi.bo.EmbeddingsRes;
import cn.jianwoo.openai.chatgptapi.bo.EnginesDataRes;
import cn.jianwoo.openai.chatgptapi.bo.EnginesListRes;
import cn.jianwoo.openai.chatgptapi.bo.EventListRes;
import cn.jianwoo.openai.chatgptapi.bo.ModerationsReq;
import cn.jianwoo.openai.chatgptapi.bo.ModerationsRes;
import cn.jianwoo.openai.chatgptapi.bo.ObjDelRes;
import cn.jianwoo.openai.chatgptapi.bo.FileDetRes;
import cn.jianwoo.openai.chatgptapi.bo.FileListRes;
import cn.jianwoo.openai.chatgptapi.bo.FileReq;
import cn.jianwoo.openai.chatgptapi.bo.FineTuneListRes;
import cn.jianwoo.openai.chatgptapi.bo.FineTunesReq;
import cn.jianwoo.openai.chatgptapi.bo.FineTunesRes;
import cn.jianwoo.openai.chatgptapi.bo.ImageReq;
import cn.jianwoo.openai.chatgptapi.bo.ImageRes;
import cn.jianwoo.openai.chatgptapi.bo.ModelRes;
import cn.jianwoo.openai.chatgptapi.bo.ModelDataRes;
import cn.jianwoo.openai.chatgptapi.exception.ApiException;
import cn.jianwoo.openai.chatgptapi.stream.Callback;

/**
 * ChatGpt API服务
 *
 * @blog https://jianwoo.cn
 * @author gulihua
 * @github https://github.com/gulihua10010/
 * @bilibili 顾咕咕了
 * @date 2023-02-22 14:32
 */
public interface PostApiService
{

    /**
     * List models<br>
     * Lists the currently available models, and provides basic information about each one such as the owner and
     * availability.
     *
     * @author gulihua
     * @return ModelRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ModelRes models() throws ApiException;


    /**
     * Retrieve model<br>
     * Retrieves a model instance, providing basic information about the model such as the owner and permissioning.
     *
     * @author gulihua
     * @param modelName model
     * @return ModelDataRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ModelDataRes model(String modelName) throws ApiException;


    /**
     * Create completion<br>
     * Creates a completion for the provided prompt and parameters<br>
     * stream = false<br>
     * 
     * @author gulihua
     * @param req req
     * @return CompletionRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    CompletionRes completions(CompletionReq req) throws ApiException;


    /**
     * Create completion<br>
     * Creates a completion for the provided prompt and parameters<br>
     * stream = true<br>
     *
     * @author gulihua
     * @param req req
     * @param callback callback funcation
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    void completionsStream(CompletionReq req, Callback<CompletionRes> callback) throws ApiException;


    /**
     * Create edit<br>
     * Creates a new edit for the provided input, instruction, and parameters.<br>
     *
     * @author gulihua
     * @param req req
     * @return CompletionRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    CompletionRes completionsEdit(CompletionReq req) throws ApiException;


    /**
     * Create image<br>
     * Creates an image given a prompt.<br>
     *
     * @author gulihua
     * @param req req
     * @return ImageRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ImageRes imageCreate(ImageReq req) throws ApiException;


    /**
     * Create image edit<br>
     * Creates an edited or extended image given an original image and a prompt.<br>
     *
     * @author gulihua
     * @param req req
     * @return ImageRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ImageRes imageEdit(ImageReq req) throws ApiException;


    /**
     * Create image variation<br>
     * Creates a variation of a given image.<br>
     *
     * @author gulihua
     * @param req req
     * @return ImageRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ImageRes imageVariate(ImageReq req) throws ApiException;


    /**
     * Create embeddings<br>
     * Creates an embedding vector representing the input text.<br>
     *
     * @author gulihua
     * @param req req
     * @return EmbeddingsRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    EmbeddingsRes embeddingsCreate(EmbeddingsReq req) throws ApiException;


    /**
     * List files<br>
     * Returns a list of files that belong to the user's organization.<br>
     *
     * @author gulihua
     * @return FileListRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    FileListRes fileList() throws ApiException;


    /**
     * Upload file<br>
     * Upload a file that contains document(s) to be used across various endpoints/features. Currently, the size of all
     * the files uploaded by one organization can be up to 1 GB. Please contact us if you need to increase the storage
     * limit.<br>
     *
     * @author gulihua
     * @param req req
     * @return FileDetRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    FileDetRes fileUpload(FileReq req) throws ApiException;


    /**
     * Delete file<br>
     * Delete a file.<br>
     *
     * @author gulihua
     * @param fileId The ID of the file to use for this request
     * @return ObjDelRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ObjDelRes fileDelete(String fileId) throws ApiException;


    /**
     * Retrieve file<br>
     * Returns information about a specific file.<br>
     *
     * @author gulihua
     * @param fileId The ID of the file to use for this request
     * @return FileDetRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    FileDetRes fileRetrieve(String fileId) throws ApiException;


    /**
     * Retrieve file content<br>
     * Returns the contents of the specified file.<br>
     *
     * @author gulihua
     * @param fileId The ID of the file to use for this request
     * @return String
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    String fileRetrieveContent(String fileId) throws ApiException;


    /**
     * Create fine-tune<br>
     * Creates a job that fine-tunes a specified model from a given dataset.<br>
     *
     * @author gulihua
     * @param req req
     * @return FineTunesRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    FineTunesRes fineTuneCreate(FineTunesReq req) throws ApiException;


    /**
     * List fine-tunes<br>
     * List your organization's fine-tuning jobs.<br>
     *
     * @author gulihua
     * @return FineTuneListRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    FineTuneListRes fineTuneList() throws ApiException;


    /**
     * Retrieve fine-tune<br>
     * Gets info about the fine-tune job.<br>
     *
     * @author gulihua
     * @param fineTuneId The ID of the fine-tune job to get events for.
     * @return FineTunesRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    FineTunesRes fineTuneRetrieve(String fineTuneId) throws ApiException;


    /**
     * Cancel fine-tune<br>
     * Immediately cancel a fine-tune job.<br>
     *
     * @author gulihua
     * @param fineTuneId The ID of the fine-tune job to get events for.
     * @return FineTunesRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    FineTunesRes fineTuneCancel(String fineTuneId) throws ApiException;


    /**
     * List fine-tune events<br>
     * Get fine-grained status updates for a fine-tune job.<br>
     *
     * @author gulihua
     * @param fineTuneId The ID of the fine-tune job to get events for.
     * @return FineTunesRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    EventListRes fineTuneEventList(String fineTuneId) throws ApiException;


    /**
     * Delete fine-tune model<br>
     * Delete a fine-tuned model. You must have the Owner role in your organization.<br>
     *
     * @author gulihua
     * @param model The model to delete
     * @return FineTunesRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ObjDelRes fineTuneDelete(String model) throws ApiException;


    /**
     * Create moderation<br>
     * Classifies if text violates OpenAI's Content Policy<br>
     *
     * @author gulihua
     * @param req req
     * @return ModerationsRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    ModerationsRes moderationsCreate(ModerationsReq req) throws ApiException;


    /**
     * List engines<br>
     * Lists the currently available (non-finetuned) models, and provides basic information about each one such as the
     * owner and availability.<br>
     *
     * @author gulihua
     * @return EnginesListRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    EnginesListRes enginesList() throws ApiException;


    /**
     * Retrieve engine<br>
     * Retrieves a model instance, providing basic information about it such as the owner and availability.<br>
     *
     * @author gulihua
     * @param engineId The ID of the engine to use for this request
     * @return FineTunesRes
     * @throws ApiException <br>
     *             --400001 未授权 <br>
     *             --500001 响应JSON错误 <br>
     *             --800001 业务错误 <br>
     *             --900001 其他错误 <br>
     **/
    EnginesDataRes enginesRetrieve(String engineId) throws ApiException;
}
