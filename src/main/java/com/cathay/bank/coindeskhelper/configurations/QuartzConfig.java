package com.cathay.bank.coindeskhelper.configurations;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cathay.bank.coindeskhelper.jobs.CurrentPriceJob;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps;

@Configuration
public class QuartzConfig {
    private CoindeskhelperProps props;

    public QuartzConfig(@Autowired CoindeskhelperProps props) {
        this.props = props;
    }

    @Bean
    JobDetail jobDetail() {
        return JobBuilder.newJob(CurrentPriceJob.class).withIdentity(CurrentPriceJob.JOB_NAME)
                .storeDurably().build();
    }

    @Bean
    Trigger jobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(this.props.getSchedule().getInterval()).repeatForever();

        return TriggerBuilder.newTrigger().forJob(jobDetail())
                .withIdentity(CurrentPriceJob.JOB_NAME).withSchedule(scheduleBuilder).build();
    }
}
