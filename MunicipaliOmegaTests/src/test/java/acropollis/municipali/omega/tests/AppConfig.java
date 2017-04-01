package acropollis.municipali.omega.tests;

import acropollis.municipali.omega.user.config.UserDatabaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = {
                "acropollis.municipali.omega.admin",
                "acropollis.municipali.omega.database",
                "acropollis.municipali.omega.tests.steps"
        },

        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserDatabaseConfig.class)
        }
)
public class AppConfig {
}