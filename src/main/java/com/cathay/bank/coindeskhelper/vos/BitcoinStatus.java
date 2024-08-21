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
public class BitcoinStatus {
    private String code;
    private int status;

    public String getCode() {
        return this.code.toUpperCase();
    }
}
