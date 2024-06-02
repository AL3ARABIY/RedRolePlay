package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.configurations.CharacterConfiguration;
import org.data.redroleplay.entities.game.Character;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.data.redroleplay.enums.CharacterGender;
import org.data.redroleplay.repositories.game.CharacterRepository;
import org.data.redroleplay.services.CharacterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    private final CharacterConfiguration characterConfiguration;

    @Value("${season.year}")
    private Integer seasonYear;

    @Override
    public Character create(WhitelistRequest whitelistRequest) {

        Character newCharacter = Character.builder()
                .characterName(whitelistRequest.getCharacterFullName())
                .age(calculateAge(whitelistRequest.getCharacterBirthDate()))
                .accountId(whitelistRequest.getUser().getAccountId())
                .isFemale(isFemale(whitelistRequest.getCharacterGender()))
                .money(characterConfiguration.getMoneyStart())
                .bankMoney(characterConfiguration.getBankMoneyStart())
                .skinId(determineSkinId(whitelistRequest.getCharacterGender()))
                .walkingStyle(characterConfiguration.getWalkingStyleStart())
                .build();

        return characterRepository.save(newCharacter);

    }

    private Integer calculateAge(LocalDate localDate) {
        int age =  seasonYear - localDate.getYear();
        return age > 100 ? 100 : Math.max(age, 1);
    }

    private Boolean isFemale(CharacterGender characterGender) {
        return CharacterGender.FEMALE.equals(characterGender);
    }

    private Integer determineSkinId(CharacterGender characterGender) {
        return isFemale(characterGender) ?
                characterConfiguration.getFemaleSkinStart() :
                characterConfiguration.getMaleSkinStart();
    }
}
