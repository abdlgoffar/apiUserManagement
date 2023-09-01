package apiUserManagement.resolver;

import apiUserManagement.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;
import apiUserManagement.entities.User;


@AllArgsConstructor
@Component
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;
    /**
     * method for register class type parameter argument resolver
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.equals(parameter.getParameterType());
    }

    /**
     * method for logic argument resolver
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //get request header for authentication
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("X-API-TOKEN");

        //check there request header authentication or not
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");

        //check token authentication valid or not
        User user = this.userRepository.findByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: invalid token"));

        //check token authentication expired or not
        if (user.getTokenExpiredAt() < System.currentTimeMillis()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: token has expired");

        return user;
    }
}
