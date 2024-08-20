package com.cathay.bank.coindeskhelper.db.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Bitcoin {
    @Id
    @Column
    private String code;

    @Column
    private String symbol;

    @Column
    private String rate;

    @Column
    private String description;

    @Column
    private float rateFloat;

    @Column
    private int status;

    @Column
    private LocalDateTime updated;

    @Column
    private String createdBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdTime;

    @Column
    private String updatedBy;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedTime;
}
