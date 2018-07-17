package com.revolut.service

import com.revolut.dto.AccountTransferDto
import com.revolut.entity.AccountEntity
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by kubus on 17/07/2018
 */

@Unroll
class TransactionSystemTest extends Specification {

    @Shared
    def acc1 = new AccountEntity("iban1", "USD", 120.00)
    @Shared
    def acc2 = new AccountEntity("iban2", "USD", 100.50)
    def accountTransferDto = new AccountTransferDto(acc1, acc2)
    def underTest = new TransactionSystem()

    def "make a transaction"() {
        given:
        def sum = 220.50

        when:
        underTest.makeTransaction(accountTransferDto, amount, "USD")

        then:
        acc1.amount == expectedAcc1
        acc2.amount == expectedAcc2
        acc1.amount + acc2.amount == sum

        where:
        amount | expectedAcc1 | expectedAcc2
        10.00  | 110.00       | 110.50
        12.00  | 98.00        | 122.50
        0.54   | 97.46        | 123.04
        1.00   | 96.46        | 124.04

    }
}
