package com.softbank.recipesitory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softbank.recipesitory.controller.RecipeController;
import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.models.RecipeDao;
import com.softbank.recipesitory.service.RecipeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeControllerTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Value("${json.config.oneRecipeJson}")
	File oneRecipeJson;
	
	@InjectMocks
	RecipeController rController;
	
	@Mock
	RecipeService mockRecipeService;
	
	ModelMapper modelMapper = new ModelMapper();
	ObjectMapper jsonMapper = new ObjectMapper();

	@Test
	public void getARecipe() throws Exception {
		//setup
//		RecipeDao data = jsonMapper.readValue(oneRecipeJson, RecipeDao.class);
		Recipe expected = jsonMapper.readValue(oneRecipeJson, Recipe.class);
		int recipeId = expected.getId();
		when(mockRecipeService.getRecipe(recipeId)).thenReturn(expected);
		
		//act
		Recipe actual = rController.getRecipe(recipeId);
		
		//verify
		verify(mockRecipeService).getRecipe(recipeId);
		assertEquals(expected, actual);
	}
	
	@Test
	public void getAllRecipes() throws Exception {
		//act
		rController.getRecipes();
		
		//verify
		verify(mockRecipeService).getRecipes();
	}
	
	@Test
	public void addRecipe() {
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
	public void editRecipe() {
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
	public void removeRecipe() {
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
