package org.data.redroleplay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WhitelistRequestStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    DENIED("denied");

    private final String value;
}
