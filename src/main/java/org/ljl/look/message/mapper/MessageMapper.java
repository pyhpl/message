package org.ljl.look.message.mapper;

import org.apache.ibatis.annotations.*;
import org.ljl.look.message.entity.Message;
import org.ljl.look.message.mapper.sql.MessageSql;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    @SelectProvider(type = MessageSql.class, method = "selectCount")
    int selectCountByToUserAndIsRead(@Param("toUser") String toUser, @Param("isRead") Boolean isRead);

    @SelectProvider(type = MessageSql.class, method = "select")
    List<Message> selectByToUserAndIsRead(@Param("toUser") String toUser, @Param("isRead") Boolean isRead);

    @Insert("INSERT INTO message VALUES(#{uuid}::uuid, #{type}, #{fromUser}, #{toUser}, #{title}, #{content}, #{isRead}, #{sendDate}, #{valid})")
    void insert(Message message);
}
