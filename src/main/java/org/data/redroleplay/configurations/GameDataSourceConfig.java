package org.data.redroleplay.configurations;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.data.redroleplay.repositories.game",
        entityManagerFactoryRef = "gameEntityManagerFactory"
)
public class GameDataSourceConfig {

    @Bean(name = "gameDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.game")
    public DataSource gameDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "gameEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean gameEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("gameDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("org.data.redroleplay.entities.game")
                .persistenceUnit("game")
                .properties(
                        Map.of(
                                "hibernate.hbm2ddl.auto", "update",
                                "hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"
                        )
                )
                .build();
    }

    @Bean(name = "gameTransactionManager")
    public PlatformTransactionManager gameTransactionManager(
            @Qualifier("gameEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
