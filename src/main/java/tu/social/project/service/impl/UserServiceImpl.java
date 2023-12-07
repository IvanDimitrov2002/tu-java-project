package tu.social.project.service.impl;

import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tu.social.project.component.jwt.UserJwtTokenGenerator;
import tu.social.project.entity.UserEntity;
import tu.social.project.exception.UserAlreadyExistsException;
import tu.social.project.exception.UserNotExistsException;
import tu.social.project.exception.UserPasswordIncorrectException;
import tu.social.project.mapper.UserMapper;
import tu.social.project.payload.request.LoginUserRequest;
import tu.social.project.payload.request.RegisterUserRequest;
import tu.social.project.payload.response.LoginUserResponse;
import tu.social.project.payload.response.RegisterUserResponse;
import tu.social.project.repository.UserRepository;
import tu.social.project.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserJwtTokenGenerator userJwtTokenGenerator;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserJwtTokenGenerator userJwtTokenGenerator
    ) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userJwtTokenGenerator = userJwtTokenGenerator;
    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        if (this.userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(request.email());
        }
        String password = this.passwordEncoder.encode(request.password());
        UserEntity userEntity = this.userMapper.map(request, password);
        this.userRepository.save(userEntity);
        String token = this.userJwtTokenGenerator.generateToken(userEntity);
        return this.userMapper.mapRegisterUserResponse(userEntity, token);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByEmail(request.email());
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new UserNotExistsException(request.email()));
        if (!this.passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            throw new UserPasswordIncorrectException(request.email());
        }
        String token = this.userJwtTokenGenerator.generateToken(userEntity);
        return this.userMapper.mapLoginUserResponse(userEntity, token);
    }


    @Override
    public UserEntity getUserById(String id) {
        return this.userRepository.findById(id).orElse(null);
    }

}