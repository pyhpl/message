package org.ljl.look.message.feign;

import org.ljl.look.message.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient("user")
public interface UserServiceFeign {

    // ************************ /api/user ************************ //
    @GetMapping("/api/user")
    User getUser(@RequestParam("openId") String openId);
}
