package org.data.redroleplay.services.implementations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.models.UserProfile;
import org.data.redroleplay.services.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;

    @Cacheable("profileCache")
    public UserProfile getUserProfile(org.springframework.security.core.userdetails.User userDetails) {

        Optional<User> authenticatedUser = userService.getUserByUsername(userDetails.getUsername());

        return authenticatedUser.map(user -> UserProfile.builder()
                        .discordAvatarUrl(user.getDiscordAvatar())
                        .email(user.getEmail())
                        .mtaUsername(user.getMtaUsername())
                        .build())
                .orElse(getDefaultUserProfile());

    }

    private UserProfile getDefaultUserProfile() {
        return UserProfile.builder()
                .discordAvatarUrl("https://cdn.discordapp.com/avatars/791452235187224618/963491b056b273c78cc2347d1d7e304d")
                .email("Default@gmail.com")
                .mtaUsername("DefaultUsername")
                .build();
    }

}
