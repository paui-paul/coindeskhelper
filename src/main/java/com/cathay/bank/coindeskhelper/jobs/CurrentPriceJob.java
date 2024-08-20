package com.cathay.bank.coindeskhelper.jobs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cathay.bank.coindeskhelper.db.entities.Bitcoin;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinRepo;
import com.cathay.bank.coindeskhelper.services.ICoindeskApiService;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CurrentPriceJob implements Job {
    public static final String JOB_NAME = "CurrentPriceJob";
    private ICoindeskApiService service;
    private IBitcoinRepo repo;

    public CurrentPriceJob(@Autowired ICoindeskApiService service, @Autowired IBitcoinRepo repo) {
        this.service = service;
        this.repo = repo;
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        CurrentPrice currentPrice = this.service.getCurrentPrice();
        Set<String> set =
                this.repo.findAll().stream().map(Bitcoin::getCode).collect(Collectors.toSet());
        List<Bitcoin> list = currentPrice.getBpi().values().stream().map(x -> {
            Bitcoin bitcoin = new Bitcoin();
            bitcoin.setCode(x.getCode());
            bitcoin.setSymbol(x.getSymbol());
            bitcoin.setRate(x.getRate());
            bitcoin.setDescription(x.getDescription());
            bitcoin.setRateFloat(x.getRateFloat());
            bitcoin.setStatus(0);
            bitcoin.setUpdated(currentPrice.getTime().getUpdated());

            if (!set.contains(x.getCode())) {
                bitcoin.setCreatedBy(JOB_NAME);
                bitcoin.setCreatedTime(LocalDateTime.now());
            } else {
                bitcoin.setUpdatedBy(JOB_NAME);
                bitcoin.setCreatedTime(LocalDateTime.now());
            }

            return bitcoin;
        }).collect(Collectors.toList());
        this.repo.saveAll(list);
        log.info("call coindesk current price done");
    }

}
