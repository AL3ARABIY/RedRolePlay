package org.data.redroleplay.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotNull(message = "First name is required")
    @NotBlank(message = "Field can not be blank")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "First name must start with an uppercase letter and contain only lowercase letters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Field can not be blank")
    @Pattern(regexp = "^[A-Z][a-z]+$", message = "Last name must start with an uppercase letter and contain only lowercase letters")
    private String lastName;

    @NotNull(message = "MTA username is required")
    @NotBlank(message = "Field can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "MTA username must contain at least 3 characters and contain only letters and digits")
    private String mtaUsername;

    @NotNull(message = "MTA serial is required")
    @NotBlank(message = "Field can not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "MTA serial must contain only letters and digits")
    @Length(min = 32, max = 32, message = "MTA serial must be 32 characters long")
    private String mtaSerial;

    @NotNull(message = "Discord ID is required")
    @NotBlank(message = "Field can not be blank")
    private String discordId;

    @NotNull(message = "Discord username is required")
    @NotBlank(message = "Field can not be blank")
    @Pattern(regexp = "^\\w+$", message =  "Discord Username must contain only letters and digits and underscore")
    private String discordUsername;

    @NotNull(message ="Discord Avatar is required")
    @NotBlank(message = "Field can not be blank")
    private String discordAvatar;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
