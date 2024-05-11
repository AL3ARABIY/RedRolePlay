package org.data.redroleplay.entities.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private Long id;

    private String email;

    private String username;

    @Column(name = "mtaserial")
    private String mtaSerial;

    private String discordId;

    private String salt;

    private String password;
}
