package com.cathay.bank.coindeskhelper.vos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Time {
    @JsonProperty("updated")
    private String updated;

    @JsonProperty("updatedISO")
    private String updatedIso;

    @JsonProperty("updateduk")
    private String updatedUk;

    @JsonIgnore
    public LocalDateTime getUpdatedLocalDateTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
        return LocalDateTime.parse(this.updated, formatter);
    }

    @JsonIgnore
    public LocalDateTime getUpdatedIsoLocalDateTime() {
        return LocalDateTime.parse(this.updatedIso, DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonIgnore
    public LocalDateTime getUpdatedUkLocalDateTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm z", Locale.ENGLISH);
        return LocalDateTime.parse(this.updatedUk, formatter);
    }
}
