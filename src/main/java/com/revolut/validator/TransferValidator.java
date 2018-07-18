package com.revolut.validator;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.revolut.dto.TransferDto;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by kubus on 16/07/2018.
 */
public interface TransferValidator extends Function<TransferDto, ValidationResult> {

    Set<String> supportedCurrencies = ImmutableSet.of("USD", "EUR", "PLN");

    static TransferValidator isValidScale() {
        return testWithMessage(transferDto -> transferDto.getAmount().scale() < 3, "Please provide amount with at most 2 digits after decimal point");
    }

    static TransferValidator nonNegativeAmount() {
        return testWithMessage(transferDto -> transferDto.getAmount() != null && transferDto.getAmount().compareTo(BigDecimal.ZERO) > 0,
                "Non valid amount. Please provide amount field greater than zero");
    }

    static TransferValidator isValidCurrency() {
        return testWithMessage(
                transferDto -> !Strings.isNullOrEmpty(transferDto.getCurrency()) && supportedCurrencies.contains(transferDto.getCurrency().toUpperCase()),
                "Non valid currency");
    }

    static TransferValidator isValidFromIBANNumber() {
        return testWithMessage(transferDto -> IBANCheckDigit.IBAN_CHECK_DIGIT.isValid(transferDto.getFrom()), "Invalid From account");
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
