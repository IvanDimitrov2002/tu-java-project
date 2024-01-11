package tu.social.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import tu.social.project.exception.CategoryAlreadyExistsException;
import tu.social.project.mapper.CategoryMapper;
import tu.social.project.payload.request.CreateCategoryRequest;
import tu.social.project.payload.response.*;
import tu.social.project.repository.CategoryRepository;
import tu.social.project.service.impl.CategoryServiceImpl;
import tu.social.project.entity.CategoryEntity;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTests {

	@Mock
	private CategoryRepository categoryRepository;
	@Mock
	private CategoryMapper categoryMapper;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Test
	public void createCategory_Success() {
		CreateCategoryRequest request = new CreateCategoryRequest(any());
		CategoryEntity categoryEntity = new CategoryEntity();
		CreateCategoryResponse expectedResponse = new CreateCategoryResponse("1", "name");

		when(categoryRepository.existsByName(request.name())).thenReturn(false);
		when(categoryMapper.mapToEntity(request)).thenReturn(categoryEntity);
		when(categoryMapper.mapCreateCategoryResponse(categoryEntity)).thenReturn(expectedResponse);

		CreateCategoryResponse response = categoryService.createCategory(request);

		assertNotNull(response);
		assertEquals(expectedResponse, response);
	}

	@Test
	public void getCategories_Success() {
		List<CategoryEntity> categories = List.of(new CategoryEntity());
		List<GetCategoriesResponse> expectedResponses = List.of(new GetCategoriesResponse(
			"1",
			"name"
		));

		when(categoryRepository.findAll()).thenReturn(categories);
		when(categoryMapper.mapToGetCategoriesResponse(categories)).thenReturn(expectedResponses);

		List<GetCategoriesResponse> responses = categoryService.getCategories();

		assertNotNull(responses);
		assertEquals(expectedResponses.size(), responses.size());
	}

	@Test(expected = CategoryAlreadyExistsException.class)
	public void createCategory_AlreadyExists() {
		CreateCategoryRequest request = new CreateCategoryRequest(any());

		when(categoryRepository.existsByName(request.name())).thenReturn(true);

		categoryService.createCategory(request);
	}

}
