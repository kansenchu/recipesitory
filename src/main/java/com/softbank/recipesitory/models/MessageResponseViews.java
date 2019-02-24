package com.softbank.recipesitory.models;

public class MessageResponseViews {
	public static interface MessageOnly {}
	public static interface MessageWithRecipe extends MessageOnly, RecipeViews.ExcludeId {}
	public static interface MessageWithRequired extends MessageOnly {}
}
