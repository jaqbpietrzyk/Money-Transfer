package com.revolut.validator;

import com.google.common.collect.ImmutableSet;
import com.revolut.dto.TransferDto;
import org.apache.commons.validator.routines.CurrencyValidator;
import org.apache.commons.validator.routines.IBANValidator;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by kubus on 16/07/2018.
 */
public interface TransferValidator extends Function<TransferDto, ValidationResult> {

    Set<String> supportedCurrencies = ImmutableSet.of("USD", "EUR", "PLN");

    static TransferValidator nonNegativeAmount() {
        return testWithMessage(transferDto -> transferDto.getAmount().compareTo(BigDecimal.ZERO) > 0, "Amount has to be greater than zero");
    }

    static TransferValidator isValidCurrency() {
        return testWithMessage(transferDto -> supportedCurrencies.contains(transferDto.getCurrency().toUpperCase()) , "Non valid currency");
    }

    static TransferValidator isValidFromIBANNumber() {
        return testWithMessage(transferDto -> IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(transferDto.getFrom()), String.format("Invalid From account"));
    }

    static TransferValidator isValidToIBANNumber() {
        return testWithMessage(transferDto -> IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(transferDto.getTo()), "Invalid To account");
    }

    static TransferValidator isDifferentAccount() {
        return testWithMessage(transferDto -> !transferDto.getFrom().equalsIgnoreCase(transferDto.getTo()), "Accounts have to be different");
    }

    static TransferValidator testWithMessage(Predicate<TransferDto> predicate, String errorMessage) {
        return transferDto -> predicate.test(transferDto) ? new ValidResult() : new InvalidResult(errorMessage);
    }

    default TransferValidator and(TransferValidator other) {
        return transferDto -> {
            final ValidationResult result = this.apply(transferDto);
            return result.isValid() ? other.apply(transferDto) : result;
        };
    }
}
