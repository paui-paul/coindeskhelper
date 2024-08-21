package com.cathay.bank.coindeskhelper.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cathay.bank.coindeskhelper.db.entities.Bitcoin;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslationId;
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinRepo;
import com.cathay.bank.coindeskhelper.db.repositories.IBitcoinTranslationRepo;
import com.cathay.bank.coindeskhelper.services.IBitcoinService;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinStatus;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;

@Component
public class BitcoinService implements IBitcoinService {
    private IBitcoinRepo bitcoinRepo;
    private IBitcoinTranslationRepo bitcoinTranslationRepo;

    public BitcoinService(@Autowired IBitcoinRepo bitcoinRepo,
            @Autowired IBitcoinTranslationRepo bitcoinTranslationRepo) {
        this.bitcoinRepo = bitcoinRepo;
        this.bitcoinTranslationRepo = bitcoinTranslationRepo;
    }

    @Override
    public List<BitCoinInfoByLanguage> findBitCoinInfoByLanguage(String language) {
        return this.bitcoinRepo.findBitcoinInfoByLanguage(language.toUpperCase());
    }

    @Override
    public BitcoinTranslation addOrUpdateTranslation(BitcoinTranslationSetting setting)
            throws BitcoinException {
        if (!this.bitcoinRepo.existsById(setting.getCode()))
            throw new BitcoinException("code: " + setting.getCode() + " not exists");

        BitcoinTranslationId id = new BitcoinTranslationId();
        id.setCode(setting.getCode());
        id.setLanguage(setting.getLanguage());
        Optional<BitcoinTranslation> optional = this.bitcoinTranslationRepo.findById(id);
        if (optional.isPresent()) {
            BitcoinTranslation translation = optional.get();
            translation.setName(setting.getName());
            translation.setDescription(setting.getDescription());
            translation.setUpdatedTime(LocalDateTime.now());
            return this.bitcoinTranslationRepo.save(translation);
        } else {
            BitcoinTranslation translation = new BitcoinTranslation();
            translation.setCode(setting.getCode());
            translation.setLanguage(setting.getLanguage());
            translation.setName(setting.getName());
            translation.setDescription(setting.getDescription());
            translation.setCreatedTime(LocalDateTime.now());
            return this.bitcoinTranslationRepo.save(translation);
        }
    }

    @Override
    public boolean deleteTranslation(String code, String language) throws BitcoinException {
        if (!this.bitcoinRepo.existsById(code.toUpperCase()))
            throw new BitcoinException("code: " + code + " not exists");

        BitcoinTranslationId id = new BitcoinTranslationId();
        id.setCode(code.toUpperCase());
        id.setLanguage(language.toUpperCase());

        if (this.bitcoinTranslationRepo.existsById(id)) {
            this.bitcoinTranslationRepo.deleteById(id);
            return true;
        } else
            return false;
    }

    @Override
    public Bitcoin updateStatus(BitcoinStatus status) throws BitcoinException {
        Optional<Bitcoin> optional = this.bitcoinRepo.findById(status.getCode());
        if (optional.isPresent()) {
            Bitcoin bitcoin = optional.get();
            bitcoin.setStatus(status.getStatus());
            bitcoin.setUpdated(LocalDateTime.now());
            return this.bitcoinRepo.save(bitcoin);
        } else {
            throw new BitcoinException("code: " + status.getCode() + " not exists");
        }
    }

}
