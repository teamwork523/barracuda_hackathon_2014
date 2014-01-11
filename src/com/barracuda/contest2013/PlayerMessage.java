package com.barracuda.contest2013;

public abstract class PlayerMessage extends Message {
	public int request_id;
	public Response response;

	public PlayerMessage(int request_id) {
		this.request_id = request_id;
		type = "move";
	}

	@Override
	public abstract String toString();
}
