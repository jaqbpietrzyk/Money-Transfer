package com.revolut.service;

import com.google.common.base.Preconditions;
import com.revolut.dto.AccountTransferDto;
import com.revolut.entity.AccountEntity;
import com.revolut.validator.AccountValidator;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Created by kubus on 17/07/2018
 */

@Slf4j
public class TransactionSystem {
    public void makeTransaction(AccountTransferDto accountTransferDto, BigDecimal amount, String currency) {
        AccountValidator.validate(accountTransferDto, amount, currency);
        AccountEntity from = accountTransferDto.getFrom();
        AccountEntity to = accountTransferDto.getTo();
        log.info("Making transaction from {} to {} for amount {}", from, to, amount);
        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));
        log.info("Transaction done. Sender state: {} Receiver state: {}", from, to);
    }

}
