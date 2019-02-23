package com.softbank.recipesitory.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softbank.recipesitory.dao.RecipeDao;
import com.softbank.recipesitory.exception.InvalidRecipeException;
import com.softbank.recipesitory.models.Messages;
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
	
	Supplier<InvalidRecipeException> notFound = () -> new InvalidRecipeException(Messages.NOT_FOUND);
	
	/**
	 * 指定したIDのレシピを返す
	 * @param id reference to desired Recipe
	 * @return the relevant Recipe
	 * @throws InvalidRecipeException 
	 */
	public Recipe getRecipe(int id) throws InvalidRecipeException {
		return mapToRecipe(repository.findById(id).orElseThrow(notFound));
	}
	
	/**
	 * Retrieve all Recipes.
	 * @return a List of all Recipes
	 */
	public List<Recipe> getRecipes() {
		return repository.findAll(Sort.by("id").ascending())
				.parallelStream()
				.peek(System.out::println)
				.map(this::mapToRecipe)
				.collect(Collectors.toList());
	}
	
	/**
	 * Add the given Recipe into the store.
	 * @param Recipe to be added
	 * @return the Recipe that was added; this should be updated with the actual id in the store.
	 * @throws InvalidRecipeException 
	 */
	public Recipe addRecipe(@Valid Recipe recipe) throws InvalidRecipeException {
		RecipeDao recipeDao = repository.save(mapToRecipeDao(recipe));
		return mapToRecipe(repository.findById(recipeDao.getId())
				.orElseThrow(notFound));
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
