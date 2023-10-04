package com.lordphiluren.financetracker.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lordphiluren.financetracker.utils.enums.Currency;
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
    private String name;
    private BigDecimal balance;
    private Currency currency;
    @JsonProperty("user")
    private UserDTO user;
}
