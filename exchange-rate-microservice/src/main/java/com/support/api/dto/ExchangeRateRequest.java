package com.support.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateRequest {
    private String fromCurrency;
    private String toCurrency;
    private Double rate;
}