package tu.social.project.service.impl;

import org.springframework.stereotype.Service;

import tu.social.project.entity.PostEntity;
import tu.social.project.mapper.PostMapper;
import tu.social.project.payload.request.CreatePostRequest;
import tu.social.project.payload.response.CreatePostResponse;
import tu.social.project.payload.response.GetPostsResponse;
import tu.social.project.repository.PostRepository;
import tu.social.project.service.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final PostMapper postMapper;

  public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.postMapper = postMapper;
  }

  @Override
  public CreatePostResponse createPost(CreatePostRequest request) {
    PostEntity postEntity = postMapper.mapToEntity(request);
    postRepository.save(postEntity);
    return postMapper.mapToCreatePostResponse(postEntity);
  }

  @Override
  public List<GetPostsResponse> getPostsByAuthor(String authorId) {
    List<PostEntity> posts = postRepository.findAllByAuthorId(authorId);
    return postMapper.mapToGetPostsResponse(posts);
  }

  @Override
  public List<GetPostsResponse> getUserLikedPosts(String userId) {
    List<PostEntity> posts = postRepository.findAllLikedByUserId(userId);
    return postMapper.mapToGetPostsResponse(posts);
  }
}
