package org.data.redroleplay.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "character")
@Data
public class CharacterConfiguration {

    private Long moneyStart;

    private Long bankMoneyStart;

    private Integer walkingStyleStart;

    private Integer maleSkinStart;

    private Integer femaleSkinStart;
}
