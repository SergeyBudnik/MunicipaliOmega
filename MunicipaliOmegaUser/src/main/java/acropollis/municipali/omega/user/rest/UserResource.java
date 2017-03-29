package acropollis.municipali.omega.user.rest;

import acropollis.municipali.omega.user.data.dto.user.User;
import acropollis.municipali.omega.user.data.response.RegistrationResponse;
import acropollis.municipali.omega.user.rest_service.user.UserRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "User", description = " ")
public class UserResource {
    @Autowired
    private UserRestService userRestService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public RegistrationResponse authenticate(@RequestBody User user) {
        String authToken = userRestService.authenticate(user);

        RegistrationResponse response = new RegistrationResponse(); {
            response.setAuthToken(authToken);
        }

        return response;
    }
}
