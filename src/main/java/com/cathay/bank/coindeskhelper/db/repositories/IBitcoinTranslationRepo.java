package com.cathay.bank.coindeskhelper.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslationId;

public interface IBitcoinTranslationRepo
        extends JpaRepository<BitcoinTranslation, BitcoinTranslationId> {

}
