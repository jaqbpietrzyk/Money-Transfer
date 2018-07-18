package com.revolut.dto;

import com.revolut.json.JsonRequired;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by kubus on 01/07/2018.
 */
@Data
public class TransferDto {
    @JsonRequired
    private final String from;
    @JsonRequired
    private final String to;
    @JsonRequired
    private final BigDecimal amount;
    @JsonRequired
    private final String currency;


}

