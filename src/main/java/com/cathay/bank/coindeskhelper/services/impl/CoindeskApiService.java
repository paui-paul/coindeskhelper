package com.cathay.bank.coindeskhelper.services.impl;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.cathay.bank.coindeskhelper.services.ICoindeskApiService;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;
import com.cathay.bank.coindeskhelper.vos.CurrentPriceConvert;

@Component
public class CoindeskApiService implements ICoindeskApiService {
    private WebClient webClient;

    private CoindeskhelperProps props;

    public CoindeskApiService(@Autowired WebClient webClient,
            @Autowired CoindeskhelperProps props) {
        this.webClient = webClient;
        this.props = props;
    }

    @Override
    public CurrentPriceConvert getCurrentPrice() {
        String uri = this.props.getApi().getCoindesk().getCurrentPriceUrl();
        CurrentPrice currentPrice = this.webClient.get().uri(uri).retrieve().bodyToMono(CurrentPrice.class).block();
        return CurrentPriceConvert.builder().updated(currentPrice.getTime().getUpdated())
                .currencies(currentPrice.getBpi().values().stream().collect(Collectors.toList()))
                .build();
    }

}
