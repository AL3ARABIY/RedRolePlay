package org.data.redroleplay.errorHandling.costums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecordNotFoundException extends RuntimeException {
    private final String error;
}
