package com.cathay.bank.coindeskhelper.aops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.cathay.bank.coindeskhelper.db.repositories.IExecutionLogRepo;
import com.cathay.bank.coindeskhelper.utils.annotations.LogExecution;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class LogExecutionAspectTest {
    @Mock
    private IExecutionLogRepo repo;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @InjectMocks
    private LogExecutionAspect logExecutionAspect;

    @Test
    void logExecutionTime_shouldLogExecutionDetails_String() throws Throwable {
        Object[] args = new Object[] {"testArg"};
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringTypeName()).thenReturn("com.example.TestClass");
        when(methodSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(args);
        when(joinPoint.proceed(args)).thenReturn("testReturnValue");
        when(objectMapper.writeValueAsString(args)).thenReturn("[\"testArg\"]");
      
        Object result = logExecutionAspect.logExecutionTime(joinPoint, mock(LogExecution.class));
        assertEquals(result, "testReturnValue");

        verify(repo, times(1)).save(argThat(log -> {
            assertEquals(log.getClassName(), "com.example.TestClass");
            assertEquals(log.getMethodName(), "testMethod");
            assertEquals(log.getParameters(), "[\"testArg\"]");
            return true;
        }));
    }
    
    @Test
    void logExecutionTime_shouldLogExecutionDetails__Result_List() throws Throwable {
        Object[] args = new Object[] {"testArg"};
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringTypeName()).thenReturn("com.example.TestClass");
        when(methodSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(args);
        List<String> list = Arrays.asList("testReturnValue");
        when(joinPoint.proceed(args)).thenReturn(list);
        when(objectMapper.writeValueAsString(args)).thenReturn("[\"testArg\"]");
        when(objectMapper.writeValueAsString(list)).thenReturn("[\"testReturnValue\"]");

        Object result = logExecutionAspect.logExecutionTime(joinPoint, mock(LogExecution.class));
        assertEquals(result, list);

        verify(repo, times(1)).save(argThat(log -> {
            assertEquals(log.getClassName(), "com.example.TestClass");
            assertEquals(log.getMethodName(), "testMethod");
            assertEquals(log.getParameters(), "[\"testArg\"]");
            return true;
        }));
    }

}
