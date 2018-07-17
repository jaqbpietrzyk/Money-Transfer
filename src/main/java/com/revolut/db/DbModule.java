package com.revolut.db;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by kubus on 17/07/2018
 */
public class DbModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HibernateUtil.class).in(Singleton.class);
    }
}
