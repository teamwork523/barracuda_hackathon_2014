/**
 * sample Java implementation for 2013 Barracuda Networks Programming Contest
 *
 */
package com.barracuda.contest2013;

import java.io.IOException;

public class ContestBot {
	private static final int RECONNECT_TIMEOUT = 15; // seconds

	private final String host;
	private final int port;
	private int game_id = -1;
	private Cards cardsState;

	public ContestBot(String host, int port) {
		this.host = host;
		this.port = port;
		cardsState = new Cards();
	}

	private void run() {
		while (true) {
			// just reconnect upon any failure
			try {
				JsonSocket sock = new JsonSocket(host, port);
				try {
					sock.connect();
				}
				catch (IOException e) {
					throw new Exception("Error establishing connection to server: " + e.toString());
				}

				while (true) {
					Message message = sock.getMessage();
		
					PlayerMessage response = handleMessage(message);
		
					if (response != null) {
						sock.sendMessage(response);
					}
				}
			}
			catch (Exception e) {
				System.err.println("Error: " + e.toString());
				System.err.println("Reconnecting in " + RECONNECT_TIMEOUT + "s");
				try {
					Thread.sleep(RECONNECT_TIMEOUT * 1000);
				}
				catch (InterruptedException ex) {}
			}
		}
	}

	// roBAst contest bot
	public PlayerMessage handleMessage(Message message) {
		System.out.println("----------------------- " + message.type + " --------------------------");
		if (message.type.equals("request") && game_id != ((MoveMessage)message).state.game_id) {
			System.out.println("New Cards");
			cardsState = new Cards();
		}
		cardsState.update(message);
		
		if (message.type.equals("request")) {
			MoveMessage m = (MoveMessage)message;
			//System.out.println("MoveMessage");
			//m.toString();
			//System.out.println();
			// new game
			if (game_id != m.state.game_id) {
				game_id = m.state.game_id;
				System.out.println("new game " + game_id);
			}
			
			// offer card and challenge
			if (m.request.equals("request_card")) {
				if (! m.state.can_challenge || Math.random() < 0.8) {
					int i = (int)(Math.random() * m.state.hand.length);
					System.out.println("Give out card: " +  m.state.hand[i]);
					return new PlayCardMessage(m.request_id, m.state.hand[i]);
				}
				else {
					System.out.println("Challenge");
					return new OfferChallengeMessage(m.request_id);
				}
			}
			// challenge offered, accept or reject
			else if (m.request.equals("challenge_offered")) {
				System.out.println("Accept Challenge");
				return new AcceptChallengeMessage(m.request_id);
				//return (Math.random() < 0.5)
				//		? new AcceptChallengeMessage(m.request_id)
				//		: new RejectChallengeMessage(m.request_id);
			}
		}
		else if (message.type.equals("result")) {
			ResultMessage r = (ResultMessage)message;
			//System.out.println("ResultMessage");
			//r.toString();
			//System.out.println();
			System.out.print("Result type: " + r.result.type);
			if (r.result.by != null) {
				System.out.println(" " + r.result.by);
			} else {
				System.out.println();
			}
		}
		else if (message.type.equals("error")) {
			ErrorMessage e = (ErrorMessage)message;
			System.err.println("Error: " + e.message);

			// need to register IP address on the contest server
			if (e.seen_host != null) {
				System.exit(1);
			}
		}
		return null;
	}

	public static void main(String[] args) {
	/*	if (args.length < 2) {
			System.err.println("Usage: java -jar ContestBot.jar <HOST> <PORT>");
			System.exit(1);
		}

		String host = args[0];
		Integer port = Integer.parseInt(args[1]);
	 */
		String host = "cuda.contest";
		Integer port = Integer.parseInt("19999");
		ContestBot cb = new ContestBot(host, port);
		cb.run();
	}
}
