package com.barracuda.contest2013;

import java.util.ArrayList;

public class Cards {
	public int[] cardRemain; // 0-12 -> 1-13
	public int[] cardEstRemain;
	public int allCardNum; // sum of cardRemain, including hidden cards
	public int hiddenNum;
	public boolean myLead;
	public boolean challangeRequest;
	public boolean debugInfo = true;
	
	public ArrayList<Integer> oppoHistory;
	public ArrayList<Integer> myHistory;
	public ArrayList<Integer> myCards; // sorted, from smallest to biggest
	public ArrayList<Integer> myAvailCards;
	
	public Cards() {
		cardRemain = new int[13];
		cardEstRemain = new int[13];
		resetCards();
		
		oppoHistory = new ArrayList<Integer>();
		myHistory  = new ArrayList<Integer>();
		myCards = new ArrayList<Integer>();
		myAvailCards = new ArrayList<Integer>();
	}
	
	public void resetCards() {
		for (int i = 0; i < 13; i++) {
			cardRemain[i] = 8;
			cardEstRemain[i] = 8;
		}
		hiddenNum = 0;
		allCardNum = 99;
		challangeRequest = false;
	}
	
	public double getBiggerProb(int no) {
		int num = 0;
		for (int i = 0; i <= no-1; i++) {
			num += cardRemain[i];
		}
		return (num * 1.0) / (allCardNum * 1.0);
	}
	
	
	public int getRemain(int no) {
		return cardRemain[no-1];
	}
	
	// num of cards must be 5
	public void addCard(int[] cards){
		if (debugInfo)
			System.out.println("func addCard()");
		
		int tmp = 0;
		for (int i = 3; i >= 0; i--) {
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
			cardRemain[cards[i]-1]--;
		}
	}	
	
	public void setAvailCards(int[] cards) {
		myAvailCards.clear();
		for (int i = 0; i < cards.length; i++) {
			myAvailCards.add(new Integer(cards[i]));
		}
	}
	
	// every time a message is received, call this function to update
	public void update(Message message) {
		if (debugInfo)
			System.out.println("func update()");
		if (message.type.equals("request")) {
			MoveMessage m = (MoveMessage)message;
			if (m.request.equals("request_card")) {
				// beginning of the game, add cards in myCard
				setAvailCards(m.state.hand);
				if (m.state.hand.length == 5) {
					addCard(m.state.hand);
				}
				if (debugInfo)
					System.out.println("last card in request: " + m.state.card);
				if (!challangeRequest) {
					if (m.state.card > 0) {
						cardRemain[m.state.card-1]--;
						allCardNum--;
						oppoHistory.add(new Integer(m.state.card));
						myLead = false;
					} else {
						myLead = true;
					}
				} else {
					if (m.state.card > 0) {
						myLead = false;
					} else {
						myLead = true;
					}
				}
			}
			else if (m.request.equals("challenge_offered")) {
				//myLead = false;
			}
		}
		else if (message.type.equals("result")) {
			ResultMessage r = (ResultMessage)message;
			// hand_done
			if (r.result.type.equals("hand_done")) {
				hiddenNum += (5 - oppoHistory.size());
				if (allCardNum - hiddenNum <= 4) {
					// new shuffle cards
					resetCards();
				}
				allCardNum -= 5;
				oppoHistory.clear();
				myHistory.clear();
				myCards.clear();
				myAvailCards.clear();
				return;
			}
			if (r.result.type.equals("trick_tied")) {
				if (debugInfo) {
					System.out.println("Trick_tied");
					System.out.println(this.toString());
				}
				int cardValue;
				if (myLead) {
					cardValue = myHistory.get(myHistory.size()-1).intValue();
					cardRemain[cardValue-1]--;
					allCardNum--;
					oppoHistory.add(new Integer(cardValue));
				}
				else {
					cardValue = oppoHistory.get(oppoHistory.size()-1).intValue();
					myHistory.add(new Integer(cardValue));
				}
				
				return;
			}
			if (r.result.card != null) {
				int cardValue = r.result.card.intValue();
				if (debugInfo)
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
	
	public void updateMyHistory(int no) {
		
		if (myLead) {
			myHistory.add(new Integer(no));
		}
		for (int i = 0; i < myAvailCards.size(); i++) {
			if (myAvailCards.get(i).intValue() == no) {
				myAvailCards.remove(i);
				break;
			}
		}
	}
	
	public String cardArrayToString(ArrayList<Integer> array) {
		String s = new String("");
		for (int i = 0; i < array.size(); i++) {
			s += (array.get(i) + " ");
		}
		return s;
	}
	
	public String cardRemainToString() {
		String s = new String("\t");
		for (int i = 0; i < 13; i++) {
			s += ((i+1) + " ");
		}
		s += "\n\t";
		for (int i = 0; i < 13; i++) {
			s += (cardRemain[i] + " ");
			if (i >= 9)
				s += " ";
		}
		s += "\n";
		return s;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("************\nCards state:\n");
		sb.append("\tMyCards: " + cardArrayToString(myCards));
		sb.append(" MyHistory: " + cardArrayToString(myHistory));
		sb.append(" OppoHistory: " + cardArrayToString(oppoHistory) + "\n");
		sb.append("\tMy Available Cards: " + cardArrayToString(myAvailCards) + "\n");
		sb.append("\tAll: " + allCardNum + " Hidden: " + hiddenNum + " Lead: " + myLead + "\n");
		sb.append(cardRemainToString());
		sb.append("************\n");
		return sb.toString();
	}

}
