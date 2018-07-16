package com.revolut.controller;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by kubus on 16/07/2018.
 */
public class ControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransferController.class).in(Singleton.class);
        bind(ErrorController.class).in(Singleton.class);
    }
}
