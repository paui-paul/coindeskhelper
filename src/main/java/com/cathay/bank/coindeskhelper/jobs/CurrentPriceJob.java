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
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinRepo;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinTranslationRepo;
import com.cathay.bank.coindeskhelper.services.ICoindeskApiService;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CurrentPriceJob implements Job {
    public static final String JOB_NAME = "CurrentPriceJob";
    private ICoindeskApiService service;
    private IBitcoinRepo bitcoinRepo;
    private IBitcoinTranslationRepo bitcoinTranslationRepo;

    public CurrentPriceJob(@Autowired ICoindeskApiService service,
            @Autowired IBitcoinRepo bitcoinRepo, IBitcoinTranslationRepo bitcoinTranslationRepo) {
        this.service = service;
        this.bitcoinRepo = bitcoinRepo;
        this.bitcoinTranslationRepo = bitcoinTranslationRepo;
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        CurrentPrice currentPrice = this.service.getCurrentPrice();
        Set<String> set = this.bitcoinRepo.findAll().stream().map(Bitcoin::getCode)
                .collect(Collectors.toSet());
        List<Bitcoin> bitcoins = currentPrice.getBpi().values().stream().map(x -> {
            Bitcoin bitcoin = new Bitcoin();
            bitcoin.setCode(x.getCode());
            bitcoin.setSymbol(x.getSymbol());
            bitcoin.setRate(x.getRate());
            bitcoin.setRateFloat(x.getRateFloat());
            bitcoin.setStatus(0);
            bitcoin.setUpdated(currentPrice.getTime().getUpdatedLocalDateTime());
            bitcoin.setUpdatedIso(currentPrice.getTime().getUpdatedIsoLocalDateTime());
            bitcoin.setUpdatedUk(currentPrice.getTime().getUpdatedUkLocalDateTime());

            if (set.contains(x.getCode())) {
                bitcoin.setUpdatedBy(JOB_NAME);
                bitcoin.setUpdatedTime(LocalDateTime.now());
            } else {
                bitcoin.setCreatedBy(JOB_NAME);
                bitcoin.setCreatedTime(LocalDateTime.now());
            }

            return bitcoin;
        }).collect(Collectors.toList());
        this.bitcoinRepo.saveAll(bitcoins);

        List<BitcoinTranslation> translations = currentPrice.getBpi().values().stream().map(x -> {
            BitcoinTranslation translation = new BitcoinTranslation();
            translation.setCode(x.getCode());
            translation.setLanguage("EN");
            translation.setName(x.getCode());
            translation.setDescription(x.getDescription());
            if (set.contains(x.getCode())) {
                translation.setUpdatedBy(JOB_NAME);
                translation.setUpdatedTime(LocalDateTime.now());
            } else {
                translation.setCreatedBy(JOB_NAME);
                translation.setCreatedTime(LocalDateTime.now());
            }
            return translation;
        }).collect(Collectors.toList());
        this.bitcoinTranslationRepo.saveAll(translations);
        log.info("call coindesk current price done");
    }

}
