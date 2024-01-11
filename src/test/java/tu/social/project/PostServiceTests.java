package tu.social.project;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tu.social.project.entity.PostEntity;
import tu.social.project.mapper.PostMapper;
import tu.social.project.payload.request.CreatePostRequest;
import tu.social.project.payload.response.*;
import tu.social.project.repository.PostRepository;
import tu.social.project.service.impl.PostServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTests {

	@Mock
	private PostRepository postRepository;

	@Mock
	private PostMapper postMapper;

	@InjectMocks
	private PostServiceImpl postService;

	private final CreatePostRequest request = new CreatePostRequest("1", "2", "3");

	private final GetPostsResponse getPostsResponse = new GetPostsResponse("1", "2", "3", "4");

	@Test
	public void createPost_Success() {
		PostEntity postEntity = new PostEntity();
		CreatePostResponse expectedResponse = new CreatePostResponse("1", "2", "3", "4");

		when(postMapper.mapToEntity(request)).thenReturn(postEntity);
		when(postMapper.mapToCreatePostResponse(postEntity)).thenReturn(expectedResponse);

		CreatePostResponse response = postService.createPost(request);

		assertNotNull(response);
		assertEquals(expectedResponse, response);
		verify(postRepository).save(postEntity);
	}

	@Test
	public void getPostsByAuthor_Success() {
		String authorId = "authorId";
		List<PostEntity> posts = List.of(new PostEntity());
		List<GetPostsResponse> expectedResponses = List.of(getPostsResponse);

		when(postRepository.findAllByAuthorId(authorId)).thenReturn(posts);
		when(postMapper.mapToGetPostsResponse(posts)).thenReturn(expectedResponses);

		List<GetPostsResponse> responses = postService.getPostsByAuthor(authorId);

		assertNotNull(responses);
		assertEquals(expectedResponses.size(), responses.size());
	}

}
