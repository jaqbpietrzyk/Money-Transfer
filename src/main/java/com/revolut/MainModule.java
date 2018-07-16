package com.revolut;

import com.google.inject.AbstractModule;
import com.revolut.controller.ControllerModule;
import com.revolut.dao.TransferDaoModule;
import com.revolut.service.ServiceModule;

/**
 * Created by kubus on 16/07/2018
 */
public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ServiceModule());
        install(new ControllerModule());
        install(new TransferDaoModule());
    }
}
