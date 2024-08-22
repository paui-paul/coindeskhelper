package com.cathay.bank.coindeskhelper.db.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "BITCOIN")
@Data
public class Bitcoin implements Serializable {
    @Id
    @Column
    private String code;

    @Column
    private String symbol;

    @Column
    private String rate;

    @Column
    private float rateFloat;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updated;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedIso;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedUk;

    @Column
    private String createdBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdTime;

    @Column
    private String updatedBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedTime;
}
