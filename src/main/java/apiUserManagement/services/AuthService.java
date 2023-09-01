package apiUserManagement.services;

import apiUserManagement.models.response.LoginUserResponse;
import apiUserManagement.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import apiUserManagement.beans.Validation;
import apiUserManagement.entities.User;
import apiUserManagement.models.request.LoginUserRequest;
import apiUserManagement.security.BCrypt;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    private final Validation validation;

    /**
     *
     * @param request
     * @return
     */
    @Transactional
    public LoginUserResponse login(LoginUserRequest request) {
        //validate user login
        this.validation.valid(request);

        //check account has registered
        User userAccount = this.userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username or password invalid"));

        //check account password
        if (BCrypt.checkpw(request.getPassword(), userAccount.getPassword()) == true) {
            //created user token for authentication
            userAccount.setToken(UUID.randomUUID().toString());
            userAccount.setTokenExpiredAt(System.currentTimeMillis() + ((60_000L * 60L * 24L) * 30L));//1 minute in current millis = 60.000L
            this.userRepository.save(userAccount);
            return LoginUserResponse.builder()
                    .token(userAccount.getToken()).build();
        } else {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username or password invalid");
        }
    }
}
