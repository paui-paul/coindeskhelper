package com.cathay.bank.coindeskhelper.services;

import org.springframework.stereotype.Service;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;

@Service
public interface IBitcoinService {
    BitcoinTranslation addOrUpdateTranslation(BitcoinTranslationSetting setting)
            throws BitcoinException;
}
