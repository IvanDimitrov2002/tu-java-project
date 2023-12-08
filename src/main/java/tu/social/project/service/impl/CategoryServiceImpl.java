package tu.social.project.service.impl;

import org.springframework.stereotype.Service;
import tu.social.project.entity.CategoryEntity;
import tu.social.project.exception.CategoryAlreadyExistsException;
import tu.social.project.mapper.CategoryMapper;
import tu.social.project.payload.request.CreateCategoryRequest;
import tu.social.project.payload.response.CreateCategoryResponse;
import tu.social.project.repository.CategoryRepository;
import tu.social.project.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CreateCategoryResponse createCategory(CreateCategoryRequest categoryToCreateRequest) {
        if (categoryRepository.existsByName(categoryToCreateRequest.name())) {
            throw new CategoryAlreadyExistsException(categoryToCreateRequest.name());
        }

        CategoryEntity categoryEntity = categoryMapper.mapToEntity(categoryToCreateRequest);
        categoryRepository.save(categoryEntity);
        return categoryMapper.mapCreateCategoryResponse(categoryEntity);
    }
}
