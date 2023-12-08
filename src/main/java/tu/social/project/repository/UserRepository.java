package tu.social.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tu.social.project.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
