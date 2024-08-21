package com.cathay.bank.coindeskhelper.configurations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps.ApiProperties;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps.ApiProperties.TimeoutProperties;

@ExtendWith(MockitoExtension.class)
class WebClientConfigTest {
    @Mock
    private CoindeskhelperProps props;

    @InjectMocks
    private WebClientConfig webClientConfig;

    @Mock
    private CoindeskhelperProps.ApiProperties api;

    @Mock
    private CoindeskhelperProps.ApiProperties.TimeoutProperties timeout;

    @Test
    void webClientBuilder_shouldReturnConfiguredBuilder() {
        when(props.getApi()).thenReturn(api);
        when(api.getTimeout()).thenReturn(timeout);
        when(timeout.getConnection()).thenReturn(5);
        when(timeout.getRead()).thenReturn(10);
        when(timeout.getWrite()).thenReturn(15);
        Builder builder = webClientConfig.webClientBuilder();
        assertNotNull(builder);
    }

    @Test
    void webClient_shouldReturnConfiguredWebClient() {
        Builder mockBuilder = mock(WebClient.Builder.class);
        when(mockBuilder.build()).thenReturn(WebClient.builder().build());
        WebClient webClient = webClientConfig.webClient(mockBuilder);
        assertNotNull(webClient);
        verify(mockBuilder, times(1)).build();
    }
}
