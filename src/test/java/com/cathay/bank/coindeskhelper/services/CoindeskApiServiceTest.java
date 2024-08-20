package com.cathay.bank.coindeskhelper.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import com.cathay.bank.coindeskhelper.services.impl.CoindeskApiService;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;
import reactor.core.publisher.Mono;

class CoindeskApiServiceTest {
    @Mock
    private WebClient webClient;

    @Mock
    private CoindeskhelperProps props;

    @InjectMocks
    private CoindeskApiService coindeskApiService;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetCurrentPrice() {
        CoindeskhelperProps.ApiProperties api = mock(CoindeskhelperProps.ApiProperties.class);
        CoindeskhelperProps.ApiProperties.CoindeskProperties coindesk =
                mock(CoindeskhelperProps.ApiProperties.CoindeskProperties.class);

        when(props.getApi()).thenReturn(api);
        when(api.getCoindesk()).thenReturn(coindesk);
        when(coindesk.getCurrentPriceUrl()).thenReturn("url");

        CurrentPrice expectedCurrentPrice = new CurrentPrice(); 
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CurrentPrice.class))
                .thenReturn(Mono.just(expectedCurrentPrice));

        CurrentPrice actualCurrentPrice = coindeskApiService.getCurrentPrice();

        assertEquals(expectedCurrentPrice, actualCurrentPrice);
        verify(requestHeadersUriSpec).uri("url");
    }
}
