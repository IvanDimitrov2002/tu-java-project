package tu.social.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tu.social.project.anotation.User;
import tu.social.project.entity.UserEntity;
import tu.social.project.payload.request.CreatePostRequest;
import tu.social.project.payload.response.CreatePostResponse;
import tu.social.project.payload.response.ErrorResponse;
import tu.social.project.payload.response.GetPostsResponse;
import tu.social.project.payload.response.UserResponse;
import tu.social.project.service.LikeService;
import tu.social.project.service.PostService;

@RequestMapping("/posts")
@RestController
@Tag(name = "Posts")
public class PostController {
  private final PostService postService;
  private final LikeService likeService;

  public PostController(PostService postService, LikeService likeService) {
    this.postService = postService;
    this.likeService = likeService;
  }

  @Operation(summary = "Create a post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created post", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CreatePostResponse.class)) }),
  })
  @PostMapping
  public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(postService.createPost(request));
  }

  @Operation(summary = "Get posts by author")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found posts", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetPostsResponse.class))) }),
  })
  @GetMapping("/author/{authorId}")
  public ResponseEntity<List<GetPostsResponse>> getPostsByAuthor(@PathVariable("authorId") String authorId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.getPostsByAuthor(authorId));
  }

  @Operation(summary = "Like a post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Post liked", content = {
          @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "404", description = "Post does not exist", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
      @ApiResponse(responseCode = "409", description = "Post already liked", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
  })
  @PostMapping("/{postId}/like")
  public ResponseEntity<String> addLike(@PathVariable String postId, @User UserEntity currentUser) {
    likeService.addLikeToPost(postId, currentUser);
    return ResponseEntity.ok("Post liked successfully!");
  }

  @Operation(summary = "Get post likes count")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Post likes count retrieved", content = {
          @Content(mediaType = "text/plain", schema = @Schema(implementation = Integer.class)) }),
      @ApiResponse(responseCode = "404", description = "Post does not exist", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
  })
  @GetMapping("/{postId}/likes")
  public ResponseEntity<Integer> getNumberOfLikes(@PathVariable String postId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(likeService.getNumberOfLikes(postId));
  }

  @Operation(summary = "Get users who liked the post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users found", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))) }),
      @ApiResponse(responseCode = "404", description = "Post does not exist", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
  })
  @GetMapping("/{postId}/likes/users")
  public ResponseEntity<List<UserResponse>> getUsersWhoLikedPost(@PathVariable String postId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(likeService.getUsersWhoLikedPost(postId));
  }

  @Operation(summary = "Remove like from a post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Post like removed", content = {
          @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
      @ApiResponse(responseCode = "404", description = "Like or post does not exist", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
  })
  @DeleteMapping("/{postId}/like")
  public ResponseEntity<String> removeLike(@PathVariable String postId, @User UserEntity currentUser) {
    likeService.removeLikeFromPost(postId, currentUser);
    return ResponseEntity.ok("Like removed successfully!");
  }

}
