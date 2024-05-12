package org.data.redroleplay.services;

import org.data.redroleplay.services.implementations.DiscordDataExtractorServiceImpl.DiscordUser;

import java.util.Optional;

public interface DiscordDataExtractorService {

    Optional<DiscordUser> getDiscordInfo(String accessToken);
}
