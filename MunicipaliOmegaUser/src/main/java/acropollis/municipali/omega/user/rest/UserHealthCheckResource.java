package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.common.dto.health_check.HealthCheck;
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
public class UserHealthCheckResource {
    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping("")
    public HealthCheck getHealth() {
        return healthCheckService
                .getHealth()
                .orElseThrow(() -> new HttpEntityNotFoundException(""));
    }
}
