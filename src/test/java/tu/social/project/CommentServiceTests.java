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
	// write tests here in similar way than the UserServiceTests.java

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private CommentMapper commentMapper;

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private CommentServiceImpl commentService;

	@Test
	public void createComment_Success() {
		CreateCommentRequest request = new CreateCommentRequest(any(), any());
		UserEntity user = new UserEntity();
		PostEntity post = new PostEntity();
		CommentEntity commentEntity = new CommentEntity();
		CreateCommentResponse expectedResponse = new CreateCommentResponse("1");

		when(entityManager.getReference(PostEntity.class, request.postId())).thenReturn(post);
		when(commentMapper.mapToEntity(request, user, post)).thenReturn(commentEntity);
		when(commentMapper.mapCreateCommentResponse(commentEntity)).thenReturn(expectedResponse);

		CreateCommentResponse response = commentService.createComment(request, user);

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
		EditCommentRequest request = new EditCommentRequest(any(), any());
		UserEntity user = new UserEntity();
		CommentEntity commentEntity = new CommentEntity();
		EditCommentResponse expectedResponse = new EditCommentResponse("1");

		when(commentRepository.findById(request.id())).thenReturn(java.util.Optional.of(commentEntity));
		when(commentMapper.mapToEditedResponse(commentEntity)).thenReturn(expectedResponse);

		EditCommentResponse response = commentService.editComment(request, user);

		assertNotNull(response);
		assertEquals(expectedResponse, response);
	}

	@Test
	public void deleteComment_Success() {
		DeleteCommentRequest request = new DeleteCommentRequest(any());
		UserEntity user = new UserEntity();
		CommentEntity commentEntity = new CommentEntity();

		when(commentRepository.findById(request.commentId())).thenReturn(Optional.of(commentEntity));

		Void response = commentService.deleteComment(request, user);

		assertNull(response);
	}

	@Test(expected = EntityNotFoundException.class)
	public void editComment_CommentNotFound() {
		EditCommentRequest request = new EditCommentRequest(any(), any());
		UserEntity user = new UserEntity();

		when(commentRepository.findById(request.id())).thenReturn(Optional.empty());

		commentService.editComment(request, user);
	}

	@Test(expected = CommentCannotBeEditedException.class)
	public void editComment_CommentCannotBeEdited() {
		EditCommentRequest request = new EditCommentRequest(any(), any());
		UserEntity user = new UserEntity();
		CommentEntity commentEntity = new CommentEntity();
		UserEntity author = new UserEntity();
		author.setId("2");
		commentEntity.setAuthor(author);

		when(commentRepository.findById(request.id())).thenReturn(Optional.of(commentEntity));

		commentService.editComment(request, user);
	}

	@Test(expected = EntityNotFoundException.class)
	public void deleteComment_CommentNotFound() {
		DeleteCommentRequest request = new DeleteCommentRequest(any());
		UserEntity user = new UserEntity();

		when(commentRepository.findById(request.commentId())).thenReturn(Optional.empty());

		commentService.deleteComment(request, user);
	}

	@Test(expected = CommentCannotBeDeletedException.class)
	public void deleteComment_NotAuthorizedToDelete() {
		DeleteCommentRequest request = new DeleteCommentRequest(any());
		UserEntity user = new UserEntity();
		UserEntity user2 = new UserEntity();
		user2.setId("2");
		CommentEntity commentEntity = new CommentEntity();

		when(commentRepository.findById(request.commentId())).thenReturn(Optional.of(commentEntity));
		when(commentEntity.getAuthor()).thenReturn(user2);

		commentService.deleteComment(request, user);
	}

}
