package com.cathay.bank.coindeskhelper.services;

import org.springframework.stereotype.Service;
import com.cathay.bank.coindeskhelper.vos.CurrentPriceConvert;

@Service
public interface ICoindeskApiService {
    CurrentPriceConvert getCurrentPrice();
}
