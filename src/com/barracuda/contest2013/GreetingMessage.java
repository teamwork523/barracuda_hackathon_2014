package com.barracuda.contest2013;

public class GreetingMessage extends Message {
	public int team_id;
	public String sark;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Greeting Message:\n");
		sb.append("\tteam_id: " + team_id + "\n");
		sb.append("\tsark: " + sark + "\n");
		return sb.toString();
	}

}
