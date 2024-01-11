package tu.social.project.service.impl;

import jakarta.persistence.*;
import org.springframework.stereotype.Service;
import tu.social.project.entity.*;
import tu.social.project.exception.*;
import tu.social.project.mapper.CommentMapper;
import tu.social.project.payload.request.*;
import tu.social.project.payload.response.*;
import tu.social.project.repository.CommentRepository;
import tu.social.project.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;
	@PersistenceContext
	private EntityManager entityManager;

	public CommentServiceImpl(
		CommentRepository commentRepository,
		EntityManager entityManager,
		CommentMapper commentMapper
	) {
		this.commentRepository = commentRepository;
		this.entityManager = entityManager;
		this.commentMapper = commentMapper;
	}

	@Override
	public CreateCommentResponse createComment(CreateCommentRequest request, UserEntity user) {
		PostEntity post = this.entityManager.getReference(PostEntity.class, request.postId());
		CommentEntity comment = this.commentMapper.mapToEntity(request, user, post);
		this.commentRepository.save(comment);
		return this.commentMapper.mapCreateCommentResponse(comment);
	}

	@Override
	public GetCommentsResponse getComments(String postId) {
		return this.commentMapper.mapGetCommentsResponse(this.commentRepository.findByPostId(postId));
	}

	@Override
	public EditCommentResponse editComment(EditCommentRequest request, UserEntity user) {
		CommentEntity comment = this.commentRepository.findById(request.id()).orElseThrow(EntityNotFoundException::new);
		if (!comment.getAuthor().getId().equals(user.getId())) {
			throw new CommentCannotBeEditedException();
		}
		comment.setContent(request.content());
		this.commentRepository.save(comment);
		return this.commentMapper.mapToEditedResponse(comment);
	}

	@Override
	public Void deleteComment(DeleteCommentRequest request, UserEntity user) {
		CommentEntity comment = this.commentRepository.findById(request.commentId()).orElseThrow(EntityNotFoundException::new);
		if (!(comment.getAuthor().getId().equals(user.getId()) || comment.getPost().getAuthor().getId().equals(user.getId()))) {
			throw new CommentCannotBeDeletedException();
		}
		this.commentRepository.delete(comment);
		return null;
	}

}
