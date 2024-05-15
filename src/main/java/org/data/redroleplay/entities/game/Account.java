package org.data.redroleplay.entities.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts" , schema = "game")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    @Column(name = "mtaserial")
    private String mtaSerial;

    @Column(name = "discord_id")
    private String discordId;

    @Column(name = "discord_username")
    private String discordUsername;

    private String salt;

    private String password;

    @Column(name = "registerdate")
    private LocalDateTime registerDate;
}
