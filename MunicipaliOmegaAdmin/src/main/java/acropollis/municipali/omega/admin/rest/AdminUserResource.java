package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.common.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.common.dto.user.UserId;
import acropollis.municipali.omega.admin.rest_service.user.AdminUserRestService;
import acropollis.municipali.omega.admin.service.authentication.AdminAuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "User", description = "PROTECTED")
public class AdminUserResource {
    @Autowired
    private AdminAuthenticationService adminAuthenticationService;
    @Autowired
    private AdminUserRestService adminUserRestService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserDetailsInfo getUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @RequestBody UserId userId
    ) {
        return adminUserRestService.getUserDetails(
                adminAuthenticationService.getCustomerInfoOrThrow(authToken),
                userId
        );
    }
}
