package com.cathay.bank.coindeskhelper.vos;

import java.time.LocalDateTime;
import com.cathay.bank.coindeskhelper.utils.JsonDeserialize.CoindeskApiTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @JsonDeserialize(using = CoindeskApiTimeDeserializer.class)
    private LocalDateTime updated;

    @JsonProperty("updatedISO")
    @JsonDeserialize(using = CoindeskApiTimeDeserializer.class)
    private LocalDateTime updatedIso;

    @JsonProperty("updateduk")
    @JsonDeserialize(using = CoindeskApiTimeDeserializer.class)
    private LocalDateTime updatedUk;
}
