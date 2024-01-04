package com.contactManager.utilities;

public class Message {
	private String msg;
	private boolean isError;
	
	public Message() {
		super();
	}

	public Message(String msg, boolean isError) {
		super();
		this.msg = msg;
		this.isError = isError;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	@Override
	public String toString() {
		return "Message [msg=" + msg + ", isError=" + isError + "]";
	}
	
}
