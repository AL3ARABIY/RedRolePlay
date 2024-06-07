package org.data.redroleplay.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserMtaSerialRequestDto {

    @NotNull(message = "MTA serial is required")
    @NotBlank(message = "Field can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "MTA serial must contain only letters and digits")
    @Length(min = 32, max = 32, message = "MTA serial must be 32 characters long")
    private String mtaSerial;

    @NotNull(message = "{Field.required}")
    private Long userId;
}
