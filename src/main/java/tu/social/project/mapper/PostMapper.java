package tu.social.project.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tu.social.project.entity.PostEntity;
import tu.social.project.payload.request.CreatePostRequest;
import tu.social.project.payload.response.CreatePostResponse;
import tu.social.project.payload.response.GetPostsResponse;

@Mapper(componentModel = "spring")
public interface PostMapper {
  @Mapping(target = "author.id", source = "authorId")
  PostEntity mapToEntity(CreatePostRequest request);

  @Mapping(target = "authorId", source = "author.id")
  CreatePostResponse mapToCreatePostResponse(PostEntity postEntity);

  @Mapping(target = "authorId", source = "author.id")
  GetPostsResponse mapToGetPostsResponse(PostEntity postEntity);

  default List<GetPostsResponse> mapToGetPostsResponse(List<PostEntity> posts) {
    if (posts == null) {
      return List.of();
    }

    return posts.stream()
        .map(this::mapToGetPostsResponse)
        .collect(Collectors.toList());
  }
}
