package com.support.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeAuditRequest {
    private String userName;
    private BigDecimal initialAmount;
    private BigDecimal finalAmount;
    private BigDecimal exchangeRate;
}
