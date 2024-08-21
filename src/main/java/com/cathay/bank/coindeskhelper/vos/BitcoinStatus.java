package com.cathay.bank.coindeskhelper.vos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BitcoinStatus {
    @Schema(description = "Bitcoin code", example = "USD")
    @NotEmpty
    private String code;

    @Schema(description = "status code, 0: active, 1: inactive", example = "1")
    @NotEmpty
    private int status;

    public String getCode() {
        return this.code.toUpperCase();
    }
}
