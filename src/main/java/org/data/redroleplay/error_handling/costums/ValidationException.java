package org.data.redroleplay.error_handling.costums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private final String error;
}
