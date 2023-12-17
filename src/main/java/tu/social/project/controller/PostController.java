package tu.social.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

  @PostMapping
  public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(postService.createPost(request));
  }

  @GetMapping("/author/{authorId}")
  public ResponseEntity<List<GetPostsResponse>> getPostsByAuthor(@PathVariable("authorId") String authorId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(postService.getPostsByAuthor(authorId));
  }
}
