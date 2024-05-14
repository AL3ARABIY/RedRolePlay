package org.data.redroleplay.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.data.redroleplay.annotations.EnumValue;
import org.data.redroleplay.enums.CharacterGender;
import org.data.redroleplay.enums.CharacterOrientation;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhitelistRequestDto {

    @NotNull(message = "Field.required")
    private String characterFirstName;

    @NotNull(message = "Field.required")
    private String characterLastName;

    @NotNull(message = "Field.required")
    @EnumValue(enumClass = CharacterGender.class, message="NotValidEnum.whitelistRequest.characterGender")
    private String characterGender;

    @NotNull(message = "Field.required")
    private String characterOrigin;

    @NotNull(message = "Field.required")
    @Past(message = "BirthDate.past")
    private LocalDate characterBirthDate;

    @NotNull(message = "Field.required")
    @EnumValue(enumClass = CharacterOrientation.class, message="NotValidEnum.whitelistRequest.characterOrientation")
    private String characterOrientation;

    @NotNull(message = "Field.required")
    @Size(min = 100, max = 5000, message = "{Field.size}")
    private String characterStory;

}
