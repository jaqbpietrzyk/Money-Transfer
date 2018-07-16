package com.revolut.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by kubus on 16/07/2018
 */
@Data
@RequiredArgsConstructor
public class ErrorDto {
    private final String message;

}
