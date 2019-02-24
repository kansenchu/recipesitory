package com.softbank.recipesitory.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbank.recipesitory.exception.InvalidRecipeException;
import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.service.RecipeService;

/**
 * レシピリクエストをやりとりするコントローラ
 * @author pikachoo
 *
 */
@RestController
@RequestMapping("/recipes")
public class RecipeController {
	
	@Inject
	RecipeService recipeService;

	/**
	 * 一個のレシピを取得する。
	 * @param id 取得したいレシピID
	 * @return 
	 * @throws InvalidRecipeException 
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Recipe getRecipe(@PathVariable int id) throws InvalidRecipeException{
		return recipeService.getRecipe(id);
	} 
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Recipe> getRecipes(){
		return recipeService.getRecipes();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Recipe addRecipe(@Valid @RequestBody Recipe newRecipe) throws InvalidRecipeException{
		return recipeService.addRecipe(newRecipe);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PATCH)
	public Recipe editRecipe(@PathVariable int id, @Valid @RequestBody Recipe Recipe) throws InvalidRecipeException{
		return recipeService.editRecipe(id, Recipe);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public Recipe removeRecipe(@PathVariable int id) throws InvalidRecipeException{
		return recipeService.removeRecipe(id);
	}
}
