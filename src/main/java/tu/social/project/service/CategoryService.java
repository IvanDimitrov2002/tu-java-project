package tu.social.project.service;

import tu.social.project.payload.request.CreateCategoryRequest;
import tu.social.project.payload.response.CreateCategoryResponse;
import tu.social.project.payload.response.GetCategoriesResponse;

import java.util.List;

public interface CategoryService {
    CreateCategoryResponse createCategory(CreateCategoryRequest categoryToCreateRequest);

    List<GetCategoriesResponse> getCategories();
}
