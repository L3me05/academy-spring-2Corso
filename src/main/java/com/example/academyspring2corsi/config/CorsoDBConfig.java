package com.example.academyspring2corsi.config;

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
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.academyspring2corsi.repository.corso",
        entityManagerFactoryRef = "corsoEntityManager",
        transactionManagerRef = "corsoTransactionManager"
)
public class CorsoDBConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource corsoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean corsoEntityManager(EntityManagerFactoryBuilder builder) {
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return builder
                .dataSource(corsoDataSource())
                .packages("com.example.academyspring2corsi.data.entityCorso")
                .persistenceUnit("corso")
                .properties(Map.of(
                        "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                        "hibernate.hbm2ddl.auto", "update",
                        "hibernate.show_sql", "true",
                        "hibernate.format_sql", "true"
                ))
                .build();
    }


    @Primary
    @Bean
    public PlatformTransactionManager corsoTransactionManager(@Qualifier("corsoEntityManager")EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }



}
