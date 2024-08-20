package com.cathay.bank.coindeskhelper.jobs;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.cathay.bank.coindeskhelper.db.entities.Bitcoin;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinRepo;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinTranslationRepo;
import com.cathay.bank.coindeskhelper.services.ICoindeskApiService;
import com.cathay.bank.coindeskhelper.vos.Currency;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;
import com.cathay.bank.coindeskhelper.vos.Time;

class CurrentPriceJobTest {
    @Mock
    private ICoindeskApiService service;

    @Mock
    private IBitcoinRepo bitcoinRepo;

    @Mock
    private IBitcoinTranslationRepo bitcoinTranslationRepo;

    @Mock
    private JobExecutionContext context;

    @InjectMocks
    private CurrentPriceJob job;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() throws JobExecutionException {
        CurrentPrice currentPrice = new CurrentPrice();
        Time time = new Time();
        time.setUpdated("Aug 20, 2024 06:18:12 UTC");
        time.setUpdatedIso("2024-08-20T06:18:12+00:00");
        time.setUpdatedUk("Aug 20, 2024 at 07:18 BST");
        currentPrice.setTime(time);
        Map<String, Currency> bpi = new HashMap<>();

        Currency usd = new Currency("USD", "$", "50,000", "Dollar", 50000f);
        bpi.put("USD", usd);
        Currency eur = new Currency("EUR", "€", "40,000", "Euro", 40000f);
        bpi.put("EUR", eur);
        currentPrice.setBpi(bpi);
        currentPrice.setChartName("chartName");
        currentPrice.setDisclaimer("disclaimer");

        when(service.getCurrentPrice()).thenReturn(currentPrice);

        Set<String> existingCodes = new HashSet<>(Arrays.asList("USD"));
        when(bitcoinRepo.findAll()).thenReturn(existingCodes.stream().map(code -> {
            Bitcoin bitcoin = new Bitcoin();
            bitcoin.setCode(code);
            return bitcoin;
        }).collect(Collectors.toList()));

        job.execute(context);

        verify(bitcoinRepo, times(1)).saveAll(anyList());
        verify(bitcoinRepo).saveAll(argThat((List<Bitcoin> bitcoins) -> {
            Bitcoin savedUSD = bitcoins.stream().filter(b -> b.getCode().equals("USD")).findFirst()
                    .orElse(null);
            Bitcoin savedEUR = bitcoins.stream().filter(b -> b.getCode().equals("EUR")).findFirst()
                    .orElse(null);

            return savedUSD != null && savedUSD.getUpdatedBy().equals(CurrentPriceJob.JOB_NAME)
                    && savedEUR != null && savedEUR.getCreatedBy().equals(CurrentPriceJob.JOB_NAME)
                    && savedEUR.getUpdatedBy() == null;
        }));
        verify(bitcoinTranslationRepo, times(1)).saveAll(anyList());
        verify(bitcoinTranslationRepo).saveAll(argThat((List<BitcoinTranslation> translations) -> {
            BitcoinTranslation savedUSDTranslation = translations.stream()
                    .filter(b -> b.getCode().equals("USD")).findFirst().orElse(null);
            BitcoinTranslation savedEURTranslation = translations.stream()
                    .filter(b -> b.getCode().equals("EUR")).findFirst().orElse(null);

            return savedUSDTranslation != null
                    && savedUSDTranslation.getUpdatedBy().equals(CurrentPriceJob.JOB_NAME)
                    && savedEURTranslation != null
                    && savedEURTranslation.getCreatedBy().equals(CurrentPriceJob.JOB_NAME)
                    && savedEURTranslation.getUpdatedBy() == null;
        }));
    }
}
