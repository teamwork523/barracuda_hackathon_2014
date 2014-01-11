package com.barracuda.contest2013;

public class ErrorMessage extends Message {
	String message;
	String seen_host;

	@Override
	public String toString() {
		return "Error: " + message + "\n";
	}
}
