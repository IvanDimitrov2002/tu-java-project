package tu.social.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tu.social.project.payload.request.CreateCategoryRequest;
import tu.social.project.payload.response.CreateCategoryResponse;
import tu.social.project.payload.response.GetCategoriesResponse;
import tu.social.project.service.CategoryService;

import java.util.List;

@RequestMapping("/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CreateCategoryResponse> createCategory(@RequestBody CreateCategoryRequest categoryToCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryToCreateRequest));
    }

    @GetMapping
    public ResponseEntity<List<GetCategoriesResponse>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }

}
