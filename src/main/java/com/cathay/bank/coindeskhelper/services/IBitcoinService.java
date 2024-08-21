package com.cathay.bank.coindeskhelper.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cathay.bank.coindeskhelper.db.entities.Bitcoin;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinStatus;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;

@Service
public interface IBitcoinService {
    List<BitCoinInfoByLanguage> findBitCoinInfoByLanguage(String language);

    BitcoinTranslation addOrUpdateTranslation(BitcoinTranslationSetting setting)
            throws BitcoinException;

    boolean deleteTranslation(String code, String language) throws BitcoinException;

    Bitcoin updateStatus(BitcoinStatus status) throws BitcoinException;
}
