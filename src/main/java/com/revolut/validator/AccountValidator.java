package com.revolut.validator;

import com.google.common.base.Preconditions;
import com.revolut.dto.AccountTransferDto;

import java.math.BigDecimal;

/**
 * Created by kubus on 17/07/2018
 */
public class AccountValidator {
    public static void validate(AccountTransferDto accountTransferDto, BigDecimal amount, String currency) {
        checkFromBalance(accountTransferDto, amount, currency);
        checkCurrency(accountTransferDto, currency);
    }

    static void checkCurrency(AccountTransferDto accountTransferDto, String currency) {
        Preconditions.checkState(accountTransferDto.getFrom().getCurrency().equalsIgnoreCase(currency), "Currency conversion not allowed");
        Preconditions.checkState(accountTransferDto.getTo().getCurrency().equalsIgnoreCase(currency), "Currency conversion not allowed");
    }

    static void checkFromBalance(AccountTransferDto accountTransferDto, BigDecimal amount, String currency) {
        BigDecimal fromAmount = accountTransferDto.getFrom().getAmount();
        Preconditions.checkState(fromAmount.compareTo(amount) >= 0.00,
                "Insufficient funds. You need %s %s more", amount.subtract(fromAmount), currency);
    }


}
