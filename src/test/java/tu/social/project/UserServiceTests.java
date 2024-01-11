package tu.social.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

	@Test
	public void registerUser_Success() {
		RegisterUserRequest request = new RegisterUserRequest(any(), any(), any());
		UserEntity userEntity = new UserEntity();
		String token = "someToken";

		when(userRepository.existsByEmail(request.email())).thenReturn(false);
		when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
		when(userMapper.map(request, "encodedPassword")).thenReturn(userEntity);
		when(userJwtTokenGenerator.generateToken(userEntity)).thenReturn(token);

		RegisterUserResponse response = userService.register(request);

		assertNotNull(response);
		assertEquals(token, response.token());
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void registerUser_UserAlreadyExists() {
		RegisterUserRequest request = new RegisterUserRequest(any(), any(), any());

		when(userRepository.existsByEmail(request.email())).thenReturn(true);

		userService.register(request);
	}

	@Test
	public void loginUser_Success() {
		LoginUserRequest request = new LoginUserRequest(any(), any());
		UserEntity userEntity = new UserEntity();
		String token = "someToken";

		when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(userEntity));
		when(passwordEncoder.matches(request.password(), userEntity.getPassword())).thenReturn(true);
		when(userJwtTokenGenerator.generateToken(userEntity)).thenReturn(token);

		LoginUserResponse response = userService.login(request);

		assertNotNull(response);
		assertEquals(token, response.token());
	}

	@Test(expected = UserNotExistsException.class)
	public void loginUser_UserNotExists() {
		LoginUserRequest request = new LoginUserRequest(any(), any());

		when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

		userService.login(request);
	}

	@Test(expected = UserPasswordIncorrectException.class)
	public void loginUser_UserPasswordIncorrect() {
		LoginUserRequest request = new LoginUserRequest(any(), any());
		UserEntity userEntity = new UserEntity();

		when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(userEntity));
		when(passwordEncoder.matches(request.password(), userEntity.getPassword())).thenReturn(false);

		userService.login(request);
	}

	@Test
	public void getUserById_Success() {
		String id = "someId";
		UserEntity userEntity = new UserEntity();

		when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));

		UserEntity result = userService.getUserById(id);

		assertNotNull(result);
		assertEquals(userEntity, result);
		// Additional assertions
	}

	@Test
	public void getUserById_NotFound() {
		String id = "someId";

		when(userRepository.findById(id)).thenReturn(Optional.empty());

		UserEntity result = userService.getUserById(id);

		assertNull(result);
	}

}
