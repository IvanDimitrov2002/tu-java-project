package tu.social.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tu.social.project.entity.CategoryEntity;
import tu.social.project.payload.request.CreatePostRequest;
import tu.social.project.payload.response.CreatePostResponse;
import tu.social.project.payload.response.GetPostsResponse;
import tu.social.project.service.PostService;

@RequestMapping("/posts")
@RestController
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @Operation(summary = "Create a post with a title, content and author")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created post", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid title, content or authorId supplied", content = @Content),
  })
  @PostMapping
  public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(postService.createPost(request));
  }

  @Operation(summary = "Get a post by its author")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found a post", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)) }),
  })
  @GetMapping("/author/{authorId}")
  public ResponseEntity<List<GetPostsResponse>> getPostsByAuthor(@PathVariable("authorId") String authorId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.getPostsByAuthor(authorId));
  }
}
