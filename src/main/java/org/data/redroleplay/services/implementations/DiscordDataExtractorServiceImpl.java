package org.data.redroleplay.services.implementations;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.configurations.DiscordConfiguration;
import org.data.redroleplay.services.DiscordDataExtractorService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscordDataExtractorServiceImpl implements DiscordDataExtractorService {

    private final DiscordConfiguration discordConfiguration;


    @Override
    public Optional<DiscordUser> getDiscordInfo(String code) {

        try {
            if (code == null || code.isEmpty()) {
                System.err.println("Authorization code not found in URL");
                return Optional.empty();
            }

            String clientId = discordConfiguration.getClientId();
            String clientSecret = discordConfiguration.getClientSecret();
            String redirectUri = discordConfiguration.getRedirectUri();
            String tokenUri = discordConfiguration.getTokenUri();
            String userInfoUri = discordConfiguration.getUserInfoUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("grant_type", "authorization_code");
            map.add("code", code);
            map.add("redirect_uri", redirectUri);
            map.add("scope", "identify");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            RestTemplate restTemplate = new RestTemplate();
            AccessTokenResponse response = restTemplate.postForObject(tokenUri, request, AccessTokenResponse.class);

            if (response == null || response.getAccess_token() == null) {
                System.err.println("Error exchanging code for access token");
                return Optional.empty();
            }

            String accessToken = response.getAccess_token();

            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setBearerAuth(accessToken);

            HttpEntity<String> userRequest = new HttpEntity<>(userHeaders);

            DiscordUser user = restTemplate.exchange(userInfoUri, HttpMethod.GET, userRequest, DiscordUser.class).getBody();

            return user == null ? Optional.empty() : Optional.of(user);
        } catch (Exception e) {
            System.err.println("Error getting user info from Discord: " + e.getMessage());
            return Optional.empty();
        }

    }

    @Data
    static class AccessTokenResponse {
        private String access_token;
    }

    @Data
    public static class DiscordUser {
        private String id;
        private String username;
        private String globalName;
        private String avatar;
        private Boolean verified;
        private String email;
    }
}
