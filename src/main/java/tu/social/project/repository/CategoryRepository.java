package tu.social.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tu.social.project.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    boolean existsByName(String name);
}
