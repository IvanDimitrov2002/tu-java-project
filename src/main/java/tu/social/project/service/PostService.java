package tu.social.project.service;

import java.util.List;

import tu.social.project.payload.request.CreatePostRequest;
import tu.social.project.payload.response.CreatePostResponse;
import tu.social.project.payload.response.GetPostsResponse;

public interface PostService {
  CreatePostResponse createPost(CreatePostRequest request);

  List<GetPostsResponse> getPostsByAuthor(String authorId);
}
