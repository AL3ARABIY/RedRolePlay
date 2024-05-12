package org.data.redroleplay.configurations;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Primary
    @Bean(name = "websiteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.website")
    public DataSource websiteDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean websiteEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("websiteDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("org.data.redroleplay.entities.website")
                .persistenceUnit("website")
                .properties(
                        Map.of(
                                "hibernate.hbm2ddl.auto", "create",
                                "hibernate.dialect", "org.hibernate.dialect.MySQLDialect"
                        )
                )
                .build();
    }

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
                                "hibernate.hbm2ddl.auto", "none",
                                "hibernate.dialect", "org.hibernate.dialect.MySQLDialect"
                        )
                )
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager websiteTransactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "gameTransactionManager")
    public PlatformTransactionManager gameTransactionManager(
            @Qualifier("gameEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
