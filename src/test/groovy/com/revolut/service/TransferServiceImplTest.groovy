package com.revolut.service

import com.revolut.dao.TransferDao
import com.revolut.db.HibernateUtil
import com.revolut.dto.AccountTransferDto
import com.revolut.dto.TransferDto
import com.revolut.entity.AccountEntity
import org.hibernate.Session
import org.hibernate.Transaction
import spock.lang.Specification

/**
 * Created by kubus on 17/07/2018
 */
class TransferServiceImplTest extends Specification {

    def transferDao = Mock(TransferDao)
    def hibernateUtil = Mock(HibernateUtil)
    def transactionSystem = Mock(TransactionSystem)

    def underTest = new TransferServiceImpl(transferDao, hibernateUtil, transactionSystem)

    def "Positive scenario for getting accounts and making a transaction"() {
        given:
        def sessionMock = Mock(Session)
        def transactionMock = Mock(Transaction)
        def transferDto = new TransferDto("iban1", "iban2",  BigDecimal.ONE, "USD")
        transferDao.findAccounts("iban1", "iban2") >> new AccountTransferDto(new AccountEntity("iban1", "USD", BigDecimal.ONE), new AccountEntity("iban2", "USD", BigDecimal.ONE))

        when:
        underTest.transfer(transferDto)

        then:
        1 * hibernateUtil.getSession() >> sessionMock
        1 * sessionMock.beginTransaction() >> transactionMock
        1 * transactionMock.commit()
        1 * sessionMock.close()
        1 * transactionSystem.makeTransaction(*_) >> { arguments ->
            final AccountTransferDto accountTransferDto = arguments[0]
            accountTransferDto.getFrom() == "iban1"
            accountTransferDto.getTo() == "iban2"
            arguments[1] == BigDecimal.ONE
            arguments[2] == "USD"
        }
    }

    def "Negative scenario for getting accounts"(){
        given:
        def sessionMock = Mock(Session)
        def transactionMock = Mock(Transaction)
        def transferDto = new TransferDto("iban1", "iban2",  BigDecimal.ONE, "USD")
        transferDao.findAccounts("iban1", "iban2") >> {throw new IllegalArgumentException("oops")}

        when:
        underTest.transfer(transferDto)

        then:
        1 * hibernateUtil.getSession() >> sessionMock
        1 * sessionMock.beginTransaction() >> transactionMock
        1 * transactionMock.rollback()
        1 * sessionMock.close()
        0 * transactionSystem.makeTransaction()
        def e = thrown(IllegalArgumentException)
        e.message == 'oops'

    }
}
