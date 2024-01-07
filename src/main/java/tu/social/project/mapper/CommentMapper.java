package tu.social.project.mapper;

import java.util.List;
import org.mapstruct.*;
import tu.social.project.entity.*;
import tu.social.project.payload.data.CommentData;
import tu.social.project.payload.request.CreateCommentRequest;
import tu.social.project.payload.response.*;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "author", source = "user")
	@Mapping(target = "post", source = "post")
	CommentEntity mapToEntity(CreateCommentRequest request, UserEntity user, PostEntity post);


	CreateCommentResponse mapCreateCommentResponse(CommentEntity comment);

	@Mapping(target = "authorId", source = "author.id")
	@Mapping(target = "postId", source = "post.id")
	CommentData mapCommentData(CommentEntity comment);

	default GetCommentsResponse mapGetCommentsResponse(List<CommentEntity> comments) {
		return new GetCommentsResponse(comments.stream()
			.map(this::mapCommentData)
			.toList());
	}

	EditCommentResponse mapToEditedResponse(CommentEntity comment);
}
