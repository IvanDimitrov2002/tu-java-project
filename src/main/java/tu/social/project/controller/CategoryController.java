package tu.social.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tu.social.project.payload.request.CreateCategoryRequest;
import tu.social.project.payload.response.CreateCategoryResponse;
import tu.social.project.payload.response.ErrorResponse;
import tu.social.project.payload.response.GetCategoriesResponse;
import tu.social.project.service.CategoryService;

import java.util.List;

@RequestMapping("/categories")
@RestController
@Tag(name = "Categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a category with a name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created category", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CreateCategoryResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Category already exists", content =  {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
    })
    @PostMapping
    public ResponseEntity<CreateCategoryResponse> createCategory(
            @RequestBody CreateCategoryRequest categoryToCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryToCreateRequest));
    }

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all categories", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetCategoriesResponse.class))) }),
    })
    @GetMapping
    public ResponseEntity<List<GetCategoriesResponse>> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }

}
