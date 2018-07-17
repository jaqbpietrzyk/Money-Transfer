package com.revolut.dao;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.revolut.db.HibernateUtil;
import com.revolut.dto.AccountTransferDto;
import com.revolut.entity.AccountEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by kubus on 16/07/2018
 */
public class TransferDaoImpl implements TransferDao {

    private final HibernateUtil hibernateUtil;

    @Inject
    public TransferDaoImpl(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    @Override
    public AccountTransferDto findAccounts(String IBANFrom, String IBANTo) {
        Session session = hibernateUtil.getSession();
        Query query = session.createQuery("FROM AccountEntity ae WHERE ae.iban = :ibanfrom OR ae.iban = :ibanto");
        query.setParameter("ibanfrom", IBANFrom);
        query.setParameter("ibanto", IBANTo);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        List<AccountEntity> list = query.list();
        Preconditions.checkArgument(list.size() == 2, "Cannot find account");
        return new AccountTransferDto(list.get(0), list.get(1));
    }
}
