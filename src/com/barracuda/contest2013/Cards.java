package com.barracuda.contest2013;

import java.util.ArrayList;

public class Cards {
	public int[] cardRemain; // 0-12 -> 1-13
	public int allCardNum; // sum of cardRemain, including hidden cards
	public int hiddenNum;
	public boolean myLead;
	
	public ArrayList<Integer> oppoHistory;
	public ArrayList<Integer> myHistory;
	public ArrayList<Integer> myCards; // sorted, from smallest to biggest
	
	public Cards() {
		cardRemain = new int[13];
		resetCards();
		
		oppoHistory = new ArrayList<Integer>();
		myHistory  = new ArrayList<Integer>();
		myCards = new ArrayList<Integer>();
	}
	
	public void resetCards() {
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
	
	// num of cards must be 5
	public void addCard(int[] cards){
		int tmp = 0;
		for (int i = 3; i >= 0; i++) {
			for (int j = 0; j <= i; j++) {
				if (cards[j] > cards[j+1]) {
					tmp = cards[j];
					cards[j] = cards[j+1];
					cards[j+1] = tmp;
				}
			}
		}
		
		for (int i = 0; i < 5; i++) {
			myCards.add(new Integer(cards[i]));
		}
	}	
	
	// every time a message is received, call this function to update
	public void update(Message message) {
		if (message.type.equals("request")) {
			MoveMessage m = (MoveMessage)message;
			if (m.request.equals("request_card")) {
				// beginning of the game, add cards in myCard
				if (m.state.hand.length == 5) {
					addCard(m.state.hand);
				}
				System.out.println("last card in request: " + m.state.card);
				if (m.state.card > 0) {
					cardRemain[m.state.card-1]--;
					allCardNum--;
					myLead = false;
				} else {
					myLead = true;
				}
			}			
		}
		else if (message.type.equals("result")) {
			ResultMessage r = (ResultMessage)message;
			// hand_done
			if (r.result.type.equals("hand_done")) {
				if (allCardNum - hiddenNum <= 4) {
					// new shuffle cards
					resetCards();
				}
				allCardNum -= 5;
				oppoHistory.clear();
				myHistory.clear();
				myCards.clear();
				return;
			}
			if (r.result.card != null) {
				int cardValue = r.result.card.intValue();
				System.out.println("last card in result: " + cardValue);
				if (myLead) {
					cardRemain[cardValue-1]--;
					allCardNum--;
					oppoHistory.add(new Integer(cardValue));
				} else {
					myHistory.add(new Integer(cardValue));
				}
			}
		}
	}
	
	public void printCardArray(ArrayList<Integer> array) {
		for (int i = 0; i < array.size(); i++) {
			System.out.print(array.get(i) + " ");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Cards state:\n");

		return sb.toString();
	}

}
