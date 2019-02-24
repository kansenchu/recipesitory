package com.softbank.recipesitory.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Recipe {
	@JsonView(RecipeViews.IncludeId.class)
	private int id;
	
	@JsonView(RecipeViews.ExcludeId.class)
	@NotNull
	private String title;			/** レシピの名前 */
	
	@JsonView(RecipeViews.ExcludeId.class)
	@NotNull
	private String makingTime;		/** レシピの作り時間。実際JSONではmaking_timeになります*/
	
	@JsonView(RecipeViews.ExcludeId.class)
	@NotNull
	private String serves;			/** レシピに対応する人数 */
	
	@JsonView(RecipeViews.ExcludeId.class)
	@NotNull
	private String ingredients;		/** 材料リスト。Listではなく、String扱いとしています。 */
	
	@JsonView(RecipeViews.ExcludeId.class)
	@NotNull
	private String cost;			/** レシピの予測値段。intではなく, Stringです。 */
	
	
}
