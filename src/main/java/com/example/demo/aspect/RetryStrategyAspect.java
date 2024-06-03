package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryStrategyAspect extends AbstractAspect {

    @After("execution(* com.example.demo.service.impl.RetryServiceImpl.applyRetryStrategyToApiCall(..))")
    public void logGetDummyDto() {
        this.setStrategy("Retry");
        super.logGetDummyDto();
    }

    @Around("execution(* com.example.demo.service.impl.RetryServiceImpl.fallbackAfterRetry(..))")
    public Object logFallbackAfterRetry(ProceedingJoinPoint joinPoint) throws Throwable {
        super.logFallback(joinPoint);
        return joinPoint.proceed();
    }
}
