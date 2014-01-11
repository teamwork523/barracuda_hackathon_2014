package com.barracuda.contest2013;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// you can't have a Java program without a factory
public class MessageFactory {
	private static Gson gson = new Gson();

	public static Message getServerMessage(String jsonMessage) {
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(jsonMessage).getAsJsonObject();
		String type = gson.fromJson(obj.get("type"), String.class);

		if (type.equals("greetings_program")) {
			return gson.fromJson(jsonMessage, GreetingMessage.class);
		}
		else if (type.equals("request")) {
			return gson.fromJson(jsonMessage, MoveMessage.class);
		}
		else if (type.equals("result")) {
			return gson.fromJson(jsonMessage, ResultMessage.class);
		}
		else if (type.equals("error")) {
			return gson.fromJson(jsonMessage, ErrorMessage.class);
		}
		else {
			ErrorMessage err = new ErrorMessage();
			err.message = "Unknown response type [" + type + "] from server";
			return err;
		}
	}

	public static String getPlayerMessage(PlayerMessage response) {
		return gson.toJson(response);
	}
}
