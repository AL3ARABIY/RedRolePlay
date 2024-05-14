package org.data.redroleplay.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.data.redroleplay.enums.CharacterGender;
import org.data.redroleplay.enums.CharacterOrientation;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhitelistRequestDto {

    private String characterFirstName;

    private String characterLastName;

    private CharacterGender characterGender;

    private String characterOrigin;

    private String characterBirthDate;

    private CharacterOrientation characterOrientation;

    private String characterStory;

}
