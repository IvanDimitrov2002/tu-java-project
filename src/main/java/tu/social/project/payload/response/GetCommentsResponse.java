package tu.social.project.payload.response;

import java.util.List;
import tu.social.project.payload.data.CommentData;

public record GetCommentsResponse(List<CommentData> comments) {

}
