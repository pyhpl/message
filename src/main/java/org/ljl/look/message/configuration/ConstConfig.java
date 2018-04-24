package org.ljl.look.message.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstConfig {
    public static final short VALID = (short) 1;
    public static final short INVALID = (short) 0;

    /** page info */
    public static final String PAGE_INFO_JSON_STR = "pageInfoJsonStr";
    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";
    public static final String DEFAULT_PAGE_NUM = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";

    /** rabbitmq */
    public static final String QUEUE_MESSAGE = "queueMessage";
}
