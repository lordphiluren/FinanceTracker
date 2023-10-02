package com.lordphiluren.financetracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @Size(min = 2, max = 100)
    private String name;
    @Column(name = "income_category")
    @NotNull
    private boolean incomeCategory;

    @OneToMany(mappedBy = "category")
    private List<FinanceOperation> financeOperations;

    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "id")
    private User user;
    public Category(String name, boolean incomeCategory, List<FinanceOperation> financeOperations, User user) {
        this.name = name;
        this.incomeCategory = incomeCategory;
        this.financeOperations = financeOperations;
        this.user = user;
    }
}
