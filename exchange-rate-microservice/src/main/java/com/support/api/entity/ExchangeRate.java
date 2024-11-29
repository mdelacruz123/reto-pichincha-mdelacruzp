package com.support.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("exchange_rate")
public class ExchangeRate {

    @Id
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private Double rate;
}