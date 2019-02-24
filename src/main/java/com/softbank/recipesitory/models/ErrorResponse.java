package com.softbank.recipesitory.models;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * エラーのレスポンス用POJOクラス
 * @author pikachoo
 *
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
	@JsonView(ResponseViews.MessageOnly.class)
	Messages message;			/** リクエストに返信メッセージ */
	@JsonView(ResponseViews.MessageWithRequired.class)
	String required; 			/** 欠けてるパラメータのString */
	
	/**
	 * メッセージだけのエラーレスポンスを作成
	 * @param message 送りたいメッセージ
	 */
	public ErrorResponse(Messages message) {
		this.message = message;
	}
}
