package com.antbean.springbootdemowebsocket;

// 服务端发出的消息
public class SMessage {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SMessage() {
		super();
	}

	public SMessage(String message) {
		super();
		this.message = message;
	}
}
