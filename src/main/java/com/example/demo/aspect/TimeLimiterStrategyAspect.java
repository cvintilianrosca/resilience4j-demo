package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeLimiterStrategyAspect extends AbstractAspect {

    @After("execution(* com.example.demo.service.impl.TimeLimiterServiceImpl.applyTimeLimiterStrategy(..))")
    public void logGetDummyDto() {
        this.setStrategy("Time-Limiter");
        super.logGetDummyDto();
    }

    @Around("execution(* com.example.demo.service.impl.TimeLimiterServiceImpl.fallback(..))")
    public Object logFallbackAfterRetry(ProceedingJoinPoint joinPoint) throws Throwable {
        super.logFallback(joinPoint);
        return joinPoint.proceed();
    }
}
