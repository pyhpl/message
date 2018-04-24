package org.ljl.look.message.mapper.sql;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageSql {

    public String selectCount(Map params) {
        return new SQL() {{
            SELECT("count(*)");
            FROM("message");
            if (params.get("toUser") != null) {
                WHERE("to_user=#{toUser}").AND();
            }
            if (params.get("isRead") != null) {
                WHERE("is_read=#{isRead}").AND();
            }
            WHERE("valid=1");
        }}.toString();
    }

    public String select(Map params) {
        return new SQL() {{
            SELECT("*");
            FROM("message");
            if (params.get("toUser") != null) {
                WHERE("to_user=#{toUser}").AND();
            }
            if (params.get("isRead") != null) {
                WHERE("is_read=#{isRead}").AND();
            }
            WHERE("valid=1");
        }}.toString();
    }

}
