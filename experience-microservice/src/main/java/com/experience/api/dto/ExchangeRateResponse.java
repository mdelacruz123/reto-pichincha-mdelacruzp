package com.experience.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeRateResponse {

    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rate;
}
