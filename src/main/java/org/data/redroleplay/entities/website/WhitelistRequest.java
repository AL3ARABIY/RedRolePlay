package org.data.redroleplay.entities.website;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.data.redroleplay.enums.CharacterGender;
import org.data.redroleplay.enums.CharacterOrientation;
import org.data.redroleplay.enums.WhitelistRequestStatus;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "website")
public class WhitelistRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String characterFirstName;

    private String characterLastName;

    @Formula("concat(characterFirstName, '_', characterLastName)")
    private String characterFullName;

    private CharacterGender characterGender;

    @Column(length = 20)
    private String characterOrigin;

    private LocalDate characterBirthDate;

    private CharacterOrientation characterOrientation;

    @Column(length = 5000)
    private String characterStory;

    private LocalDateTime requestDate;

    private WhitelistRequestStatus status;

    private LocalDateTime verifiedDate;

    @ManyToOne
    private User verifiedBy;

    @Column(length = 5000)
    private String reason;

    @Column(length = 5000)
    private String note;

}
