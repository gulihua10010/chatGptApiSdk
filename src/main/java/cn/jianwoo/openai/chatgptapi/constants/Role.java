package cn.jianwoo.openai.chatgptapi.constants;

/**
 *
 * MessageReq.role 定义的枚举 <br>
 * <a href=
 * "https://platform.openai.com/docs/guides/chat/introduction">https://platform.openai.com/docs/guides/chat/introduction</a>
 *
 * @author gulihua
 * 
 */
public enum Role {
    /** 系统消息 */
    SYSTEM("system"),
    /** 用户消息 */
    USER("user"),
    /** 助手消息 */
    ASSISTANT("assistant"),;

    private String name;

    public String getName()
    {
        return this.name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    Role(String name)
    {
        this.name = name;
    }
}
