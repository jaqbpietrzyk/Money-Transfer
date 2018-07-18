package com.revolut.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.revolut.dto.TransferDto;

public class JsonModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    Gson provideCustomizedGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TransferDto.class, new AnnotatedDeserializer<TransferDto>());
        return gsonBuilder.create();
    }
}
