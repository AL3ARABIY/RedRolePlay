package org.data.redroleplay.dtos.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDisplayForAdminDto {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String email;

    private String mtaUsername;

    private String mtaSerial;

    private String discordId;

    private String discordUsername;

    private String discordAvatar;

    private LocalDateTime registerDate;

    private Integer maxWhitelistRequests;
}
