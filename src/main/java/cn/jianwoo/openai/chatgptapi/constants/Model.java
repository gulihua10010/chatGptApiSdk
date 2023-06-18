package cn.jianwoo.openai.chatgptapi.constants;

/**
 *
 * 常用的模型枚举 <br>
 * 虽然PostApiService.models 接口中返回了69 中模型，这里只列举
 * <a href="https://platform.openai.com/docs/models/">https://platform.openai.com/docs/models/</a> 中所常用的几个模型<br>
 *
 *
 * @author gulihua
 *
 */
public enum Model {
    /** Most capable GPT-3 model. Can do any task the other models can do, often with higher quality. */
    DAVINCI("davinci"),
    /**
     * Can do any language task with better quality, longer output, and consistent instruction-following than the curie,
     * babbage, or ada models. Also supports inserting completions within text.
     */
    TEXT_DAVINCI_003("text-davinci-003"),
    /** text-davinci-001 */
    TEXT_DAVINCI_001("text-davinci-001"),
    /**
     * Most capable Codex model. Particularly good at translating natural language to code. In addition to completing
     * code, also supports inserting completions within code.
     */
    CODE_DAVINCI_002("code-davinci-002"),
    /** Very capable, faster and lower cost than Davinci. */
    TEXT_CURIE_001("text-curie-001"),
    /** Very capable, but faster and lower cost than Davinci. */
    CURIE("curie"),
    /** Capable of straightforward tasks, very fast, and lower cost. */
    TEXT_BABBAGE_001("text-babbage-001"),
    /** Capable of very simple tasks, usually the fastest model in the GPT-3 series, and lowest cost. */
    TEXT_ADA_001("text-ada-001"),
    /** Capable of very simple tasks, usually the fastest model in the GPT-3 series, and lowest cost. */
    ADA("ada"),
    /** davinci-instruct-beta */
    DAVINCI_INSTRUCT_BETA("davinci-instruct-beta"),
    /** curie-instruct-beta */
    CURIE_INSTRUCT_BETA("curie-instruct-beta"),
    /**
     * Almost as capable as Davinci Codex, but slightly faster. This speed advantage may make it preferable for
     * real-time applications.
     */
    CODE_CUSHMAN_001("code-cushman-001"),
    /** Capable of straightforward tasks, very fast, and lower cost. */
    BABBAGE("babbage"),
    /**
     * Similar capabilities to text-davinci-003 but trained with supervised fine-tuning instead of reinforcement
     * learning
     */
    TEXT_DAVINCI_002("text-davinci-002"),
    /**
     * Most capable GPT-3.5 model and optimized for chat at 1/10th the cost of text-davinci-003. Will be updated with
     * our latest model iteration.<br>
     * MAX TOKENS: 4,096 tokens
     */
    GPT_35_TURBO("gpt-3.5-turbo"),
    /**
     * Snapshot of gpt-3.5-turbo from March 1st 2023. Unlike gpt-3.5-turbo, this model will not receive updates, and
     * will only be supported for a three month period ending on June 1st 2023.
     */
    GPT_35_TURBO_0301("gpt-3.5-turbo-0301"),
    /**
     * Same capabilities as the standard gpt-3.5-turbo model but with 4 times the context. <br>
     * MAX TOKENS: 16,384 tokens
     */
    GPT_35_TURBO_16K("gpt-3.5-turbo-16k"),

    /**
     * Snapshot of gpt-3.5-turbo from June 13th 2023 with function calling data. Unlike gpt-3.5-turbo, this model will
     * not receive updates, and will be deprecated 3 months after a new version is released.<br>
     * MAX TOKENS: 4,096 tokens
     */
    GPT_35_TURBO_0613("gpt-3.5-turbo-0613"),

    /**
     * Snapshot of gpt-3.5-turbo-16k from June 13th 2023. Unlike gpt-3.5-turbo-16k, this model will not receive updates,
     * and will be deprecated 3 months after a new version is released.<br>
     * MAX TOKENS: 16,384 tokens
     */
    GPT_35_TURBO_16K_0613("gpt-3.5-turbo-16k-0613"),

    /** Whisper is a general-purpose speech recognition model. */
    WHISPER_1("whisper-1"),
    /**
     * Embeddings are a numerical representation of text that can be used to measure the relateness between two pieces
     * of text.
     */
    TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
    /**
     * More capable than any GPT-3.5 model, able to do more complex tasks, and optimized for chat. Will be updated with
     * our latest model iteration.
     */
    GPT_4("gpt-4"),
    /**
     * Snapshot of gpt-4 from March 14th 2023. Unlike gpt-4, this model will not receive updates, and will only be
     * supported for a three month period ending on June 14th 2023.<br>
     * MAX TOKENS: 8,192 tokens
     */
    GPT_4_0314("gpt-4-0314"),
    /**
     * Same capabilities as the base gpt-4 mode but with 4x the context length. Will be updated with our latest model
     * iteration.<br>
     * MAX TOKENS: 32,768 tokens
     */
    GPT_4_32K("gpt-4-32k"),
    /**
     * Snapshot of gpt-4-32 from March 14th 2023. Unlike gpt-4-32k, this model will not receive updates, and will only
     * be supported for a three month period ending on June 14th 2023.
     */
    GPT_4_32K_0314("gpt-4-32k-0314"),
    /**
     * Snapshot of gpt-4 from June 13th 2023 with function calling data. Unlike gpt-4, this model will not receive
     * updates, and will be deprecated 3 months after a new version is released.<br>
     * MAX TOKENS: 8,192 tokens
     */
    GPT_4_0613("gpt-4-0613"),

    /**
     * Snapshot of gpt-4-32 from June 13th 2023. Unlike gpt-4-32k, this model will not receive updates, and will be
     * deprecated 3 months after a new version is released.<br>
     * MAX TOKENS: 32,768 tokens
     */
    GPT_4_32K_0613("gpt-4-32k-0613"),;

    private String name;

    public String getName()
    {
        return this.name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    Model(String name)
    {
        this.name = name;
    }
}
