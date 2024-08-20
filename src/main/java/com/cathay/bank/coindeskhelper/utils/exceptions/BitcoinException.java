package com.cathay.bank.coindeskhelper.utils.exceptions;

public class BitcoinException extends Exception {
    public BitcoinException() {
        super();
    }

    public BitcoinException(String message) {
        super(message);
    }

    public BitcoinException(String message, Throwable cause) {
        super(message, cause);
    }

    public BitcoinException(Throwable cause) {
        super(cause);
    }
}
