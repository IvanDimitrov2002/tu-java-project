package tu.social.project.service;

import tu.social.project.entity.UserEntity;

import java.util.List;

public interface LikeService {
    void addLikeToPost(String postId, UserEntity user);
    Integer getNumberOfLikes(String postId);
}
