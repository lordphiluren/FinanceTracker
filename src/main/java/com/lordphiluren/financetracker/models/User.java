package com.lordphiluren.financetracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    @Size(min = 4, max = 24)
    @NotNull
    private String username;
    @Column(name = "password")
    @NotNull
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
