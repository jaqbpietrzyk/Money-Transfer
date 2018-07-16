package com.revolut.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;

import static spark.Spark.post;

/**
 * Created by kubus on 16/07/2018.
 */
public class TransferController {

    private final Gson gson;

    @Inject
    public TransferController(Gson gson) {
        this.gson = gson;
        initPath();
    }

    public void initPath() {
        post("/transfer", (req, res) -> {
                    return null;
                }
        );
    }
}
