package tu.social.project.service;

import tu.social.project.entity.UserEntity;
import tu.social.project.payload.request.*;
import tu.social.project.payload.response.*;

public interface CommentService {

	CreateCommentResponse createComment(CreateCommentRequest request, UserEntity user);

	GetCommentsResponse getComments(GetCommentsRequest request);

	EditCommentResponse editComment(EditCommentRequest request, UserEntity user);

	Void deleteComment(DeleteCommentRequest request, UserEntity user);
}
