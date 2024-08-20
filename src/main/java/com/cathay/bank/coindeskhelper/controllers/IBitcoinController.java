package com.cathay.bank.coindeskhelper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;
import com.cathay.bank.coindeskhelper.vos.RestResult;


@RestController
@RequestMapping("/api/bitcoin")
public interface IBitcoinController {
    @PostMapping("/translation/add-or-update")
    ResponseEntity<RestResult<BitcoinTranslation>> addOrUpdateTranslation(
            @RequestBody BitcoinTranslationSetting setting) throws BitcoinException;

}
