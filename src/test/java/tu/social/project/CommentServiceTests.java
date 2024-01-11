package tu.social.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import jakarta.persistence.*;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tu.social.project.entity.*;
import tu.social.project.exception.*;
import tu.social.project.mapper.CommentMapper;
import tu.social.project.payload.request.*;
import tu.social.project.payload.response.*;
import tu.social.project.repository.CommentRepository;
import tu.social.project.service.impl.CommentServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTests {

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private CommentMapper commentMapper;

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private CommentServiceImpl commentService;

	private final CreateCommentRequest createCommentRequest = new CreateCommentRequest("1", "2");

	private final EditCommentRequest editCommentRequest = new EditCommentRequest("id", "text");

	@Test
	public void createComment_Success() {
		UserEntity user = new UserEntity();
		PostEntity post = new PostEntity();
		CommentEntity commentEntity = new CommentEntity();
		CreateCommentResponse expectedResponse = new CreateCommentResponse("1");

		when(entityManager.getReference(PostEntity.class, createCommentRequest.postId())).thenReturn(post);
		when(commentMapper.mapToEntity(createCommentRequest, user, post)).thenReturn(commentEntity);
		when(commentMapper.mapCreateCommentResponse(commentEntity)).thenReturn(expectedResponse);

		CreateCommentResponse response = commentService.createComment(createCommentRequest, user);

		assertNotNull(response);
		assertEquals(expectedResponse, response);
	}

	@Test
	public void getComments_Success() {
		String postId = "1";
		GetCommentsResponse expectedResponse = new GetCommentsResponse(new ArrayList<>());
		when(commentMapper.mapGetCommentsResponse(commentRepository.findByPostId(postId))).thenReturn(expectedResponse);

		GetCommentsResponse response = commentService.getComments(postId);

		assertNotNull(response);
		assertEquals(expectedResponse, response);
	}

	@Test
	public void editComment_Success() {
		UserEntity user = new UserEntity();
		user.setId("1");
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setAuthor(user);
		EditCommentResponse expectedResponse = new EditCommentResponse("1");

		when(commentRepository.findById(editCommentRequest.id())).thenReturn(java.util.Optional.of(commentEntity));
		when(commentMapper.mapToEditedResponse(commentEntity)).thenReturn(expectedResponse);

		EditCommentResponse response = commentService.editComment(editCommentRequest, user);

		assertNotNull(response);
		assertEquals(expectedResponse, response);
	}

	@Test
	public void deleteComment_Success() {
		DeleteCommentRequest request = new DeleteCommentRequest("id");
		UserEntity user = new UserEntity();
		user.setId("1");
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setAuthor(user);

		when(commentRepository.findById(request.commentId())).thenReturn(Optional.of(commentEntity));

		Void response = commentService.deleteComment(request, user);

		assertNull(response);
	}

	@Test(expected = EntityNotFoundException.class)
	public void editComment_CommentNotFound() {
		UserEntity user = new UserEntity();

		when(commentRepository.findById(editCommentRequest.id())).thenReturn(Optional.empty());

		commentService.editComment(editCommentRequest, user);
	}

	@Test(expected = CommentCannotBeEditedException.class)
	public void editComment_CommentCannotBeEdited() {
		UserEntity user = new UserEntity();
		CommentEntity commentEntity = new CommentEntity();
		UserEntity author = new UserEntity();
		author.setId("2");
		commentEntity.setAuthor(author);

		when(commentRepository.findById(editCommentRequest.id())).thenReturn(Optional.of(commentEntity));

		commentService.editComment(editCommentRequest, user);
	}

	@Test(expected = EntityNotFoundException.class)
	public void deleteComment_CommentNotFound() {
		DeleteCommentRequest request = new DeleteCommentRequest("id");
		UserEntity user = new UserEntity();

		when(commentRepository.findById(request.commentId())).thenReturn(Optional.empty());

		commentService.deleteComment(request, user);
	}

	@Test(expected = CommentCannotBeDeletedException.class)
	public void deleteComment_NotAuthorizedToDelete() {
		DeleteCommentRequest request = new DeleteCommentRequest("id");
		UserEntity user = new UserEntity();
		PostEntity post = new PostEntity();
		post.setAuthor(user);
		UserEntity user2 = new UserEntity();
		user2.setId("2");
		user.setId("1");
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setPost(post);
		commentEntity.setAuthor(user);

		when(commentRepository.findById(request.commentId())).thenReturn(Optional.of(commentEntity));

		commentService.deleteComment(request, user2);
	}

}
