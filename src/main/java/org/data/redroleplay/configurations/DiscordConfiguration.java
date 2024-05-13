package org.data.redroleplay.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "discord")
@Data
public class DiscordConfiguration {

    private String clientId;

    private String clientSecret;

    private String authorizationUri;

    private String userInfoUri;

    private String redirectUri;

    private String tokenUri;
}
