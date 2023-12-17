package tu.social.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import tu.social.project.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, String> {
  List<PostEntity> findAllByAuthorId(String authorId);
}
