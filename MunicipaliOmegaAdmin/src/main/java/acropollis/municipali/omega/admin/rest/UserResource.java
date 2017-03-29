package acropollis.municipali.omega.admin.rest;

import acropollis.municipali.omega.admin.data.dto.user.UserDetailsInfo;
import acropollis.municipali.omega.admin.data.dto.user.UserId;
import acropollis.municipali.omega.admin.rest_service.user.UserRestService;
import acropollis.municipali.omega.admin.service.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "User", description = "PROTECTED")
public class UserResource {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRestService userRestService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserDetailsInfo getUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @RequestBody UserId userId
    ) {
        return userRestService.getUserDetails(
                authenticationService.getCustomerInfoOrThrow(authToken),
                userId
        );
    }
}
