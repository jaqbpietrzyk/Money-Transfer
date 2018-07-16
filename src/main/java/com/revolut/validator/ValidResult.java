package com.revolut.validator;

import lombok.Data;

import java.util.Optional;

/**
 * Created by kubus on 16/07/2018.
 */
@Data
public class ValidResult implements ValidationResult {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }
}
