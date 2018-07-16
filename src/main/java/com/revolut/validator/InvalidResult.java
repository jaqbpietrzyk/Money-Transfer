package com.revolut.validator;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

/**
 * Created by kubus on 16/07/2018.
 */
@Data
public class InvalidResult implements ValidationResult {

    private final String reason;

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.of(reason);
    }
}
