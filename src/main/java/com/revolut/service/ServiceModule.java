package com.revolut.service;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by kubus on 16/07/2018
 */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransferService.class).to(TransferServiceImpl.class).in(Singleton.class);
        bind(TransactionSystem.class).in(Singleton.class);

    }
}
