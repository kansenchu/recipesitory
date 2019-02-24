package com.softbank.recipesitory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softbank.recipesitory.controller.RecipeController;
import com.softbank.recipesitory.exception.RecipeNotFoundException;
import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.models.RecipeViews;
import com.softbank.recipesitory.models.SuccessResponse;
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
	@Value("${json.config.deletionSuccessResponse}")
	File deletionSuccessResponse;
	@Value("${json.config.updateSuccessResponse}")
	File updateSuccessResponse;
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
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rController).build();
	}
	
	@Test
	public void deleteOneRecipe() throws Exception {
		//setup
		Recipe expectedRecipe = jsonMapper.readValue(oneRecipeJson, Recipe.class);
		int recipeId = expectedRecipe.getId();
		when(mockRecipeService.removeRecipe(recipeId)).thenReturn(expectedRecipe);
		
		String requestUrl = String.format(urlTemplate, port, recipeId);
		String expected = new String(Files.readAllBytes(deletionSuccessResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).removeRecipe(recipeId);
	}
	
	@Test
	public void getNonexistentRecipe() throws Exception {
		//setup
		int recipeId = 999;
		when(mockRecipeService.getRecipe(recipeId)).thenThrow(new RecipeNotFoundException());
		
		String requestUrl = String.format(urlTemplate, port, recipeId);
		String expected = new String(Files.readAllBytes(notFoundResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
		
		//verify
				.andExpect(status().is(404))
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).getRecipe(recipeId);
	}
	
	@Test
	public void getAllRecipes() throws Exception {
		//setup
		List<Recipe> expectedRecipe = jsonMapper.readValue(allRecipesJson, new TypeReference<List<Recipe>>(){});
		when(mockRecipeService.getRecipes()).thenReturn(expectedRecipe);
		
		String requestUrl = String.format(urlTemplate, port, "");
		String expected = new String(Files.readAllBytes(allRecipesResponse.toPath()));

		//act
		mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).getRecipes();
	}
	
	@Test
	public void addRecipe() throws Exception {
		//setup
		String requestUrl = String.format(urlTemplate, port, "");
		Recipe recipe = jsonMapper.readValue(addRecipeJson, Recipe.class);
		String parameter = jsonMapper.writeValueAsString(recipe);
		String expected = new String(Files.readAllBytes(creationSuccessResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
											.contentType(MediaType.APPLICATION_JSON)
											.content(parameter))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).addRecipe(recipe);
	}
	
	@Test
	public void editRecipe() throws Exception {
		//setup
		String requestUrl = String.format(urlTemplate, port, 1);
		Recipe recipe = jsonMapper.readValue(addRecipeJson, Recipe.class);
		String parameter = jsonMapper.writeValueAsString(recipe);
		System.out.println(parameter);
		String expected = new String(Files.readAllBytes(updateSuccessResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.patch(requestUrl)
											.contentType(MediaType.APPLICATION_JSON)
											.content(parameter))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).addRecipe(recipe);
	}
	
	@Test
	public void updateNonexistentRecipe() throws Exception {
		//setup
		int recipeId = 999;
		String requestUrl = String.format(urlTemplate, port, recipeId);
		Recipe recipe = jsonMapper.readValue(addRecipeJson, Recipe.class);
		String parameter = jsonMapper.writeValueAsString(recipe);
		System.out.println(parameter);
		String expected = new String(Files.readAllBytes(updateSuccessResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.patch(requestUrl)
											.contentType(MediaType.APPLICATION_JSON)
											.content(parameter))
		
		//verify
				.andExpect(status().is(404))
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).editRecipe(eq(recipeId), any(Recipe.class));
	}
	
	@Test
	public void removeRecipe() throws Exception {
		//setup
		Recipe expectedRecipe = jsonMapper.readValue(oneRecipeJson, Recipe.class);;
		int recipeId = 1;
		when(mockRecipeService.removeRecipe(recipeId)).thenReturn(expectedRecipe); 
		
		String requestUrl = String.format(urlTemplate, port, recipeId);
		System.out.println(requestUrl);
		String expected = new String(Files.readAllBytes(deletionSuccessResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl))
		
		//verify
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).removeRecipe(recipeId);
	}
	
	@Test
	public void deleteNonexistentRecipe() throws Exception {
		//setup
		int recipeId = 999;
		when(mockRecipeService.removeRecipe(recipeId)).thenThrow(new RecipeNotFoundException());
		
		String requestUrl = String.format(urlTemplate, port, recipeId);
		String expected = new String(Files.readAllBytes(notFoundResponse.toPath()));
		
		//act
		mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl))
		
		//verify
				.andExpect(status().is(404))
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(content().json(expected));
		verify(mockRecipeService).removeRecipe(recipeId);
	}

}
