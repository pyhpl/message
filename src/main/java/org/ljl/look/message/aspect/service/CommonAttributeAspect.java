package org.ljl.look.message.aspect.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.ljl.look.message.configuration.ConstConfig;
import org.ljl.look.message.entity.Message;
import org.ljl.look.message.util.UuidTool;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class CommonAttributeAspect {

    @Pointcut("execution(public * org.ljl.look.message.service.MessageService.add(..))")
    public void addMessage(){}

    @Before("addMessage()")
    public void doBeforeAdd(JoinPoint joinPoint) throws Exception {
        Object arg = joinPoint.getArgs()[0];
        if (arg instanceof Message) { // topic
            Message message = (Message) arg;
            message.setUuid(UuidTool.getValue());
            message.setRead(false);
            message.setSendDate(new Date());
            message.setValid(ConstConfig.VALID);
        }
    }
}














