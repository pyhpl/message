package org.ljl.look.message.aspect.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.ljl.look.message.configuration.ConstConfig;
import org.ljl.look.message.util.JsonTool;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class PageInfoAspect {

    @Pointcut("execution(public * org.ljl.look.message.service.MessageService.get*(..))")
    public void getMessage(){}

    @Before("getMessage()")
    public void doBeforeAdd(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            if (request.getQueryString().contains(ConstConfig.PAGE_INFO_JSON_STR)) {
                Map<String, String> pageInfoMap = JsonTool.fromJson(request.getParameter(ConstConfig.PAGE_INFO_JSON_STR), new TypeReference<Map<String, String>>() {});
                PageHelper.startPage(
                        Integer.valueOf(pageInfoMap.getOrDefault(ConstConfig.PAGE_NUM, ConstConfig.DEFAULT_PAGE_NUM)),
                        Integer.valueOf(pageInfoMap.getOrDefault(ConstConfig.PAGE_SIZE, ConstConfig.DEFAULT_PAGE_SIZE))
                );
                if (pageInfoMap.containsKey("orderBy")) {
                    PageHelper.orderBy(pageInfoMap.get("orderBy"));
                }
            }
        }
        else {
            PageHelper.startPage(1, 10);
            PageHelper.orderBy("send_date DESC");
        }
    }

}
