package com.revolut.dao;

import com.revolut.dto.AccountTransferDto;
import com.revolut.dto.TransferDto;
import com.revolut.entity.AccountEntity;

import java.util.List;

/**
 * Created by kubus on 16/07/2018
 */
public interface TransferDao {
    AccountTransferDto findAccounts(String IBANFrom, String IBANTo);
}
