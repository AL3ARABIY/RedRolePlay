package org.data.redroleplay.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String mtaUsername;
    private LocalDate birthDate;
    private String email;
    private String password;
}
