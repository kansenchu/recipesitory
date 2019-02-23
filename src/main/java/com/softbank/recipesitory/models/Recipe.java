package com.softbank.recipesitory.models;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * REST API でやりとりする時使うレシピクラス
 * ポイントとして、値段がStringになります。
 * @author pikachoo
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
	@JsonView(RecipeViews.IncludeId.class)
	private int id;
	
	@JsonView(RecipeViews.ExcludeId.class)
	private String title;			/** レシピの名前 */
	
	@JsonView(RecipeViews.ExcludeId.class)
	private String makingTime;		/** レシピの作り時間 */
	
	@JsonView(RecipeViews.ExcludeId.class)
	private String serves;			/** レシピに対応する人数 */
	
	@JsonView(RecipeViews.ExcludeId.class)
	private String ingredients;		/** 材料リスト。Listではなく、String扱いとしています。 */
	
	@JsonView(RecipeViews.ExcludeId.class)
	private String cost;			/** レシピの予測値段。intではなく, Stringです。 */
}
