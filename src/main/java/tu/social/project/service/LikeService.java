package tu.social.project.service;

import tu.social.project.entity.UserEntity;
import tu.social.project.payload.response.UserResponse;

import java.util.List;

public interface LikeService {
    void addLikeToPost(String postId, UserEntity user);
    Integer getNumberOfLikes(String postId);
    List<UserResponse> getUsersWhoLikedPost(String postId);
    void removeLikeFromPost(String postId, UserEntity user);
}
