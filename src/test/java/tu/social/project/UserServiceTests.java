package tu.social.project;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import tu.social.project.component.jwt.UserJwtTokenGenerator;
import tu.social.project.entity.UserEntity;
import tu.social.project.exception.*;
import tu.social.project.mapper.UserMapper;
import tu.social.project.payload.request.*;
import tu.social.project.payload.response.*;
import tu.social.project.repository.UserRepository;
import tu.social.project.service.impl.UserServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

	@Mock
	private UserMapper userMapper;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserJwtTokenGenerator userJwtTokenGenerator;

	@InjectMocks
	private UserServiceImpl userService;

	private final RegisterUserRequest registerUserRequest = new RegisterUserRequest("email", "password", "name");
	private final LoginUserRequest loginUserRequest = new LoginUserRequest("email", "password");

	@Test
	public void registerUser_Success() {
		UserEntity userEntity = new UserEntity();
		String token = "someToken";

		when(userRepository.existsByEmail(registerUserRequest.email())).thenReturn(false);
		when(passwordEncoder.encode(registerUserRequest.password())).thenReturn("encodedPassword");
		when(userMapper.map(registerUserRequest, "encodedPassword")).thenReturn(userEntity);
		when(userMapper.mapRegisterUserResponse(userEntity, token)).thenReturn(new RegisterUserResponse("1", token));
		when(userJwtTokenGenerator.generateToken(userEntity)).thenReturn(token);

		RegisterUserResponse response = userService.register(registerUserRequest);

		assertNotNull(response);
		assertEquals(token, response.token());
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void registerUser_UserAlreadyExists() {

		when(userRepository.existsByEmail(registerUserRequest.email())).thenReturn(true);

		userService.register(registerUserRequest);
	}

	@Test
	public void loginUser_Success() {
		UserEntity userEntity = new UserEntity();
		userEntity.setPassword("encodedPassword");
		String token = "someToken";

		when(userRepository.findByEmail(loginUserRequest.email())).thenReturn(Optional.of(userEntity));
		when(passwordEncoder.matches(loginUserRequest.password(), userEntity.getPassword())).thenReturn(true);
		when(userJwtTokenGenerator.generateToken(userEntity)).thenReturn(token);
		when(userMapper.mapLoginUserResponse(userEntity, token)).thenReturn(new LoginUserResponse("1", token));

		LoginUserResponse response = userService.login(loginUserRequest);

		assertNotNull(response);
		assertEquals(token, response.token());
	}

	@Test(expected = UserNotExistsException.class)
	public void loginUser_UserNotExists() {

		when(userRepository.findByEmail(registerUserRequest.email())).thenReturn(Optional.empty());

		userService.login(loginUserRequest);
	}

	@Test(expected = UserPasswordIncorrectException.class)
	public void loginUser_UserPasswordIncorrect() {
		UserEntity userEntity = new UserEntity();

		when(userRepository.findByEmail(registerUserRequest.email())).thenReturn(Optional.of(userEntity));

		userService.login(loginUserRequest);
	}

	@Test
	public void getUserById_Success() {
		String id = "someId";
		UserEntity userEntity = new UserEntity();

		when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

		UserEntity result = userService.getUserById(id);

		assertNotNull(result);
		assertEquals(userEntity, result);
	}

	@Test
	public void getUserById_NotFound() {
		String id = "someId";

		when(userRepository.findById(id)).thenReturn(Optional.empty());

		UserEntity result = userService.getUserById(id);

		assertNull(result);
	}

}
