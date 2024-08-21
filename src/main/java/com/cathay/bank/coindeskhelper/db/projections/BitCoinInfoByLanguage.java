package com.cathay.bank.coindeskhelper.db.projections;

import java.time.LocalDateTime;

public interface BitCoinInfoByLanguage {
    public String getCode();

    public float getRateFloat();

    public String getName();

    public String getDescription();

    public LocalDateTime getUpdated();
}
