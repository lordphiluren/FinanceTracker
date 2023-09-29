package com.lordphiluren.financetracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "finance_operation")
@Data
@NoArgsConstructor
public class FinanceOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "income_operation")
    @NotNull
    private boolean incomeOperation;
    @Column(name = "amount")
    @PositiveOrZero
    private BigDecimal amount;
    @Column(name = "done_at")
    @PastOrPresent
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date doneAt;
    @Column(name = "description")
    @Size(max = 50)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public FinanceOperation(boolean incomeOperation,
                            BigDecimal amount,
                            Date doneAt,
                            String description,
                            Category category,
                            Account account) {
        this.incomeOperation = incomeOperation;
        this.amount = amount;
        this.doneAt = doneAt;
        this.description = description;
        this.category = category;
        this.account = account;
    }
}
