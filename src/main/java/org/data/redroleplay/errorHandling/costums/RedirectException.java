package org.data.redroleplay.errorHandling.costums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedirectException extends RuntimeException {
    private final String redirectUrl;
}
