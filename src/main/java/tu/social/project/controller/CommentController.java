package tu.social.project.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tu.social.project.anotation.User;
import tu.social.project.entity.UserEntity;
import tu.social.project.payload.request.*;
import tu.social.project.payload.response.*;
import tu.social.project.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	public ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest request, @User UserEntity user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(request, user));
	}

	@GetMapping
	public ResponseEntity<GetCommentsResponse> getComments(@RequestBody GetCommentsRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getComments(request));
	}

	@PutMapping
	public ResponseEntity<EditCommentResponse> editComment(@RequestBody EditCommentRequest request, @User UserEntity user) {
		return ResponseEntity.status(HttpStatus.OK).body(this.commentService.editComment(request, user));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteComment(@RequestBody DeleteCommentRequest request, @User UserEntity user) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.commentService.deleteComment(request, user));
	}
}
