package com.barracuda.contest2013;

public class OfferChallengeMessage extends PlayerMessage {

	public OfferChallengeMessage(int request_id) {
		super(request_id);
		response = new Response("offer_challenge");
	}

	@Override
	public String toString() {
		return "Offer Challenge\n";
	}
}
