package com.cathay.bank.coindeskhelper.controllers.impl;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.cathay.bank.coindeskhelper.controllers.IBitcoinController;
import com.cathay.bank.coindeskhelper.db.entities.BitcoinTranslation;
import com.cathay.bank.coindeskhelper.db.projections.BitCoinInfoByLanguage;
import com.cathay.bank.coindeskhelper.services.IBitcoinService;
import com.cathay.bank.coindeskhelper.utils.annotations.LogExecution;
import com.cathay.bank.coindeskhelper.utils.exceptions.BitcoinException;
import com.cathay.bank.coindeskhelper.vos.BitcoinTranslationSetting;
import com.cathay.bank.coindeskhelper.vos.RestResult;

@Component
public class BitcoinController implements IBitcoinController {
    private IBitcoinService service;

    public BitcoinController(IBitcoinService service) {
        this.service = service;
    }

    @LogExecution
    @Override
    public ResponseEntity<RestResult<List<BitCoinInfoByLanguage>>> findBitcoinByLanguage(
            String language) {
        RestResult<List<BitCoinInfoByLanguage>> result = new RestResult<>();
        result.setResult(this.service.findBitCoinInfoByLanguage(language));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @LogExecution
    @Override
    public ResponseEntity<RestResult<BitcoinTranslation>> addOrUpdateTranslation(
            BitcoinTranslationSetting setting) throws BitcoinException {
        RestResult<BitcoinTranslation> result = new RestResult<>();
        result.setResult(this.service.addOrUpdateTranslation(setting));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @LogExecution
    @Override
    public ResponseEntity<RestResult<Boolean>> deleteTranslation(String code, String language)
            throws BitcoinException {
        RestResult<Boolean> result = new RestResult<>();
        result.setResult(this.service.deleteTranslation(code, language));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @LogExecution
    @Override
    public ResponseEntity<RestResult<Boolean>> delete(String code) throws BitcoinException {
        RestResult<Boolean> result = new RestResult<>();
        result.setResult(this.service.deleteBitcoin(code));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
