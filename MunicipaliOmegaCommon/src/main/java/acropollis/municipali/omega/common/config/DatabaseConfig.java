package acropollis.municipali.omega.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.util.Properties;

import static acropollis.municipali.omega.common.config.PropertiesConfig.config;

public class DatabaseConfig {
    public static final String JPA_REPOSITORIES_LOCATION = "acropollis.municipali.omega.database.db.dao";

    public static DataSource getDataSource(int maxPoolSize) {
        HikariConfig dataSourceConfig = new HikariConfig(); {
            dataSourceConfig.setDriverClassName(config.getDatabaseDriver().getValue());
            dataSourceConfig.setJdbcUrl(config.getDatabaseUrl().getValue());
            dataSourceConfig.setUsername(config.getDatabaseUsername().getValue());
            dataSourceConfig.setPassword(config.getDatabasePassword().getValue());
            dataSourceConfig.setMaximumPoolSize(maxPoolSize);
            dataSourceConfig.setMaxLifetime((int) ((long) config.getDatabaseMaxActive().getValue()));
        }

        return new HikariDataSource(dataSourceConfig);
    }

    public static LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("acropollis.municipali.omega.database");

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", config.getDatabaseDialect().getValue());
        jpaProperties.put("hibernate.hbm2ddl.auto", config.getDatabaseStartupAction().getValue());
        jpaProperties.put("hibernate.show_sql", config.getDatabaseShowSql().getValue());
        jpaProperties.put("hibernate.format_sql", config.getDatabaseFormatSql().getValue());
        jpaProperties.put("hibernate.id.new_generator_mappings", config.getDatabaseIdNewGeneratorMappings().getValue());
        jpaProperties.put("hibernate.connection.release_mode", config.getDatabaseConnectionReleaseMode().getValue());

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    public static JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}
