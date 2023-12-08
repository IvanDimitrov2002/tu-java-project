package tu.social.project.mapper;

import org.mapstruct.Mapper;
import tu.social.project.entity.CategoryEntity;
import tu.social.project.payload.request.CreateCategoryRequest;
import tu.social.project.payload.response.CreateCategoryResponse;
import tu.social.project.payload.response.GetCategoriesResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryEntity mapToEntity(CreateCategoryRequest categoryRequest);
    CreateCategoryResponse mapCreateCategoryResponse(CategoryEntity categoryEntity);

    List<GetCategoriesResponse> mapToGetCategoriesResponse(List<CategoryEntity> categories);
}
