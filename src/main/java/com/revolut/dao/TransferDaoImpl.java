package com.revolut.dao;

import com.revolut.db.HibernateUtil;
import com.revolut.dto.TransferDto;
import com.revolut.entity.AccountEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by kubus on 16/07/2018
 */
public class TransferDaoImpl implements TransferDao {
    @Override
    public List<AccountEntity> findAccounts(TransferDto transferDto) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM AccountEntity ae WHERE ae.iban = :iban AND ae.iban = :iban");
        query.setParameter(1, transferDto.getFrom());
        query.setParameter(2, transferDto.getTo());
        query.setLockMode(LockModeType.PESSIMISTIC_READ);
        List<AccountEntity> list = query.list();
        return list;
    }
}
