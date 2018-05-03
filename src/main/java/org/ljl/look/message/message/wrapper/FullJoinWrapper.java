package org.ljl.look.message.message.wrapper;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullJoinWrapper {
    private String openId;
    private String name;
    private String avatar;
    private String activityUuid;
    private String activityTitle;
    private String activityPublishUser;
}
