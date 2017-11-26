package acropollis.municipali.omega.user_notification.rest;

import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.common.exceptions.HttpEntityNotFoundException;
import acropollis.municipali.omega.health_check.service.HealthCheckService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
@Api(tags = "Health Check", description = " ")
public class UserNotificationHealthCheckResource {
    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping("")
    public CommonComponentHealth getHealth() {
        return healthCheckService
                .getHealth()
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }
}
