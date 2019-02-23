package com.softbank.recipesitory.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.service.RecipeService;


@RestController
@RequestMapping("/recipes")
public class RecipeController {
	
	@Inject
	RecipeService recipeService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Recipe getRecipe(@PathVariable int id){
		return recipeService.getRecipe(id);
	} 
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<Recipe> getRecipes(){
		return recipeService.getRecipes();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Recipe addRecipe(@Valid @RequestBody Recipe newRecipe){
		return recipeService.addRecipe(newRecipe);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PATCH)
	public Recipe editRecipe(@PathVariable int id, @Valid @RequestBody Recipe Recipe){
		return recipeService.editRecipe(id, Recipe);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public Recipe removeRecipe(@PathVariable int id){
		return recipeService.removeRecipe(id);
	}
}
