package com.softbank.recipesitory.controller;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.annotation.JsonView;
import com.softbank.recipesitory.exception.InvalidRecipeException;
import com.softbank.recipesitory.exception.RecipeNotFoundException;
import com.softbank.recipesitory.models.AllRecipeResponse;
import com.softbank.recipesitory.models.ErrorResponse;
import com.softbank.recipesitory.models.ResponseViews;
import com.softbank.recipesitory.models.SuccessResponse;
import com.softbank.recipesitory.models.Messages;
import com.softbank.recipesitory.models.Recipe;
import com.softbank.recipesitory.service.RecipeService;

/**
 * レシピリクエストをやりとりするコントローラ。
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
	 * @return 一個のレシピのレスポンス
	 */
	@JsonView(ResponseViews.MessageWithRecipe.class)
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public SuccessResponse getRecipe(@PathVariable int id) {
		return new SuccessResponse(Messages.GET_ONE, recipeService.getRecipe(id));
	} 
	
	/**
	 * 全レシピを取得する。
	 * @return 全部のレシピが含めているレスポンス
	 */
	@RequestMapping(method=RequestMethod.GET)
	public AllRecipeResponse getRecipes(){
		return new AllRecipeResponse(recipeService.getRecipes());
	}
	
	/**
	 * レシピを加えるメソッド
	 * @param newRecipe 新しいレシピの詳細
	 * @return 新しいレシピを含めてるレスポンス
	 */
	@RequestMapping(method=RequestMethod.POST)
	public SuccessResponse addRecipe(@RequestBody Recipe newRecipe) {
		ArrayList<String> missing = new ArrayList<String>();
		if (newRecipe.getTitle() == null) missing.add("title");
		if (newRecipe.getMakingTime() == null) missing.add("making_time");
		if (newRecipe.getIngredients() == null) missing.add("ingredients");
		if (newRecipe.getServes() == null) missing.add("serves");
		if (newRecipe.getCost() == null) missing.add("cost");
		if (!missing.isEmpty()) {
			throw new InvalidRecipeException(String.join(",", missing));
		}
				
		Recipe actualRecipe = recipeService.addRecipe(newRecipe);
		return new SuccessResponse(Messages.CREATION_SUCCESS, actualRecipe);
	}
	
	/**
	 * レシピを変更するメソッド。
	 * 指定していないフィルドは古いものを使うままにします。
	 * @param id 変えたいレシピのid
	 * @param ecipe 変えたいもの詳細
	 * @return 変更したレシピ詳細
	 */
	@JsonView(ResponseViews.MessageWithRecipe.class)
	@RequestMapping(value="/{id}", method=RequestMethod.PATCH)
	public SuccessResponse editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
		Recipe newRecipe = recipeService.editRecipe(id, recipe);
		return new SuccessResponse(Messages.UPDATE_SUCCESS, newRecipe);
	}

	/**
	 * レシピを削除するメソッド
	 * @param id 削除するレシピID
	 * @return 削除成功レスポンス
	 */
	@JsonView(ResponseViews.MessageOnly.class)
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public SuccessResponse removeRecipe(@PathVariable int id) {
		recipeService.removeRecipe(id);
		return new SuccessResponse(Messages.DELETION_SUCCESS);
	}
	
	/**
	 * レシピが見つかれない時対応するハンドラー
	 * @param ex もらったエクセプション
	 * @return 決定のエラーメッセージ
	 */
	@JsonView(ResponseViews.MessageOnly.class)
	@ExceptionHandler(RecipeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse recipeNotFoundHandler(RecipeNotFoundException ex) {
		return new ErrorResponse(Messages.NOT_FOUND);
	}
	
	@JsonView(ResponseViews.MessageWithRequired.class)
	@ExceptionHandler(InvalidRecipeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse invalidReceipeHandler(InvalidRecipeException ex) {
		System.out.println("this sthing");
		return new ErrorResponse(Messages.CREATION_FAILED, ex.getMessage());
	}
	
	@JsonView(ResponseViews.MessageWithRequired.class)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse invalidReceipeHandler(Exception ex) {
		System.out.println(ex.getCause().getMessage());
		return new ErrorResponse(Messages.CREATION_FAILED, ex.getMessage());
	}
	
}
