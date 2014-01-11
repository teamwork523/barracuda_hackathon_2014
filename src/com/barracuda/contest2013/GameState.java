package com.barracuda.contest2013;

import java.util.Arrays;

public class GameState {
	public int[] hand;
	public int card;
	public int hand_id;
	public int game_id;
	public int your_tricks;
	public int their_tricks;
	public boolean can_challenge;
	public boolean in_challenge;
	public int total_tricks;
	public int your_points;
	public int opponent_id;
	public int their_points;
	public int player_number;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\tGame State:\n");
		sb.append("\t\thand: " + Arrays.toString(hand) + "\n");
		if (card > 0) sb.append("\t\tcard: " + card + "\n");
		sb.append("\t\thand_id: " + hand_id + "\n");
		sb.append("\t\tgame_id: " + game_id + "\n");
		sb.append("\t\tyour_tricks: " + your_tricks + "\n");
		sb.append("\t\ttheir_tricks: " + their_tricks + "\n");
		sb.append("\t\tcan_challenge: " + can_challenge + "\n");
		sb.append("\t\tin_challenge: " + in_challenge + "\n");
		sb.append("\t\ttotal_tricks: " + total_tricks + "\n");
		sb.append("\t\tyour_points: " + your_points + "\n");
		sb.append("\t\topponent_id: " + opponent_id + "\n");
		sb.append("\t\ttheir_points: " + their_points + "\n");
		sb.append("\t\tplayer_number: " + player_number + "\n");
		return sb.toString();
	}
}
