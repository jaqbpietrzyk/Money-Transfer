package com.revolut.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by kubus on 15/07/2018.
 */

@Entity
@Table(name = "ACCOUNT")
@Data
@NoArgsConstructor
public class AccountEntity {

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "IBAN")
    private String iban;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    public AccountEntity(String iban, String currency, BigDecimal amount) {
        this.iban = iban;
        this.currency = currency;
        this.amount = amount;
    }
}
