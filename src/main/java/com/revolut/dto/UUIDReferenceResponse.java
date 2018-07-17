package com.revolut.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by kubus on 17/07/2018
 */

@Data
@RequiredArgsConstructor
public class UUIDReferenceResponse {
    private final String uuid;
}
