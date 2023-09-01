package apiUserManagement.controllers;

import apiUserManagement.models.request.LoginUserRequest;
import apiUserManagement.models.response.LoginUserResponse;
import apiUserManagement.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import apiUserManagement.models.response.CoreResponse;

@RestController
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * route user login authentication
     * @param request
     * @return
     */
    @PostMapping(
            path = "/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CoreResponse<LoginUserResponse> login(@RequestBody LoginUserRequest request) {
        LoginUserResponse login = this.authService.login(request);
        return CoreResponse.<LoginUserResponse>builder().status(true).payload(login).build();
    }
}
