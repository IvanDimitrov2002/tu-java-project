package tu.social.project.service;

import tu.social.project.entity.UserEntity;

public interface LikeService {
    void addLikeToPost(String postId, UserEntity user);
    Integer getNumberOfLikes(String postId);
    void removeLikeFromPost(String postId, UserEntity user);
}
