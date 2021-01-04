package com.chaisheng6.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(* com.chaisheng6.web.*(..))")
    public void log(){


    }
}
