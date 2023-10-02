package com.lordphiluren.financetracker.models;

import com.lordphiluren.financetracker.utils.enums.Currency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "account")
    private List<FinanceOperation> financeOperations;

    public Account(String name, BigDecimal balance, Currency currency, User user,
                   List<FinanceOperation> financeOperations) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.user = user;
        this.financeOperations = financeOperations;
    }
}
