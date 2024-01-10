package tu.social.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tu.social.project.entity.LikeEntity;
import tu.social.project.entity.PostEntity;
import tu.social.project.entity.UserEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, String> {
    boolean existsByUserAndPost(UserEntity user, PostEntity post);
}
