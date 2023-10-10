package com.lordphiluren.financetracker.web.dto;

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
public class NewFinanceOperationDTO {
    private long id;
    private BigDecimal amount;
    @JsonProperty("done_at")
    private Date doneAt;
    @JsonProperty("category_id")
    private long categoryId;
    @JsonProperty("account_id")
    private long accountId;
    private String description;
}
