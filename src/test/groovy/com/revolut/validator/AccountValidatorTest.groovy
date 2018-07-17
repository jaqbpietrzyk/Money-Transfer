package com.revolut.validator

import com.revolut.dto.AccountTransferDto
import com.revolut.entity.AccountEntity
import spock.lang.Specification

/**
 * Created by kubus on 17/07/2018
 */
class AccountValidatorTest extends Specification {
    def "check currency positive test case"() {
        given:
        def currency = "USD"
        def acc1 = new AccountEntity("iban1", currency, BigDecimal.ONE)
        def acc2 = new AccountEntity("iban1", currency, BigDecimal.ONE)
        def accountTransferDto = new AccountTransferDto(acc1, acc2)

        when:
        AccountValidator.checkCurrency(accountTransferDto, currency)

        then:
        noExceptionThrown()
    }

    def "check currency negative test case"() {
        given:
        def currency = "USD"
        def acc1 = new AccountEntity("iban1", "PLN", BigDecimal.ONE)
        def acc2 = new AccountEntity("iban1", currency, BigDecimal.ONE)
        def accountTransferDto = new AccountTransferDto(acc1, acc2)

        when:
        AccountValidator.checkCurrency(accountTransferDto, currency)

        then:
        thrown(IllegalStateException)
    }

    def "check balance postive test"() {
        given:
        def underBalance = 80.00
        def acc1 = new AccountEntity("iban1", "USD", 100.00)
        def acc2 = new AccountEntity("iban1", "USD", BigDecimal.ONE)
        def accountTransferDto = new AccountTransferDto(acc1, acc2)

        when:
        AccountValidator.checkFromBalance(accountTransferDto, underBalance, "USD")

        then:
        noExceptionThrown()
    }

    def "check balance negative test"() {
        given:
        def underBalance = 120.00
        def acc1 = new AccountEntity("iban1", "USD", 100.00)
        def acc2 = new AccountEntity("iban1", "USD", BigDecimal.ONE)
        def accountTransferDto = new AccountTransferDto(acc1, acc2)

        when:
        AccountValidator.checkFromBalance(accountTransferDto, underBalance, "USD")

        then:
        thrown(IllegalStateException)
    }
}
