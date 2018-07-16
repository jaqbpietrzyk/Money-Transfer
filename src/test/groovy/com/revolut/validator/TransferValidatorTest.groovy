package com.revolut.validator

import com.revolut.dto.TransferDto
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class TransferValidatorTest extends Specification {
    def "NonNegativeAmount"(amount, expected) {
        given:
        def transferDto = new TransferDto("1", "2", amount, "USD")
        def transferValidator = TransferValidator.nonNegativeAmount();

        when:
        ValidationResult result = transferValidator.apply(transferDto)

        then:
        result.isValid() == expected

        where:
        amount || expected
        1.0    || true
        2      || true
        0.5    || true
        0      || false
        0.0    || false
        -2     || false

    }

    def "IsValidCurrency"(currency, expected) {
        given:
        def transferDto = new TransferDto("1", "2", BigDecimal.ONE, currency)
        def transferValidator = TransferValidator.isValidCurrency()

        when:
        ValidationResult result = transferValidator.apply(transferDto)

        then:
        result.isValid() == expected

        where:
        currency  || expected
        "USD"     || true
        "usd"     || true
        "PLN"     || true
        "EUR"     || true
        "us"      || false
        ""        || false
        "unknown" || false
    }

    def "IsValidFromIBANNumber"(IBAN, expected) {
        given:
        def transferDto = new TransferDto(IBAN, "1", BigDecimal.ONE, "USD")
        def transferValidator = TransferValidator.isValidFromIBANNumber()
        when:
        ValidationResult result = transferValidator.apply(transferDto)

        then:
        result.isValid() == expected

        where:
        IBAN                           || expected
        "BA275680000123456789"         || true
        "PL10105000997603123456789123" || true
        "123"                          || false
        ""                             || false
        "shouldBeInvalid"              || false

    }

    def "IsValidToIBANNumber"(IBAN, expected) {
        given:
        def transferDto = new TransferDto("1", IBAN, BigDecimal.ONE, "USD")
        def transferValidator = TransferValidator.isValidToIBANNumber()

        when:
        ValidationResult result = transferValidator.apply(transferDto)

        then:
        result.isValid() == expected

        where:
        IBAN                           || expected
        "BA275680000123456789"         || true
        "PL10105000997603123456789123" || true
        "123"                          || false
        ""                             || false
        "shouldBeInvalid"              || false
    }

    def "IsDifferentAccount"(acc1, acc2, expected) {
        given:
        def transferDto = new TransferDto(acc1, acc2, BigDecimal.ONE, "USD")
        def transferValidator = TransferValidator.isDifferentAccount()

        when:
        ValidationResult result = transferValidator.apply(transferDto)

        then:
        result.isValid() == expected

        where:
        acc1   | acc2           || expected
        "same" | "same"         || false
        ""     | ""             || false
        "same" | "bitDifferent" || true
        "same" | "sAme"         || false
        "same" | "sOme"         || true

    }
}
