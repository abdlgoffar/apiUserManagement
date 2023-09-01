package apiUserManagement;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import apiUserManagement.resolver.UserMethodArgumentResolver;

import java.util.List;

@AllArgsConstructor
@Configuration
public class MethodArgumentResolver implements WebMvcConfigurer {
    private final UserMethodArgumentResolver userMethodArgumentResolver;

    /**
     * method for register method argument resolver
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(userMethodArgumentResolver);
    }
}
