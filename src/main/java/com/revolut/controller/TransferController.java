package com.revolut.controller;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.revolut.dto.ErrorDto;
import com.revolut.dto.TransferDto;
import com.revolut.dto.UUIDReferenceResponse;
import com.revolut.service.TransferService;
import com.revolut.validator.TransferValidator;
import com.revolut.validator.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import static com.revolut.validator.TransferValidator.isDifferentAccount;
import static com.revolut.validator.TransferValidator.isValidCurrency;
import static com.revolut.validator.TransferValidator.isValidFromIBANNumber;
import static com.revolut.validator.TransferValidator.isValidScale;
import static com.revolut.validator.TransferValidator.isValidToIBANNumber;
import static com.revolut.validator.TransferValidator.nonNegativeAmount;
import static spark.Spark.halt;
import static spark.Spark.post;

/**
 * Created by kubus on 16/07/2018.
 */
@Slf4j
public class TransferController {

    private final Gson gson;
    private final TransferService transferService;
    private final TransferValidator transferValidator = nonNegativeAmount().and(isDifferentAccount()).
        and(isValidCurrency()).and(isValidFromIBANNumber()).and(isValidToIBANNumber()).and(isValidScale());

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
                    req.attribute("uuid", uuid);

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
        ValidationResult validationResult = transferValidator.apply(transferDto);
        if (!validationResult.isValid()) {
            log.error("Invalid request, reason: {}", validationResult.getReason());
            halt(400, gson.toJson(new ErrorDto(validationResult.getReason().orElse("Unknown error"), MDC.get("uuid"))));
        }
        log.info("Valid, proceeding with transaction");
    }
}
