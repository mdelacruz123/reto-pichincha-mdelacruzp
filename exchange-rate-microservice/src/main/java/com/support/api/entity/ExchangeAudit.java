package com.support.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("exchange_audit")
public class ExchangeAudit {
    @Id
    private Long id;
    private String userName;
    private BigDecimal initialAmount;
    private BigDecimal finalAmount;
    private BigDecimal exchangeRate;
    private LocalDateTime processDate;
}