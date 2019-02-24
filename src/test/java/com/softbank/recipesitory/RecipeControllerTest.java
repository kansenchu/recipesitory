package com.softbank.recipesitory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softbank.recipesitory.controller.RecipeController;
import com.softbank.recipesitory.dao.RecipeDao;
import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.service.RecipeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
		"local.api.url.template=http://localhost:%d/recipes/%s"})
public class RecipeControllerTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Value("${json.config.oneRecipeJson}")
	File oneRecipeJson;
	@Value("${json.config.allRecipesJson}")
	File allRecipesJson;
	@Value("${json.config.addRecipeJson}")
	File addRecipeJson;
	
	@Value("${json.config.oneRecipeResponse}")
	File oneRecipeResponse;
	@Value("${json.config.allRecipesResponse}")
	File allRecipesResponse;
	@Value("${json.config.notFoundResponse}")
	File notFoundResponse;
	@Value("${json.config.creationSuccessResponse}")
	File creationSuccessResponse;
	@Value("${json.config.creationFailedResponse}")
	File creationFailedResponse;
	
	@Mock
	RecipeService mockRecipeService;
	
	@InjectMocks
	RecipeController rController;
	
	@Inject
	WebApplicationContext wac;
	
	@LocalServerPort
	int port;
	
	@Value("${local.api.url.template}")
	String urlTemplate;
	
	MockMvc mockMvc;
	
	ModelMapper modelMapper = new ModelMapper();
	ObjectMapper jsonMapper = new ObjectMapper();
	
	@Before
	public void setup() throws IOException {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void getARecipe() throws Exception {
		//setup
		Recipe expectedRecipe = jsonMapper.readValue(oneRecipeJson, Recipe.class);
		int recipeId = expectedRecipe.getId();
		//when(mockRecipeService.getRecipe(recipeId)).thenReturn(expectedRecipe);
		
		String requestUrl = String.format(urlTemplate, port, recipeId);
		String expected = new String(Files.readAllBytes(oneRecipeResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		//verify(mockRecipeService).getRecipe(recipeId);
	}
	
	@Test
	public void getNonexistentRecipe() throws Exception {
		//setup
		int recipeId = 999;
		
		String requestUrl = String.format(urlTemplate, port, recipeId);
		String expected = new String(Files.readAllBytes(notFoundResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		//verify(mockRecipeService).getRecipe(recipeId);
	}
	
	@Test
	public void getAllRecipes() throws Exception {
		//setup
		List<Recipe> expectedRecipe = jsonMapper.readValue(allRecipesJson, new TypeReference<List<Recipe>>(){});
		//when(mockRecipeService.getRecipes()).thenReturn(expectedRecipe);
		
		String requestUrl = String.format(urlTemplate, port, "");
		String expected = new String(Files.readAllBytes(allRecipesResponse.toPath()));

		//act
		mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
//		verify(mockRecipeService).getRecipes();
	}
	
	@Test
	public void addRecipe() throws Exception {
		//setup
		Recipe newRecipe = mock(Recipe.class);
		when(mockRecipeService.addRecipe(newRecipe)).thenReturn(newRecipe);
		
		//act
		Recipe actual = rController.addRecipe(newRecipe);
		
		//verify
		verify(mockRecipeService).addRecipe(newRecipe);
		assertEquals(newRecipe, actual);
	}
	
	@Test
	public void editRecipe() throws Exception {
		//setup
		Recipe expected = mock(Recipe.class);
		int id = 0;
		when(mockRecipeService.editRecipe(eq(id), any(Recipe.class))).thenReturn(expected);
		
		//act
		Recipe actual = rController.editRecipe(id, expected);
		
		//verify
		verify(mockRecipeService).editRecipe(id, expected);
		assertEquals(expected, actual);
	}
	
	@Test
	public void removeRecipe() throws Exception {
		//setup
		Recipe expected = mock(Recipe.class);
		int id = 0;
		when(mockRecipeService.removeRecipe(id)).thenReturn(expected);
		
		//act
		Recipe actual = rController.removeRecipe(id);
		
		//verify
		verify(mockRecipeService).removeRecipe(id);
		assertEquals(expected, actual);
	}

}
