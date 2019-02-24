package com.softbank.recipesitory.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 普段レシピ返す用POJOクラス
 * @author pikachoo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse { 
	@JsonView(MessageResponseViews.MessageOnly.class)
	Messages message;				/** リクエストに返信メッセージ */
	@JsonView(MessageResponseViews.MessageWithRecipe.class)
	List<Recipe> recipe;		/** レシピがあれば、返す */
	@JsonView(MessageResponseViews.MessageWithRequired.class)
	String required;
}
