package cn.jianwoo.openai.chatgptapi.exception;

/**
 * @author GuLihua
 * @Description
 * @date 2020-08-04 14:21
 */
public class ApiException extends Exception
{
    public static final ApiException SYSTEM_ERROR = new ApiException("999999", "服务出错!");
    private static final long serialVersionUID = -4668522971027224346L;
    protected String code;
    protected String msg;

    public ApiException()
    {
    }


    public ApiException(String code, String msg, Object... args)
    {
        super(args == null || args.length == 0 ? msg : String.format(msg, args));
        this.code = code;
        this.msg = args == null || args.length == 0 ? msg : String.format(msg, args);
    }


    public ApiException(String message)
    {
        super(message);
        this.msg = message;
    }


    public ApiException(String message, Throwable cause)
    {
        super(message, cause);
        this.msg = message;

    }


    public ApiException(Throwable cause)
    {
        super(cause);
    }


    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        this.msg = message;

    }


    public ApiException getNewInstance(String code, String msg, Object... args)
    {
        return new ApiException(code, msg, args);
    }


    public ApiException formatMsg(String msg, Object... args)
    {
        return new ApiException(code, msg, args);
    }


    public String getCode()
    {
        return code;
    }


    public void setCode(String code)
    {
        this.code = code;
    }


    public String getMsg()
    {
        return msg;
    }


    public void setMsg(String msg)
    {
        this.msg = msg;
    }


    public ApiException format(Object... args)
    {
        return new ApiException(this.code, this.msg, args);
    }
}
