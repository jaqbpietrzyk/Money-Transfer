package com.revolut.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.revolut.dto.TransferDto;
import com.revolut.dto.UUIDReferenceResponse;
import com.revolut.service.TransferService;
import com.revolut.validator.TransferValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

import static com.revolut.validator.TransferValidator.*;
import static spark.Spark.post;

/**
 * Created by kubus on 16/07/2018.
 */
@Slf4j
public class TransferController {

    private final Gson gson;
    private final TransferService transferService;
    private final TransferValidator transferValidator = nonNegativeAmount().and(isDifferentAccount()).
            and(isValidCurrency()).and(isValidFromIBANNumber()).and(isValidToIBANNumber());

    @Inject
    public TransferController(Gson gson, TransferService transferService) {
        this.gson = gson;
        this.transferService = transferService;
        initPath();
    }

    public void initPath() {
        post("/transfer", (req, res) -> {
                    try {
                        String uuid = UUID.randomUUID().toString();
                        MDC.put("uuid", uuid);

                        TransferDto transferDto = gson.fromJson(req.body(), TransferDto.class);
                        validateTransferDto(transferDto);
                        transferService.transfer(transferDto);

                        return new UUIDReferenceResponse(uuid);
                    } finally {
                        MDC.remove("uuid");
                    }
                }, uuid -> gson.toJson(uuid)
        );
    }

    private void validateTransferDto(TransferDto transferDto) {
        log.info("Validating transfer dto: {}", transferDto);
        transferValidator.apply(transferDto);
        log.info("Valid, proceeding with transaction");
    }
}
