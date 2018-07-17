package com.revolut.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by kubus on 16/07/2018
 */
public class TransferDaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransferDao.class).to(TransferDaoImpl.class).in(Singleton.class);
    }
}
