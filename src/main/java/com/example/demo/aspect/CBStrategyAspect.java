package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CBStrategyAspect extends AbstractAspect {


    @After("execution(* com.example.demo.service.impl.CircuitBreakerServiceImpl.applyCircuitBreakerStrategyToApiCall(..))")
    public void logGetDummyDto() {
        this.setStrategy("Circuit-Breaker");
        super.logGetDummyDto();
    }

    @Around("execution(* com.example.demo.service.impl.CircuitBreakerServiceImpl.fallback(..))")
    public Object logFallbackAfterRetry(ProceedingJoinPoint joinPoint) throws Throwable {
        super.logFallback(joinPoint);
        return joinPoint.proceed();
    }

}
