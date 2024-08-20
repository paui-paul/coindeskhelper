package com.cathay.bank.coindeskhelper.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.cathay.bank.coindeskhelper.controllers.ICoindeskApiController;
import com.cathay.bank.coindeskhelper.services.ICoindeskApiService;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;
import com.cathay.bank.coindeskhelper.vos.RestResult;

@Component
public class CoindeskApiController implements ICoindeskApiController {
    private ICoindeskApiService service;

    public CoindeskApiController(@Autowired ICoindeskApiService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<RestResult<CurrentPrice>> getCurrentPrice() {
        RestResult<CurrentPrice> result = new RestResult<>();
        result.setResult(this.service.getCurrentPrice());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
