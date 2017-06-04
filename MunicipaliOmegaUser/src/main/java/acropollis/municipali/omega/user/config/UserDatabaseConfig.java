package acropollis.municipali.omega.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static acropollis.municipali.omega.common.config.DatabaseConfig.*;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(JPA_REPOSITORIES_LOCATION)
public class UserDatabaseConfig {
    @Bean
    DataSource dataSource() {
        return getDataSource();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        return getEntityManagerFactoryBean(dataSource);
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return getTransactionManager(entityManagerFactory);
    }
}
