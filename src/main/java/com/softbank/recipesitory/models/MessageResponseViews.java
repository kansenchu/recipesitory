package com.softbank.recipesitory.models;

import com.softbank.recipesitory.models.RecipeViews.ExcludeId;

public class MessageResponseViews {
	public static class MessageOnly {}
	public static class MessageWithRecipe extends MessageOnly {}
	public static class MessageWithRequired extends MessageOnly {}
}
