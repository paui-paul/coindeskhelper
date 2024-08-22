package com.cathay.bank.coindeskhelper.db.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "BITCOIN_TRANSLATION")
@IdClass(BitcoinTranslationId.class)
@Data
public class BitcoinTranslation implements Serializable {

    @Id
    @Column
    private String code;

    @Id
    @Column
    private String language;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String createdBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdTime;

    @Column
    private String updatedBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedTime;

}
