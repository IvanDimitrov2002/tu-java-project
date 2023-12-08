package tu.social.project.service;

import tu.social.project.entity.UserEntity;
import tu.social.project.payload.request.LoginUserRequest;
import tu.social.project.payload.request.RegisterUserRequest;
import tu.social.project.payload.response.LoginUserResponse;
import tu.social.project.payload.response.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request);

    LoginUserResponse login(LoginUserRequest request);
    UserEntity getUserById(String userId);
}
