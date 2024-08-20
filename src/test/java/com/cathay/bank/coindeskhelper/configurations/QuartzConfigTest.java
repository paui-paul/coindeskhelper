package com.cathay.bank.coindeskhelper.configurations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.cathay.bank.coindeskhelper.jobs.CurrentPriceJob;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps;

class QuartzConfigTest {
    @Mock
    private CoindeskhelperProps props;

    @InjectMocks
    private QuartzConfig quartzConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testJobDetail() {
        JobDetail jobDetail = quartzConfig.jobDetail();

        // 驗證結果
        assertNotNull(jobDetail);
        assertEquals(CurrentPriceJob.class, jobDetail.getJobClass());
        assertEquals(CurrentPriceJob.JOB_NAME, jobDetail.getKey().getName());
    }

    @Test
    void testSampleJobTrigger() {
        CoindeskhelperProps.ScheduleProperties schedule = new CoindeskhelperProps.ScheduleProperties();
        schedule.setInterval(10); 
        when(props.getSchedule()).thenReturn(schedule);

        Trigger trigger = quartzConfig.jobTrigger();
        assertNotNull(trigger);
        assertEquals(CurrentPriceJob.JOB_NAME, trigger.getJobKey().getName());
        
        assertTrue(trigger instanceof SimpleTrigger);
        SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
        assertEquals(10 * 1000, simpleTrigger.getRepeatInterval());
    }

}
