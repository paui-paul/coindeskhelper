package com.cathay.bank.coindeskhelper.vos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RestResult<T> {
    private boolean success;
    private String message;
    private T result;

    public RestResult() {
        this.success = true;
        this.message = "";
    }
}
