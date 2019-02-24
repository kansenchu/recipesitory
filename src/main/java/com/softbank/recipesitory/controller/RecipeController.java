package com.softbank.recipesitory.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.softbank.recipesitory.exception.InvalidRecipeException;
import com.softbank.recipesitory.models.MessageResponse;
import com.softbank.recipesitory.models.MessageResponseViews;
import com.softbank.recipesitory.models.Messages;
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
	
	@JsonView(MessageResponseViews.MessageWithRecipe.class)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public MessageResponse getRecipe(@PathVariable int id) throws InvalidRecipeException{
		ArrayList<Recipe> returnList = new ArrayList<>();
		try {
			returnList.add(recipeService.getRecipe(id));
			return new MessageResponse(Messages.GET_ONE, returnList, "");
		} catch (InvalidRecipeException e) {
			return new MessageResponse(Messages.NOT_FOUND, returnList, "");
		}
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
