package com.softbank.recipesitory.models;

/**
 * レシピのRESTやるとりするViewを指定するクラス。
 * 実際に管理するロジックは、Jacksonのライブラリにあります。
 * Recipeクラスの中のAnnotationによってViewの項目が決める。
 * 
 * @see <a href="https://www.techscore.com/blog/2016/12/18/java-jackson-serialize-deserialize/">
 https://www.techscore.com/blog/2016/12/18/java-jackson-serialize-deserialize/</a>
 * @author pikachoo
 *
 */
public class RecipeViews {
	public static class ExcludeId {}
	public static class IncludeId extends ExcludeId {}
}
