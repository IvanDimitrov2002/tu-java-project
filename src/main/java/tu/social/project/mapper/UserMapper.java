package tu.social.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tu.social.project.entity.UserEntity;
import tu.social.project.payload.request.RegisterUserRequest;
import tu.social.project.payload.response.LoginUserResponse;
import tu.social.project.payload.response.RegisterUserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", source = "password")
    UserEntity map(RegisterUserRequest request, String password);
    RegisterUserResponse mapRegisterUserResponse(UserEntity entity, String token);
    LoginUserResponse mapLoginUserResponse(UserEntity entity, String token);
}
