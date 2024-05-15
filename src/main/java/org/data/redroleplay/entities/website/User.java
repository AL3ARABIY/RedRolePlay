package org.data.redroleplay.entities.website;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "website")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String mtaUsername;

    @Column(unique = true)
    private String mtaSerial;

    @Column(unique = true)
    private String discordId;

    @Column(unique = true)
    private String discordUsername;

    private String discordAvatar;

    private String salt;

    private String mtaPassword;

    @Column(unique = true)
    private Long accountId;

    private LocalDateTime registerDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "authority_id", referencedColumnName = "id"))

    private Collection<Authority> authorities;
}
