package com.cathay.bank.coindeskhelper.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.cathay.bank.coindeskhelper.services.ICoindeskApiService;
import com.cathay.bank.coindeskhelper.utils.annotations.LogExecution;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;

@Component
public class CoindeskApiService implements ICoindeskApiService {
    private WebClient webClient;

    private CoindeskhelperProps props;

    public CoindeskApiService(@Autowired WebClient webClient,
            @Autowired CoindeskhelperProps props) {
        this.webClient = webClient;
        this.props = props;
    }

    @LogExecution
    @Override
    public CurrentPrice getCurrentPrice() {
        String uri = this.props.getApi().getCoindesk().getCurrentPriceUrl();
        return this.webClient.get().uri(uri).retrieve().bodyToMono(CurrentPrice.class).block();
    }

}
