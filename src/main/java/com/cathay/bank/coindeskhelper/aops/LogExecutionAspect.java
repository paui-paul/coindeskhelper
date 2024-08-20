package com.cathay.bank.coindeskhelper.aops;

import java.time.LocalDateTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.cathay.bank.coindeskhelper.db.entities.ExecutionLog;
import com.cathay.bank.coindeskhelper.db.repositories.IExecutionLogRepo;
import com.cathay.bank.coindeskhelper.utils.annotations.LogExecution;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class LogExecutionAspect {
    private IExecutionLogRepo repo;
    private ObjectMapper objectMapper;

    public LogExecutionAspect(IExecutionLogRepo repo, ObjectMapper objectMapper) {
        this.repo = repo;
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(logExecution)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecution logExecution)
            throws Throwable {
        LocalDateTime startTime = LocalDateTime.now();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();

        Object returnValue = joinPoint.proceed(args);

        LocalDateTime endTime = LocalDateTime.now();
        long duration = java.time.Duration.between(startTime, endTime).toMillis();
        String returnValueAsString;
        if (returnValue == null) {
            returnValueAsString = "void";
        } else if (isPrimitiveOrWrapper(returnValue.getClass()) || returnValue instanceof String) {
            returnValueAsString = returnValue.toString();
        } else {
            returnValueAsString = objectMapper.writeValueAsString(returnValue);
        }
        ExecutionLog executionLog = new ExecutionLog();
        executionLog.setClassName(className);
        executionLog.setMethodName(methodName);
        executionLog.setParameters(objectMapper.writeValueAsString(args));
        executionLog.setReturnValue(returnValueAsString);
        executionLog.setStartTime(startTime);
        executionLog.setEndTime(endTime);
        executionLog.setDuration(duration);

        repo.save(executionLog);

        return returnValue;
    }

    private boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() || type == Integer.class || type == Long.class
                || type == Double.class || type == Boolean.class || type == Byte.class
                || type == Character.class || type == Short.class || type == Float.class;
    }
}
