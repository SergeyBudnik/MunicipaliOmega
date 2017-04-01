package acropollis.municipali.omega;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"acropollis.municipali.omega.admin", "acropollis.municipali.omega.user"})
public class AppConfig {
}