package com.cathay.bank.coindeskhelper.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;
import com.cathay.bank.coindeskhelper.vos.RestResult;

@RestController
@RequestMapping("/api/coin-desk-api")
public interface ICoindeskApiController {
    @GetMapping("current-price")
    public ResponseEntity<RestResult<CurrentPrice>> getCurrentPrice();
}
