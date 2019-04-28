package com.clzmall.admin.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * Created by bairong on 2018/11/16.
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    long startTime = 0;

    @Pointcut("execution(public * com.clzmall.admin.service.impl*..*(..))")
    public void log() {
    }

    @Before("log()")
    public void beforeMethod(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getName();//全类名
        String methodName = joinPoint.getSignature().getName();//方法名
        Object[] args = joinPoint.getArgs(); // 参数值
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名
        StringBuffer sb = new StringBuffer();
        if (args.length != 0 && args.length == argNames.length) {
            for (int i = 0; i < argNames.length; i++) {
                sb.append(argNames[i]).append(" = ").append(args[i]).append(" ");
            }
        }
        log.info(className + "." + methodName + "(), " + sb.toString());
    }


    @AfterReturning(pointcut = "log()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) {
        long endTime = System.currentTimeMillis();
        log.info("耗时:{}ms, 返回参数：{}", endTime - startTime, JSON.toJSON(returnValue));
    }

}
