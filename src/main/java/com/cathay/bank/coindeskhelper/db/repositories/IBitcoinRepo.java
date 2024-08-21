package com.cathay.bank.coindeskhelper.db.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cathay.bank.coindeskhelper.db.entities.Bitcoin;
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;

public interface IBitcoinRepo extends JpaRepository<Bitcoin, String> {
    static final String FIND_BITCOIN_INFO_BY_LANGUAGE_SQL = """
            SELECT
                A.CODE
              , A.RATE_FLOAT
              , B.NAME
              , B.DESCRIPTION
              , A.UPDATED
            FROM
                BITCOIN A
            LEFT JOIN
                BITCOIN_TRANSLATION B
                ON
                    A.CODE         = B.CODE
                    AND B.LANGUAGE = :language
            WHERE
                1            = 1
                AND A.STATUS = 0
            """;

    @Query(value = FIND_BITCOIN_INFO_BY_LANGUAGE_SQL, nativeQuery = true)
    List<BitCoinInfoByLanguage> findBitcoinInfoByLanguage(@Param("language") String language);
}
