package com.barracuda.contest2013;

public class MoveMessage extends Message {
	public String request;
	public GameState state;
	int request_id;
	float remaining;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Move Message:\n");
		sb.append("\trequest: " + request + "\n");
		sb.append("\trequest_id: " + request_id + "\n");
		sb.append("\tremaining: " + remaining + "\n");
		sb.append(state);
		return sb.toString();
	}
}
