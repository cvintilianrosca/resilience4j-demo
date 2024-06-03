package com.example.demo.aspect;

import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.time.LocalDateTime;

public abstract class AbstractAspect {

    private int numberOfCalls = 1;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Setter
    private String strategy;


    public void logGetDummyDto() {
        System.out.println();
        System.out.println(ANSI_GREEN + "---------------------------//----------------------------" + ANSI_GREEN);
        System.out.println(ANSI_GREEN + "Strategy: " + strategy + ANSI_GREEN);
        System.out.println(ANSI_GREEN + "API call to Microservice2 was made" + ANSI_GREEN);
        System.out.println(ANSI_GREEN + "Call number: " + numberOfCalls + ANSI_GREEN);
        System.out.println(ANSI_GREEN + "Timestamp: " + LocalDateTime.now());
        numberOfCalls++;
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void logFallback(ProceedingJoinPoint joinPoint) {
        System.out.println(ANSI_RED + "---------------------------//----------------------------" + ANSI_RED);
        System.out.println(ANSI_RED + "Strategy: " + strategy + ANSI_RED);
        System.out.println(ANSI_RED + "Method fallback was executed" + ANSI_RED);
        System.out.println(ANSI_RED + "Retry was executed after: " + numberOfCalls + "calls" + ANSI_RED);
        System.out.println(ANSI_RED + "Timestamp: " + LocalDateTime.now() + ANSI_RED);
        numberOfCalls = 1;

        final Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Exception exception) {
            System.out.println("Exception message: " + exception.getMessage());
        }

        System.out.println(ANSI_RED + "---------------------------//----------------------------" + ANSI_RED);
        System.out.println();
        System.out.println(ANSI_RESET + "                                  Next call " + ANSI_RESET);
        System.out.println();
        System.out.println();
        System.out.println();
    }

}
