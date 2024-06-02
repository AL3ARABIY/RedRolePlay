package org.data.redroleplay.services;

import org.data.redroleplay.entities.game.Character;
import org.data.redroleplay.entities.website.WhitelistRequest;

public interface CharacterService {

    Character create(WhitelistRequest whitelistRequest);
}
