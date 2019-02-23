package com.softbank.recipesitory.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全レシピ返す用POJOクラス
 * @author pikachoo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllRecipeResponse {
	List<Recipe> recipes;
}
