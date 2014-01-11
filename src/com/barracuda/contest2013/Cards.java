package com.barracuda.contest2013;

public class Cards {
	private int[] cardRemain; // 0-12 -> 1-13
	private int allCardNum; 
	private int hiddenNum;
	private boolean handDone;	
	
	public Cards() {
		cardRemain = new int[13];
		for (int i = 0; i < 13; i++)
			cardRemain[i] = 8;
		hiddenNum = 0;
		allCardNum = 104;
	}
	
	public double getBiggerProb(int no) {
		int num = 0;
		for (int i = 0; i < no-1; i++) {
			num += cardRemain[i];
		}
		return (num * 1.0) / (allCardNum * 1.0);
	}
	
	
	public int getRemain(int no) {
		return cardRemain[no-1];
	}
	
	// every time a message is received, call this function to update
	public void update(Message message) {
		if (message.type.equals("request")) {
			MoveMessage m = (MoveMessage)message;
			if (m.request.equals("request_card")) {
				System.out.println("last card in request: " + m.state.card);
				if (m.state.card > 0) {
					cardRemain[m.state.card-1]--;
				}
			}			
		}
		else if (message.type.equals("result")) {
			ResultMessage r = (ResultMessage)message;
			if (r.result.card != null) {
				System.out.println("last card in result: " + r.result.card.intValue());
				cardRemain[r.result.card.intValue()-1]--;
			}
		}
	}

}
