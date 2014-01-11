package com.barracuda.contest2013;

public class RejectChallengeMessage extends PlayerMessage {

	public RejectChallengeMessage(int request_id) {
		super(request_id);
		response = new Response("reject_challenge");
	}

	@Override
	public String toString() {
		return "Reject Challenge\n";
	}
}
