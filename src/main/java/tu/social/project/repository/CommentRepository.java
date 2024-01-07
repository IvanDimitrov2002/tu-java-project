package tu.social.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tu.social.project.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, String> {
	List<CommentEntity> findByPostId(String postId);
}
