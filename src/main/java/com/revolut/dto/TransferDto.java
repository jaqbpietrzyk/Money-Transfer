package com.revolut.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by kubus on 01/07/2018.
 */
@Data
public class TransferDto {
    private final String from;
    private final String to;
    private final BigDecimal amount;
    private final String currency;
}

