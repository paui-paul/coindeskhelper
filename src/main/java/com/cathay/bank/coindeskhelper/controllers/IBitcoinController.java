package com.cathay.bank.coindeskhelper.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;
import com.cathay.bank.coindeskhelper.vos.RestResult;


@RestController
@RequestMapping("/api/bitcoin")
public interface IBitcoinController {

        @GetMapping("/{language}")
        ResponseEntity<RestResult<List<BitCoinInfoByLanguage>>> findBitcoinByLanguage(
                        @PathVariable String language);

        @PostMapping("/translation/add-or-update")
        ResponseEntity<RestResult<BitcoinTranslation>> addOrUpdateTranslation(
                        @RequestBody BitcoinTranslationSetting setting) throws BitcoinException;

        @DeleteMapping("/translation/{code}/{language}")
        ResponseEntity<RestResult<Boolean>> deleteTranslation(@PathVariable String code,
                        @PathVariable String language) throws BitcoinException;
}
