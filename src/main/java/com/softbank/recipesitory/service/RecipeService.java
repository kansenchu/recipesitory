package com.softbank.recipesitory.service;

import java.util.List;

import javax.validation.Valid;

import com.softbank.recipesitory.models.Recipe;

public class RecipeService {
	/**
	 * Retrieve a Recipe with the given id.
	 * @param id reference to desired Recipe
	 * @return the relevant Recipe
	 */
	public Recipe getRecipe(int id) {
		return null;
	}
	
	/**
	 * Retrieve all Recipes.
	 * @return a List of all Recipes
	 */
	public List<Recipe> getRecipes() {
		return null;
	}
	
	/**
	 * Add the given Recipe into the store.
	 * @param Recipe to be added
	 * @return the Recipe that was added; this should be updated with the actual id in the store.
	 */
	public Recipe addRecipe(@Valid Recipe Recipe) {
		return null;
	}
	
	/**
	 * Edit the current existing Recipe in the store.
	 * @param id of the Recipe to be updated
	 * @param Recipe the new details of the Recipe
	 * @return the currently inserted record of id
	 */
	public Recipe editRecipe(int id, Recipe Recipe) {
		return null;
	}
	
	/**
	 * Removes the given Recipe.
	 * @param id of the Recipe to be deleted.
	 * @return the deleted Recipe
	 */
	public Recipe removeRecipe(int id) {
		return null;
	}
	
	
}
