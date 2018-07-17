package com.revolut.dao

import com.revolut.db.HibernateUtil
import com.revolut.dto.AccountTransferDto
import com.revolut.entity.AccountEntity
import org.hibernate.Session
import org.hibernate.query.Query
import spock.lang.Specification

import javax.persistence.LockModeType

/**
 * Created by kubus on 17/07/2018
 */
class TransferDaoImplTest extends Specification {

    def hibernateUtil = Mock(HibernateUtil)
    def underTest = new TransferDaoImpl(hibernateUtil)

    def "find accounts and create dto"() {
        given:
        def query = Mock(Query)
        def sessionMock = Mock(Session)

        when:
        def accounts = underTest.findAccounts("iban1", "iban2")

        then:
        1 * hibernateUtil.getSession() >> sessionMock
        1 * sessionMock.createQuery(_) >> query
        1 * query.setLockMode(LockModeType.PESSIMISTIC_WRITE)
        1 * query.list() >> [new AccountEntity("iban1", "USD", BigDecimal.ONE), new AccountEntity("iban2", "USD", BigDecimal.ONE)]
        accounts.from.iban == "iban1"
        accounts.to.iban == "iban2"
    }

    def "Find not enough accounts"() {
        given:
        def query = Mock(Query)
        def sessionMock = Mock(Session)

        when:
        def accounts = underTest.findAccounts("iban1", "iban2")

        then:
        1 * hibernateUtil.getSession() >> sessionMock
        1 * sessionMock.createQuery(_) >> query
        1 * query.setLockMode(LockModeType.PESSIMISTIC_WRITE)
        1 * query.list() >> [new AccountEntity("iban1", "USD", BigDecimal.ONE)]
        thrown(IllegalArgumentException)
    }
}
