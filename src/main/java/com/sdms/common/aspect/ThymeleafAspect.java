package com.sdms.common.aspect;

import com.sdms.service.SessionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Aspect
public class ThymeleafAspect {

    @Resource
    private SessionService sessionService;

    @Around("execution(public String com.sdms.controller.*Controller.to*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object proceed = joinPoint.proceed();
        sessionService.refreshNoHandleCount();
        return proceed;
    }
}
