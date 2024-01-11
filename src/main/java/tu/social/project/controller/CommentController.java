package tu.social.project.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tu.social.project.anotation.User;
import tu.social.project.entity.UserEntity;
import tu.social.project.payload.request.*;
import tu.social.project.payload.response.*;
import tu.social.project.service.CommentService;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comments")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@Operation(summary = "Create a comment")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Comment created", content = {
          @Content(mediaType = "application/json",schema = @Schema(implementation = CreateCommentResponse.class)) }),
  })
	@PostMapping
	public ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest request, @User UserEntity user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(request, user));
	}

	@Operation(summary = "Get comments by post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Comments found", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = GetCommentsResponse.class)) }),
  })
	@GetMapping("/{postId}")
	public ResponseEntity<GetCommentsResponse> getComments(@PathVariable("postId") String postId) {
		return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getComments(postId));
	}

	@Operation(summary = "Edit comment")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Comment edited", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = EditCommentResponse.class)) }),
			@ApiResponse(responseCode = "403", description = "Comment can be edited only by its author", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
  })
	@PutMapping
	public ResponseEntity<EditCommentResponse> editComment(@RequestBody EditCommentRequest request, @User UserEntity user) {
		return ResponseEntity.status(HttpStatus.OK).body(this.commentService.editComment(request, user));
	}

	@Operation(summary = "Delete comment")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Comment deleted", content = @Content),
			@ApiResponse(responseCode = "403", description = "Comment can be deleted only by its author or post's author", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
  })
	@DeleteMapping
	public ResponseEntity<Void> deleteComment(@RequestBody DeleteCommentRequest request, @User UserEntity user) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.commentService.deleteComment(request, user));
	}
}
