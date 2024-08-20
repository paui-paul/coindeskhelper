package com.cathay.bank.coindeskhelper.services;

import org.springframework.stereotype.Service;
import com.cathay.bank.coindeskhelper.vos.CurrentPrice;

@Service
public interface ICoindeskApiService {
    CurrentPrice getCurrentPrice();
}
