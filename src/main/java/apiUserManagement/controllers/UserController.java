package apiUserManagement.controllers;

import apiUserManagement.models.request.RegisterUserRequest;
import apiUserManagement.models.request.UpdateUserRequest;
import apiUserManagement.models.response.DataUserResponse;
import apiUserManagement.models.response.UpdateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import apiUserManagement.entities.User;
import apiUserManagement.models.response.CoreResponse;
import apiUserManagement.services.UserService;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * route user register
     * @param request
     * @return
     */
    @PostMapping(
            path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CoreResponse<String> register(@RequestBody RegisterUserRequest request) {
        this.userService.register(request);
        return CoreResponse.<String>builder().status(true).payload("OK").build();
    }

    /**
     * route get user
     * @param user
     * @return
     */
    @GetMapping(
            path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CoreResponse<Object> get(User user) {
        DataUserResponse dataUserResponse = this.userService.get(user);
        return CoreResponse.builder().status(true).payload(dataUserResponse).build();
    }

    /**
     * route update user
     * @param user
     * @param request
     * @return
     */
    @PatchMapping(
            path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CoreResponse<UpdateUserResponse> update(User user, @RequestBody UpdateUserRequest request) {
         UpdateUserResponse updateUserResponse = this.userService.update(user, request);
        return CoreResponse.<UpdateUserResponse>builder().status(true).payload(updateUserResponse).build();
    }
}
