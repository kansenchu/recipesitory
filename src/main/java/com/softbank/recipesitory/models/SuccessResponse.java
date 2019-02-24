package com.softbank.recipesitory.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * リクゥエスト成功して、一個のレシピか、メッセージだけ返す用POJOクラス
 * @author pikachoo
 *
 */
@Data
@AllArgsConstructor
public class SuccessResponse { 
	@JsonView(ResponseViews.MessageOnly.class)
	Messages message;				/** リクエストに返信メッセージ */
	@JsonView(ResponseViews.MessageWithRecipe.class)
	List<Recipe> recipe;		/** レシピがあれば、返す */
	
	/**
	 * メッセージだけのエラーレスポンスを作成
	 * @param message 送りたいメッセージ
	 */
	public SuccessResponse(Messages message) {
		this.message = message;
	}
	
	/**
	 * 一個のレシピを受けて直接リストに入れるコンスラクタ
	 * @param message 送りたいメッセージ
	 * @param recipe 入れたいレシピ
	 */
	public SuccessResponse(Messages message, Recipe recipe) {
		this.message = message;
		this.recipe = new ArrayList<>();
		this.recipe.add(recipe);
	}
}
