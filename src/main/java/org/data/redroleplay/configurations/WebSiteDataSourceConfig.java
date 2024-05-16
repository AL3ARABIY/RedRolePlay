package org.data.redroleplay.configurations;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
          basePackages = "org.data.redroleplay.repositories.website",
          entityManagerFactoryRef = "webSiteEntityManagerFactory",
          transactionManagerRef = "webSiteTransactionManager"
)
public class WebSiteDataSourceConfig {
    @Primary
    @Bean(name = "webSiteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.website")
    public DataSource websiteDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "webSiteEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean websiteEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("webSiteDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("org.data.redroleplay.entities.website")
                .persistenceUnit("website")
                .properties(
                        Map.of(
                                "hibernate.hbm2ddl.auto", "update"
                        )
                )
                .build();
    }

    @Primary
    @Bean(name = "webSiteTransactionManager")
    public PlatformTransactionManager websiteTransactionManager(
            @Qualifier("webSiteEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
