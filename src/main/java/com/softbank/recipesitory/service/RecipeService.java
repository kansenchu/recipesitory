package com.softbank.recipesitory.service;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.softbank.recipesitory.dao.RecipeDao;
import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.repository.RecipeRepo;

/**
 * JDBC ドライバが裏で働いてるレシピサービス。
 * ドライバはRecipeRepoで管理する.
 * @author pikachoo
 * @see {@link RecipeRepo}
 */
@Service
public class RecipeService {
	
	@Inject
	RecipeRepo repository;
	
	ModelMapper mapper = new ModelMapper();
	
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
	
	private Recipe mapToRecipe(RecipeDao RecipeDao) {
		return mapper.map(RecipeDao, Recipe.class);
	}
	
	private RecipeDao mapToRecipeDao(Recipe Recipe) {
		return mapper.map(Recipe, RecipeDao.class);
	}
}
