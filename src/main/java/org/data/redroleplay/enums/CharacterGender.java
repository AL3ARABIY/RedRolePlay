package org.data.redroleplay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharacterGender {
    MALE("male"),
    FEMALE("female");

    private final String value;
}
