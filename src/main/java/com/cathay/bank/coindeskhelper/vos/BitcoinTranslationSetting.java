package com.cathay.bank.coindeskhelper.vos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class BitcoinTranslationSetting {
    @Schema(description = "Bitcoin code", example = "USD")
    @NotEmpty
    @NotNull
    private String code;

    @Schema(description = "language for name & description", example = "EN")
    @NotEmpty
    private String language;

    @Schema(description = "name, default: {code}", example = "USD")
    @NotEmpty
    private String name;

    @Schema(description = "description", example = "description")
    private String description;

    public String getCode() {
        return this.code == null ? this.code : this.code.toUpperCase();
    }

    public String getLanguage() {
        return this.language == null ? this.language : this.language.toUpperCase();
    }
}
