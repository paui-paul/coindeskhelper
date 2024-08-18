package com.cathay.bank.coindeskhelper.vos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CurrentPriceConvert {
    private LocalDateTime updated;
    private List<Currency> currencies;

    public static final Function<CurrentPrice, CurrentPriceConvert> Converter = x -> {
        List<Currency> list = x.getBpi().values().stream().collect(Collectors.toList());
        return CurrentPriceConvert.builder()
                    .updated(x.getTime().getUpdated())
                    .currencies(list)
                    .build();
    };
}
