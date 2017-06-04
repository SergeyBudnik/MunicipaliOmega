package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.dto.health_check.HealthCheck;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/health-check")
@Api(tags = "Health Check", description = " ")
public class AdminHealthCheckResource {
    @GetMapping("")
    public HealthCheck getHealth() {
        return HealthCheck.builder()
                .globalHealth(true)
                .databaseHealth(false)
                .lastUpdateDate(new Date().getTime())
                .build();
    }
}
