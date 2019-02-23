package com.softbank.recipesitory;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softbank.recipesitory.exception.InvalidRecipeException;
import com.softbank.recipesitory.models.Messages;
import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.service.RecipeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeServiceTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Value("${json.config.oneRecipeJson}")
	File oneRecipeJson;

	@Value("${json.config.allRecipesJson}")
	File allRecipesJson;
	
	@Value("${json.config.addRecipeJson}")
	File additionalRecipeJson;
	
	@Inject
	RecipeService recipeService;
	
	ObjectMapper jsonMapper = new ObjectMapper();
	
	@Test
	public void getOneRecipe() throws JsonParseException, JsonMappingException, IOException, InvalidRecipeException {
		//setup
		Recipe expected = jsonMapper.readValue(oneRecipeJson, Recipe.class);
		int testRecipeId = expected.getId();
		
		//act
		Recipe actual = recipeService.getRecipe(testRecipeId);
		
		//verify
		assertEquals(expected, actual);
	}
	
	@Test
	public void getOneRecipe_nonexistent() throws InvalidRecipeException {
		//setup
		expectedEx.expect(InvalidRecipeException.class);
		expectedEx.expectMessage(Messages.NOT_FOUND.getMessage());
		int testRecipeId = -1;
		
		//act
		recipeService.getRecipe(testRecipeId);
		
		//verify -- done above
	}
	
	@Test
	public void getAllRecipes() throws JsonParseException, JsonMappingException, IOException {
		//setup
		List<Recipe> expected = jsonMapper.readValue(allRecipesJson, new TypeReference<List<Recipe>>(){});
		
		//act
		List<Recipe> actual = recipeService.getRecipes();
		
		//verify
		assertEquals(expected, actual);
	}

	@Test
	public void addRecipe() throws JsonParseException, JsonMappingException, IOException, InvalidRecipeException{
		//setup
		List<Recipe> expectedRecipeList = jsonMapper.readValue(allRecipesJson, new TypeReference<List<Recipe>>(){});
		Recipe newRecipe = jsonMapper.readValue(additionalRecipeJson, Recipe.class);
		
		Recipe expectedRecipe = jsonMapper.readValue(additionalRecipeJson, Recipe.class);
		expectedRecipeList.add(expectedRecipe);
		expectedRecipe.setId(expectedRecipeList.size());
		
		//act
		Recipe actual = recipeService.addRecipe(newRecipe);
		List<Recipe> actualRecipeList = recipeService.getRecipes();
		
		//verify
		assertEquals(expectedRecipe, actual);
		assertEquals(expectedRecipeList, actualRecipeList);
	}
	
	@Test
	public void editRecipe() throws JsonParseException, JsonMappingException, IOException, InvalidRecipeException {
		//setup
		List<Recipe> expectedRecipeList = jsonMapper.readValue(allRecipesJson, new TypeReference<List<Recipe>>(){});
		Recipe newRecipe = jsonMapper.readValue(additionalRecipeJson, Recipe.class);
		
		Recipe expectedRecipe = jsonMapper.readValue(additionalRecipeJson, Recipe.class);
		int targetId = expectedRecipeList.get(0).getId();
		expectedRecipeList.set(0, expectedRecipe);
		expectedRecipe.setId(targetId);
		
		//act
		Recipe actual = recipeService.editRecipe(targetId, newRecipe);
		List<Recipe> actualRecipeList = recipeService.getRecipes();
		
		//verify
		assertEquals(expectedRecipeList, actualRecipeList);
		assertEquals(expectedRecipe, actual);
	}
	
	@Test
	public void editRecipe_nonexistent() throws JsonParseException, JsonMappingException, IOException, InvalidRecipeException {
		//setup
		expectedEx.expect(InvalidRecipeException.class);
		expectedEx.expectMessage(Messages.NOT_FOUND.getMessage());

		Recipe newRecipe = jsonMapper.readValue(additionalRecipeJson, Recipe.class);
		int testRecipeId = -1;
		
		//act
		recipeService.editRecipe(testRecipeId, newRecipe);
		
		//verify -- done above
	}
	
	@Test
	public void removeRecipe() throws JsonParseException, JsonMappingException, IOException, InvalidRecipeException{
		//setup
		List<Recipe> expectedRecipeList = jsonMapper.readValue(allRecipesJson, new TypeReference<List<Recipe>>(){});
		Recipe expectedRecipe = expectedRecipeList.remove(0);
		
		//act
		Recipe actualRecipe = recipeService.removeRecipe(expectedRecipe.getId());
		List<Recipe> actualRecipeList = recipeService.getRecipes();
		
		//verify
		assertEquals(expectedRecipeList, actualRecipeList);
		assertEquals(expectedRecipe, actualRecipe);
	}
	
	@Test
	public void removeRecipe_nonexistent() throws InvalidRecipeException {
		//setup
		expectedEx.expect(InvalidRecipeException.class);
		expectedEx.expectMessage(Messages.NOT_FOUND.getMessage());
		int testRecipeId = -1;
		
		//act
		recipeService.removeRecipe(testRecipeId);
		
		//verify -- done above
	}

}
