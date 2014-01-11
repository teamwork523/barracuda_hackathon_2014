package com.barracuda.contest2013;

public class AcceptChallengeMessage extends PlayerMessage {

	public AcceptChallengeMessage(int request_id) {
		super(request_id);
		response = new Response("accept_challenge");
	}

	@Override
	public String toString() {
		return "Accept Challenge\n";
	}
}
