package com.softbank.recipesitory.exception;

import com.softbank.recipesitory.models.Messages;

/**
 * レシピサービスがinvalidレシピを取得しようとしてるexception
 * @author pikachoo
 *
 */
public class InvalidRecipeException extends Exception {

	/**
	 * 基本なException constructor
	 */
	public InvalidRecipeException() {
		super();
	}
	
	/**
	 * 指定したメッセージを入れます。
	 * @param msg エラーメッセージ
	 */
	public InvalidRecipeException(Messages msg) {
		super(msg.getMessage());
	}
	
}
