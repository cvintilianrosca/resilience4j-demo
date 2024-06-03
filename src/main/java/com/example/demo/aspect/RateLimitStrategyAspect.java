package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimitStrategyAspect extends AbstractAspect {

    @After("execution(* com.example.demo.service.impl.RateLimterServiceImpl.applyRateLimiterStrategyToApiCall(..))")
    public synchronized void logGetDummyDto() {
        this.setStrategy("Rate-Limit");
        super.logGetDummyDto();
    }

    @Around("execution(* com.example.demo.service.impl.RateLimterServiceImpl.fallback(..))")
    public synchronized Object logFallbackAfterRetry(ProceedingJoinPoint joinPoint) throws Throwable {
        super.logFallback(joinPoint);
        return joinPoint.proceed();
    }
}
