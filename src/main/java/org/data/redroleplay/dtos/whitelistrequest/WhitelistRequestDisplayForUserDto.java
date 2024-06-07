package org.data.redroleplay.dtos.whitelistrequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.data.redroleplay.enums.CharacterGender;
import org.data.redroleplay.enums.CharacterOrientation;
import org.data.redroleplay.enums.WhitelistRequestStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhitelistRequestDisplayForUserDto {

    private Long id;

    private String characterFullName;

    private CharacterGender characterGender;

    private String characterOrigin;

    private LocalDate characterBirthDate;

    private CharacterOrientation characterOrientation;

    private String characterStory;

    private LocalDateTime requestDate;

    private WhitelistRequestStatus status;

    private LocalDateTime verifiedDate;

    private String reason;

    private String note;
}
