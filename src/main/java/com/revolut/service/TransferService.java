package com.revolut.service;

import com.revolut.dao.TransferDao;
import com.revolut.dto.TransferDto;

import java.util.UUID;

/**
 * Created by kubus on 17/07/2018
 */
public interface TransferService {
    void transfer(TransferDto transferDto);
}
