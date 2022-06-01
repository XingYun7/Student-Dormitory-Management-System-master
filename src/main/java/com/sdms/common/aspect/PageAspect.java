package com.sdms.common.aspect;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PageAspect {

    @Pointcut("@annotation(com.sdms.common.annotation.PageRequestCheck)")
    public void withAnnotation() {
    }

    @Pointcut("execution(public com.sdms.common.page.Page com.sdms.service.impl.*.*(com.sdms.common.page.PageRequest))")
    public void matchMethod() {
    }

    @Around("withAnnotation()||matchMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        val pageRequest = (PageRequest) joinPoint.getArgs()[0];
        val limit = pageRequest.getLimit();
        val offset = (pageRequest.getPage() - 1) * limit;
        if (offset < 0 || limit <= 0) {
            return Page.empty();
        } else {
            return joinPoint.proceed();
        }
    }
}
