package com.revolut.dto;

import com.revolut.entity.AccountEntity;
import lombok.Data;

/**
 * Created by kubus on 17/07/2018
 */
@Data
public class AccountTransferDto {
    private final AccountEntity from;
    private final AccountEntity to;
}
