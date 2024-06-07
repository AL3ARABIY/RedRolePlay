package org.data.redroleplay.error_handling.costums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedirectException extends RuntimeException {
    private final String redirectUrl;
}
