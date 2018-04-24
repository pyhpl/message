package org.ljl.look.message.message.wrapper;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageWrapper<T> {

    private MessageMethod method;
    private T body;

    public enum  MessageMethod {
        GET, PUT, DELETE, POST
    }
}
