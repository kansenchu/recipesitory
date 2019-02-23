package com.softbank.recipesitory;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RecipeControllerTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Value("${json.config.oneRecipeJson}")
	File oneRecipeJson;
	
	@InjectMocks
	RecipeController bController;
	
	@Mock
	RecipeService mockRecipeService;
	
	ObjectMapper jsonMapper = new ObjectMapper();

	@Test
	public void getARecipe() throws Exception {
		//setup
		Recipe expected = jsonMapper.readValue(oneRecipeJson, Recipe.class);
		long RecipeId = expected.getId();
		when(mockRecipeService.getRecipe(RecipeId)).thenReturn(expected);
		
		//act
		Recipe actual = bController.getRecipe(RecipeId);
		
		//verify
		verify(mockRecipeService).getRecipe(RecipeId);
		assertEquals(expected, actual);
	}
	
	@Test
	public void getAllRecipes() throws Exception {
		//act
		bController.getRecipes();
		
		//verify
		verify(mockRecipeService).getRecipes();
	}
	
	@Test
	public void addRecipe() {
		//setup
		Recipe newRecipe = mock(Recipe.class);
		when(mockRecipeService.addRecipe(newRecipe)).thenReturn(newRecipe);
		
		//act
		Recipe actual = bController.addRecipe(newRecipe);
		
		//verify
		verify(mockRecipeService).addRecipe(newRecipe);
		assertEquals(newRecipe, actual);
	}
	
	@Test
	public void editRecipe() {
		//setup
		Recipe expected = mock(Recipe.class);
		long id = 0L;
		when(mockRecipeService.editRecipe(eq(id), any(Recipe.class))).thenReturn(expected);
		
		//act
		Recipe actual = bController.editRecipe(id, expected);
		
		//verify
		verify(mockRecipeService).editRecipe(id, expected);
		assertEquals(expected, actual);
	}
	
	@Test
	public void removeRecipe() {
		//setup
		Recipe expected = mock(Recipe.class);
		long id = 0L;
		when(mockRecipeService.removeRecipe(id)).thenReturn(expected);
		
		//act
		Recipe actual = bController.removeRecipe(id);
		
		//verify
		verify(mockRecipeService).removeRecipe(id);
		assertEquals(expected, actual);
	}

}
