package com.cathay.bank.coindeskhelper.vos;

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
    private ScheduleProperties schedule;

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
        @ConfigurationProperties(prefix = "props.api.coindesk")
        public static class CoindeskProperties {
            private String baseUri;
            private String currentPrice;

            public String getCurrentPriceUrl() {
                return this.baseUri + this.currentPrice;
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ConfigurationProperties(prefix = "props.schedule")
    public static class ScheduleProperties {
        private int interval;
    }
}
