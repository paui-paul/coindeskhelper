package com.cathay.bank.coindeskhelper.vos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BitcoinTranslationSetting {
    private String code;
    private BitcoinLanguage language;
    private String name;
    private String description;
}
