package com.revolut.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.revolut.dto.ErrorDto;

import static spark.Spark.exception;

/**
 * Created by kubus on 16/07/2018
 */
public class ErrorController {

    private final Gson gson;

    @Inject
    public ErrorController(Gson gson) {
        this.gson = gson;
        initPath();
    }

    public void initPath(){
        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(gson.toJson(new ErrorDto(e.getMessage())));
        });

        exception(JsonSyntaxException.class, (e, req, res) -> {
            res.status(400);
            res.body(gson.toJson(new ErrorDto(e.getMessage())));
        });
    }
}
