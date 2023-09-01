package apiUserManagement.services;

import apiUserManagement.beans.Validation;
import apiUserManagement.models.request.RegisterUserRequest;
import apiUserManagement.models.request.UpdateUserRequest;
import apiUserManagement.models.response.DataUserResponse;
import apiUserManagement.models.response.UpdateUserResponse;
import apiUserManagement.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import apiUserManagement.entities.User;
import apiUserManagement.security.BCrypt;

import java.util.Objects;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final Validation validation;

    /**
     * register
     * @param request
     */
    @Transactional
    public void register(RegisterUserRequest request) {
        //validate user register
        this.validation.valid(request);

        //check user has register
        if (this.userRepository.existsById(request.getUsername())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username has registered");

        //save user register
        this.userRepository.save(new User(
                request.getUsername(),
                BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()),
                request.getName()));
    }

    /**
     * get user
     * @param user
     * @return
     */
    public DataUserResponse get(User user) {
        return DataUserResponse.builder()
                .username(user.getUsername())
                .name(user.getName()).build();
    }

    /**
     * update user
     * @param user
     * @param request
     * @return
     */
    @Transactional
    public UpdateUserResponse update(User user, UpdateUserRequest request) {
        //validate user update
        this.validation.valid(request);

        //update user
        if (Objects.nonNull(request.getUsername())) user.setUsername(request.getUsername());
        if (Objects.nonNull(request.getName())) user.setName(request.getName());
        if (Objects.nonNull(request.getPassword())) user.setPassword(request.getPassword());
        this.userRepository.save(user);

        return UpdateUserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword()).build();
    }


}
