package com.softbank.recipesitory.service;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.softbank.recipesitory.dao.RecipeDao;
import com.softbank.recipesitory.exception.InvalidRecipeException;
import com.softbank.recipesitory.exception.RecipeNotFoundException;
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
	
	/**
	 * 指定したIDのレシピを返す
	 * @param id 欲しいレシピ
	 * @return 指定したレシピ
	 * @throws RecipeNotFoundException レシピ見つかれない時
	 */
	public Recipe getRecipe(int id) throws RecipeNotFoundException {
		return mapToRecipe(repository.findById(id).orElseThrow(RecipeNotFoundException::new));
	}
	
	/**
	 * 全レシピ取得
	 * @return 全レシピのリスト
	 */
	public List<Recipe> getRecipes() {
		return repository.findAll(Sort.by("id").ascending())
				.parallelStream()
				.peek(System.out::println)
				.map(this::mapToRecipe)
				.collect(Collectors.toList());
	}
	
	/**
	 * レシピをDBに加える
	 * @param 加えるレシピの情報
	 * @return 実際にDBに存在してる新しいレシピ
	 * @throws InvalidRecipeException レシピがあっていない時
	 */
	public Recipe addRecipe(@Valid Recipe recipe) {
		try {
			RecipeDao toSave = mapToRecipeDao(recipe);
			System.out.println(toSave);
			RecipeDao recipeDao = repository.save(mapToRecipeDao(recipe));
			return mapToRecipe(repository.findById(recipeDao.getId())
					.orElseThrow(RecipeNotFoundException::new));
		} catch (ConstraintViolationException ex) {
			throw new RecipeNotFoundException();
		}
	}
	
	/**
	 * 現在存在してるレシピを更新
	 * @param id 変えたいレシピ
	 * @param recipe 新しい詳細
	 * @return 更新されたレシピ
	 * @throws InvalidRecipeException レシピが見つからない時
	 */
	public Recipe editRecipe(int id, Recipe recipe) throws RecipeNotFoundException {
		return repository.findById(id).map((Function<RecipeDao, Recipe>) oldRecipe -> {
			if (recipe.getTitle() != null) oldRecipe.setTitle(recipe.getTitle());
			if (recipe.getMakingTime() != null) oldRecipe.setMakingTime(recipe.getMakingTime());
			if (recipe.getServes() != null) oldRecipe.setServes(recipe.getServes());
			if (recipe.getIngredients() != null) oldRecipe.setIngredients(recipe.getIngredients());
			if (recipe.getCost() != null) oldRecipe.setCost(Integer.parseInt(recipe.getCost()));
			return mapToRecipe(repository.save(oldRecipe));
		}).orElseThrow(RecipeNotFoundException::new);
	}
	
	/**
	 * レシピを削除する
	 * @param id 削除したいレシピID
	 * @return 削除したレシピ
	 * @throws InvalidRecipeException レシピが見つからない時
	 */
	public Recipe removeRecipe(int id) {
		return repository.findById(id)
				.map((Function<RecipeDao, Recipe>) recipe -> {
					repository.delete(recipe);
					return mapToRecipe(recipe);
				}).orElseThrow(RecipeNotFoundException::new);
	}
	
	/**
	 * DAOからREST APIを投げるオブジェクトに変換
	 * @param recipeDao 変換したいDAO
	 * @return 平等のAPIモデルオブジェクト
	 */
	private Recipe mapToRecipe(RecipeDao RecipeDao) {
		return mapper.map(RecipeDao, Recipe.class);
	}
	
	/**
	 * REST APIを投げるオブジェクに変換
	 * @param recipe 変換したいレシピ
	 * @return 平等のDAOオブジェクト
	 */
	private RecipeDao mapToRecipeDao(Recipe Recipe) {
		return mapper.map(Recipe, RecipeDao.class);
	}
}
