package com.cathay.bank.coindeskhelper.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import com.cathay.bank.coindeskhelper.vos.CoindeskhelperProps;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    private CoindeskhelperProps props;

    public WebClientConfig(@Autowired CoindeskhelperProps props) {
        this.props = props;
    }

    @Bean
    WebClient.Builder webClientBuilder() {
        int connTimeout = this.props.getApi().getTimeout().getConnection() * 1000;
        int readTimeout = this.props.getApi().getTimeout().getRead();
        int writeTimeout = this.props.getApi().getTimeout().getWrite();
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connTimeout)
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout))
                        .addHandlerLast(new WriteTimeoutHandler(writeTimeout)));
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient));

    }

    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }
}
