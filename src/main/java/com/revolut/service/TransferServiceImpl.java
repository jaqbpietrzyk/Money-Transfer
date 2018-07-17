package com.revolut.service;

import com.google.inject.Inject;
import com.revolut.dao.TransferDao;
import com.revolut.db.HibernateUtil;
import com.revolut.dto.AccountTransferDto;
import com.revolut.dto.TransferDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * Created by kubus on 17/07/2018
 */
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final TransferDao transferDao;
    private final HibernateUtil hibernateUtil;
    private final TransactionSystem transactionSystem;

    @Inject
    public TransferServiceImpl(TransferDao transferDao, HibernateUtil hibernateUtil, TransactionSystem transactionSystem) {
        this.transferDao = transferDao;
        this.hibernateUtil = hibernateUtil;
        this.transactionSystem = transactionSystem;
    }


    @Override
    public void transfer(TransferDto transferDto) {
        Session currentSession = hibernateUtil.getSession();
        Transaction transaction = currentSession.beginTransaction();
        try {
            AccountTransferDto accounts = transferDao.findAccounts(transferDto.getFrom(), transferDto.getTo());
            log.info("Got {} entity accounts", accounts);
            transactionSystem.makeTransaction(accounts, transferDto.getAmount(), transferDto.getCurrency());
            transaction.commit();
        } catch (Exception exc) {
            transaction.rollback();
            throw exc;
        } finally {
            currentSession.close();
        }
    }
}
