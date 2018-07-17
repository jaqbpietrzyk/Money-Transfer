package com.revolut.dto;

import com.revolut.entity.AccountEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by kubus on 17/07/2018
 */
@RequiredArgsConstructor
@Getter
public class AccountTransferDto {
    private final AccountEntity from;
    private final AccountEntity to;
}
