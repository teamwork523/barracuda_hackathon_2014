package com.barracuda.contest2013;

public class Cards {
	public int[] cardRemain; // 0-12 -> 1-13
	public int allCardNum; 
	public int hiddenNum;
	public boolean handDone;
	
	public int[] oppoHistory;
	public int[] myHistory;
	public int[] myCards;
	
	public Cards() {
		cardRemain = new int[13];
		for (int i = 0; i < 13; i++)
			cardRemain[i] = 8;
		hiddenNum = 0;
		allCardNum = 104;
		
		oppoHistory = new int[5];
		myHistory  = new int[5];
		myCards = new int[5];
		
		for (int i = 0; i < 5; i++) {
			oppoHistory[i] = 0;
			myHistory[i] = 0;
			myCards[i] = 0;
		}
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
