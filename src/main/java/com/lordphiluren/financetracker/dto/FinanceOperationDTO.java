package com.lordphiluren.financetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOperationDTO {
//    @JsonProperty("isIncomeOperation")
//    private boolean incomeOperation;
    private BigDecimal amount;
    private Date doneAt;
    @JsonProperty("category")
    private CategoryDTO categoryDTO;
    @JsonProperty("account")
    private AccountDTO accountDTO;
    private String description;
}
