package com.softbank.recipesitory.models;

/**
 * 普段メッセージレスポンスを返す用POJOクラス
 * @author pikachoo
 *
 */
public class ResponseViews {
	public static interface MessageOnly {}
	public static interface MessageWithRecipe extends MessageOnly, RecipeViews.ExcludeId {}
	public static interface MessageWithRequired extends MessageOnly {}
}
