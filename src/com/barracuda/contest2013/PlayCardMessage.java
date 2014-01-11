package com.barracuda.contest2013;

public class PlayCardMessage extends PlayerMessage {
	public PlayCardMessage(int request_id, int card) {
		super(request_id);
		response = new Response("play_card", card);
	}

	@Override
	public String toString() {
		return "Play Card " + response.card + "\n";
	}
}
