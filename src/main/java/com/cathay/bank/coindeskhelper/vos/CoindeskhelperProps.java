package com.cathay.bank.coindeskhelper.vos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ConfigurationProperties(prefix = "props")
public class CoindeskhelperProps {
    private ApiProperties api;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ConfigurationProperties(prefix = "props.api")
    public static class ApiProperties {
        private TimeoutProperties timeout;
        private CoindeskProperties coindesk;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        @ConfigurationProperties(prefix = "props.api.timeout")
        public static class TimeoutProperties {
            private int connection;
            private int read;
            private int write;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        @Component
        public static class CoindeskProperties {
            @Value("${props.api.coindesk.baseuri}")
            private String baseUri;
            @Value("${props.api.coindesk.currentprice}")
            private String currentPrice;

            public String getCurrentPriceUrl() {
                return this.baseUri + this.currentPrice;
            }
        }
    }

}
