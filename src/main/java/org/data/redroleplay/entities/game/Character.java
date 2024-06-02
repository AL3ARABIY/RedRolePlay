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
@Table(name = "characters" , schema = "game")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "charactername")
    private String characterName;

    @Column(name = "account")
    private Long accountId;

    @Column(name = "skin")
    private Integer skinId; // 14 for male , 10 for female

    private Long money;

    @Column(name = "gender")
    private Boolean isFemale;

    private Integer age; // 1 - 100

    @Column(name = "bankmoney")
    private Long bankMoney;

    @Column(name = "walkingstyle")
    private Integer walkingStyle;
}
