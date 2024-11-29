package com.experience.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeRequest {
    @NotNull(message = "Source currency is required")
    private String fromCurrency;

    @NotNull(message = "Target currency is required")
    private String toCurrency;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "User ID is required")
    private Long userId;
}
