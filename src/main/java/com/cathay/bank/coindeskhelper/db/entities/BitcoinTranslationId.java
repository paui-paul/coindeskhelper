package com.cathay.bank.coindeskhelper.db.entities;

import java.io.Serializable;
import lombok.Data;

@Data
public class BitcoinTranslationId implements Serializable {
    private String code;
    private String language;
}
