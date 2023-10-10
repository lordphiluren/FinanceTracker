package com.lordphiluren.financetracker.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lordphiluren.financetracker.models.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private long id;
    private String name;
    private BigDecimal balance;
    private Currency currency;
}
