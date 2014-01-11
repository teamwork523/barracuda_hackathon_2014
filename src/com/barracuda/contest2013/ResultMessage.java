package com.barracuda.contest2013;

public class ResultMessage extends Message {
	public Result result;
	public int your_player_num;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Result Message:\n");
		sb.append(result.toString());
		sb.append("\tyour_player_num: " + your_player_num + "\n");
		return sb.toString();
	}
}
