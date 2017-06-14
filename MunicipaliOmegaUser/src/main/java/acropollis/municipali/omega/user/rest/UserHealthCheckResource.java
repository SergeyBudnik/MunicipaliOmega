package acropollis.municipali.omega.user.rest;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acropollis.municipali.omega.common.dto.health_check.HealthCheck;

import java.util.Date;

@RestController
@RequestMapping("/health-check")
@Api(tags = "Health Check", description = " ")
public class UserHealthCheckResource {
    @GetMapping("")
    public HealthCheck getHealth() {
        return HealthCheck.builder()
                .globalHealth(true)
                .databaseHealth(false)
                .lastUpdateDate(new Date().getTime())
                .build();
    }
}
