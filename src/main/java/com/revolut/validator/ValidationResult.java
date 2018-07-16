package com.revolut.validator;

import java.util.Optional;

/**
 * Created by kubus on 16/07/2018.
 */
public interface ValidationResult {
    boolean isValid();
    Optional<String> getReason();
}
