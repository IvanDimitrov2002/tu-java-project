package tu.social.project.service;

import tu.social.project.payload.request.CreateCategoryRequest;
import tu.social.project.payload.response.CreateCategoryResponse;

public interface CategoryService {
    CreateCategoryResponse createCategory(CreateCategoryRequest categoryToCreateRequest);
}
