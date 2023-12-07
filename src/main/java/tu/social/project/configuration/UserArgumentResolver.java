package tu.social.project.configuration;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import tu.social.project.anotation.User;
import tu.social.project.component.jwt.UserJwtTokenGenerator;
import tu.social.project.entity.UserEntity;
import tu.social.project.exception.UnauthorizedUserException;
import tu.social.project.service.UserService;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserJwtTokenGenerator userJwtTokenGenerator;
    private final UserService userService;

    public UserArgumentResolver(UserJwtTokenGenerator userJwtTokenGenerator, UserService userService) {
        this.userJwtTokenGenerator = userJwtTokenGenerator;
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(User.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnauthorizedUserException();
        }
        token = token.replace("Bearer ", "");
        String userId = this.userJwtTokenGenerator.getUserIdFromToken(token);
        UserEntity userEntity = this.userService.getUserById(userId);

        if (userEntity == null) {
            throw new UnauthorizedUserException();
        }

        return userEntity;
    }
}
