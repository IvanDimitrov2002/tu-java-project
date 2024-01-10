package tu.social.project.service.impl;

import org.springframework.stereotype.Service;
import tu.social.project.entity.LikeEntity;
import tu.social.project.entity.PostEntity;
import tu.social.project.entity.UserEntity;
import tu.social.project.exception.AlreadyLikedPostException;
import tu.social.project.exception.LikeNotFoundException;
import tu.social.project.exception.PostNotFoundException;
import tu.social.project.repository.LikeRepository;
import tu.social.project.repository.PostRepository;
import tu.social.project.service.LikeService;


@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void addLikeToPost(String postId, UserEntity user) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new AlreadyLikedPostException();
        }

        LikeEntity like = new LikeEntity();
        like.setPost(post);
        like.setUser(user);

        like = likeRepository.save(like);

        post.getLikes().add(like);
        postRepository.save(post);
    }

    @Override
    public Integer getNumberOfLikes(String postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return post.getLikes().size();
    }

    @Override
    public void removeLikeFromPost(String postId, UserEntity user) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        LikeEntity like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new LikeNotFoundException(user.getId(), postId));

        post.getLikes().remove(like);
        likeRepository.delete(like);
    }

}
