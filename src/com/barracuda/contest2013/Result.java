package com.barracuda.contest2013;

import java.util.HashMap;


public class Result {
	public String type;
	public Integer by;
	public String reason;
	public Integer card;

	// all this to work around not being able to switch(String)
	public static final int TRICK_WON  = 0;
	public static final int TRICK_TIED = 1;
	public static final int HAND_DONE  = 2;
	public static final int GAME_WON   = 3;
	public static final int ACCEPTED   = 4;
	public static final int ERROR      = 5;
	private static HashMap<String,Integer> resType;
	static {
		resType = new HashMap<String, Integer>();
		resType.put("trick_won", TRICK_WON);
		resType.put("trick_tied", TRICK_TIED);
		resType.put("hand_done", HAND_DONE);
		resType.put("game_won", GAME_WON);
		resType.put("accepted", ACCEPTED);
		resType.put("error", ERROR);
	}

	public static int resultType(String type) {
		return resType.get(type);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\ttype: " + type + "\n");
		if (by != null)     sb.append("\tby: " + by + "\n");
		if (reason != null) sb.append("\treason: " + reason + "\n");
		if (card != null)   sb.append("\tcard: " + card + " (last card played)\n");
		return sb.toString();
	}

}