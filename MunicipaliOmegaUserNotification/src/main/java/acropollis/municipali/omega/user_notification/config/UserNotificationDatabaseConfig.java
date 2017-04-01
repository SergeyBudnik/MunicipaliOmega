package acropollis.municipali.omega.user_notification.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("acropollis.municipali.omega.database.db.dao")
public class UserNotificationDatabaseConfig {
    @Bean
    DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();

        dataSourceConfig.setDriverClassName(config.getString("database.driver"));
        dataSourceConfig.setJdbcUrl(config.getString("database.url"));
        dataSourceConfig.setUsername(config.getString("database.username"));
        dataSourceConfig.setPassword(config.getString("database.password"));

        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("acropollis.municipali.omega.database");

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", config.getString("database.dialect"));
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");//config.getString("database.startupAction"));
        jpaProperties.put("hibernate.show_sql", config.getString("database.showSql"));
        jpaProperties.put("hibernate.format_sql", config.getString("database.formatSql"));
        jpaProperties.put("hibernate.id.new_generator_mappings", "false");

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}
