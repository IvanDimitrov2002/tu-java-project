package tu.social.project;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tu.social.project.entity.*;
import tu.social.project.exception.*;
import tu.social.project.payload.response.UserResponse;
import tu.social.project.repository.*;
import tu.social.project.service.impl.LikeServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LikeServiceTests {

	@Mock
	private LikeRepository likeRepository;
	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private LikeServiceImpl likeService;


	@Test
	public void addLikeToPost_Success() {
		String postId = "somePostId";
		UserEntity user = new UserEntity();
		PostEntity post = new PostEntity();
		post.setLikes(new HashSet<>());

		when(postRepository.findById(postId)).thenReturn(Optional.of(post));
		when(likeRepository.existsByUserAndPost(user, post)).thenReturn(false);

		likeService.addLikeToPost(postId, user);

		verify(likeRepository).save(any(LikeEntity.class));
		verify(postRepository).save(post);
	}

	@Test
	public void getNumberOfLikes_Success() {
		String postId = "somePostId";
		PostEntity post = new PostEntity();
		LikeEntity like = new LikeEntity();
		UserEntity userEntity = new UserEntity();
		userEntity.setId("someUserId");
		like.setUser(userEntity);
		post.setLikes(Set.of(like));
		when(postRepository.findById(postId)).thenReturn(Optional.of(post));

		Integer numberOfLikes = likeService.getNumberOfLikes(postId);

		assertNotNull(numberOfLikes);
		assertEquals(post.getLikes().size(), numberOfLikes);
	}

	@Test
	public void getUsersWhoLikedPost_Success() {
		String postId = "somePostId";
		PostEntity post = new PostEntity();
		LikeEntity like = new LikeEntity();
		UserEntity userEntity = new UserEntity();
		userEntity.setId("someUserId");
		like.setUser(userEntity);
		post.setLikes(Set.of(like));
		when(postRepository.findById(postId)).thenReturn(Optional.of(post));

		List<UserResponse> users = likeService.getUsersWhoLikedPost(postId);

		assertNotNull(users);
		assertEquals(1, users.size());
	}

	@Test(expected = PostNotFoundException.class)
	public void addLikeToPost_PostNotFound() {
		String postId = "somePostId";
		UserEntity user = new UserEntity(/* parameters */);

		when(postRepository.findById(postId)).thenReturn(Optional.empty());

		likeService.addLikeToPost(postId, user);
	}

	@Test(expected = AlreadyLikedPostException.class)
	public void addLikeToPost_AlreadyLikedPost() {
		String postId = "somePostId";
		UserEntity user = new UserEntity();
		PostEntity post = new PostEntity();

		when(postRepository.findById(postId)).thenReturn(Optional.of(post));
		when(likeRepository.existsByUserAndPost(user, post)).thenReturn(true);

		likeService.addLikeToPost(postId, user);
	}

	@Test(expected = PostNotFoundException.class)
	public void getNumberOfLikes_PostNotFound() {
		String postId = "somePostId";

		when(postRepository.findById(postId)).thenReturn(Optional.empty());

		likeService.getNumberOfLikes(postId);
	}

	@Test(expected = PostNotFoundException.class)
	public void getUsersWhoLikedPost_PostNotFound() {
		String postId = "somePostId";

		when(postRepository.findById(postId)).thenReturn(Optional.empty());

		likeService.getUsersWhoLikedPost(postId);
	}

}
