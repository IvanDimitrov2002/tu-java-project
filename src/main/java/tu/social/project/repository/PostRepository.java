package tu.social.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import tu.social.project.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, String> {
  List<PostEntity> findAllByAuthorId(String authorId);

  @Query("SELECT p FROM PostEntity p JOIN p.likes l WHERE l.user.id = :userId")
  List<PostEntity> findAllLikedByUserId(@Param("userId") String userId);
}
