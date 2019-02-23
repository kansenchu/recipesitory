package com.softbank.recipesitory.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * DBとやりとりする時使うレシピクラス
 * ポイントとして、cost (値段)がintの基準でしています。
 * @author pikachoo
 *
 */

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDao {
	@Id
	private int id;
	@NotNull
	private String title;			/** レシピの名前 */
	@NotNull
	@Column(name="making_time")
	private String makingTime;		/** レシピの作り時間 */
	@NotNull
	private String serves;			/** レシピに対応する人数 */
	@NotNull
	private String ingredients;		/** 材料リスト。Listではなく、String扱いとしています。 */
	@NotNull
	private int cost;			/** レシピの予測値段。 */
}