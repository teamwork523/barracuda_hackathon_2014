package com.barracuda.contest2013;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class JsonSocket {
	private final String host;
	private final int port;
	private Socket sock;
	private DataInputStream reader;
	private DataOutputStream writer;

	public JsonSocket(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() throws IOException {
		sock = new Socket(host, port);
		// disable nagle's algorithm to prevent latency on small packets
		sock.setTcpNoDelay(true);
		reader = new DataInputStream(sock.getInputStream());
		writer = new DataOutputStream(sock.getOutputStream());
	}

	public Message getMessage() throws Exception {
		String jsonMessage = "";

		try {
			int payloadLen = reader.readInt();
			byte[] jsonArr = new byte[payloadLen];
			if (reader.read(jsonArr) < 1) {
				throw new Exception("server response was truncated");
			}
			jsonMessage = new String(jsonArr);
		}
		catch (Exception e) {
			System.err.println("Error reading response from server: " + e.toString());
			throw e;
		}

		Message message = MessageFactory.getServerMessage(jsonMessage);
		return message;
	}

	public void sendMessage(PlayerMessage response) throws IOException {
		String json = MessageFactory.getPlayerMessage(response);

		try {
			byte[] jsonArr = json.getBytes("US-ASCII");
			writer.writeInt(jsonArr.length);
			writer.write(jsonArr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw e;
		}
	}
}
