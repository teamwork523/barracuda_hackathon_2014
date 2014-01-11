package com.barracuda.contest2013;

public class Response extends Message {
	public Integer card;

	public Response(String type) {
		this.type = type;
	}

	public Response(String type, int card) {
		this.type = type;
		this.card = new Integer(card);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\tResponse:\n");
		sb.append("\t\ttype: " + type + "\n");
		return sb.toString();
	}

}
