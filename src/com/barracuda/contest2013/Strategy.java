package com.barracuda.contest2013;

import java.util.Arrays;

public class Strategy {
  public static final int CARD_NOT_EXIST = -1;
  
  /******************************************************/
  /******************* Hand Strategy ********************/
  /******************************************************/
  public static int findTheSmallestCard(int[] hands) {
    int minCard = 14;
    for (int i = 0; i < hands.length; i++) {
      if (hands[i] < minCard) {
        minCard = hands[i];
      }
    }
    return minCard;
  }
  
  public static int findTheMedianCard(int[] hands) {
    Arrays.sort(hands);
    if (hands.length == 1) {
      return hands[0];
    }
    return hands[(int)(hands.length / 2)];
  }
  
  public static int findTheLargestCard(int[] hands) {
    int maxCard = 0;
    for (int i = 0; i < hands.length; i++) {
      if (hands[i] > maxCard) {
        maxCard = hands[i];
      }
    }
    return maxCard;
  }
  
  // Conservative passive action
  /**
   * If least greater than the opponent card exist, then return that one.
   * Otherwise return the smallest card.
   */
  public static int findTheLeastBestCard(int[] hands, int opponentCard) {
    int leastBestCard = CARD_NOT_EXIST;
    
    // find the least greater card
    for (int i = 0; i < hands.length; i++) {
      if (hands[i] > opponentCard) {
        if (leastBestCard == CARD_NOT_EXIST ||
            (leastBestCard != CARD_NOT_EXIST && leastBestCard > hands[i])) {
          leastBestCard = hands[i];
        }
      }
    }
    
    if (leastBestCard == CARD_NOT_EXIST) {
      return findTheSmallestCard(hands);
    } else {
      return leastBestCard;
    }
  }
  
  
  /******************************************************/
  /************** Challenge Strategy ********************/
  /******************************************************/
  // Majority count to decide whether to propose/reject a challenge
  public static boolean restMajorityGreaterThanThreshold(GameState state, 
                                                         Cards cards,
                                                         double threshold) {
    int[] hands = state.hand;
    int targetWinTricksCount = state.your_tricks - state.their_tricks;
    
    if (targetWinTricksCount >= 3 && (cards.allCardNum - cards.hiddenNum > 20)) {
      if (cards.myLead) {
        return false;
      } else {
        return true;
      }
    }
    
    //int targetWinTricksCount = 0;
    
    for (int i = 0; i < hands.length; i++) {
      if (cards.isHighestNCard(hands[i], 2) || cards.getBiggerProb(hands[i]) > threshold) {
        targetWinTricksCount++;
      } else if (cards.isLowestNCard(hands[i], 2) || cards.getBiggerProb(hands[i]) <= threshold) {
        targetWinTricksCount--;
      }
    }
    
    int requiredWin = 0;
    
    /*if (hands.length == 1) {
      requiredWin = 0;
    } else {
      requiredWin = hands.length / 2;
    }*/
    
    return (targetWinTricksCount > requiredWin) ? true : false;
  }
  
  public static PlayerMessage handleMessage(Message message, Cards cardsState) {
    MoveMessage m = (MoveMessage)message;
    
    // offer card and challenge
    if (m.request.equals("request_card")) {
      //if (! m.state.can_challenge || Math.random() < 0.8) {
      if (m.state.can_challenge && restMajorityGreaterThanThreshold(m.state, cardsState, 0.7)) {
          // offer challenge
          if (cardsState.debugInfo)
            System.out.println("Can C?" + m.state.can_challenge + " Challenge >>>>>>>>>>>>>>>>>>>>");
          return new OfferChallengeMessage(m.request_id);
      } else {
        // offer card
        // int i = (int)(Math.random() * m.state.hand.length);
        // select the smallest to pick the card
        
        int selectedCard;
        if (cardsState.myLead) {
          selectedCard = findTheSmallestCard(m.state.hand);
        } else {
          selectedCard = findTheLeastBestCard(m.state.hand, m.state.card);
        }
        findTheSmallestCard(m.state.hand);
        if (cardsState.debugInfo) {
          System.out.print("Hands: ");
          for (int j = 0; j < m.state.hand.length; j++) {
            System.out.print(m.state.hand[j] + " ");
          }
          System.out.println();
          System.out.println("Give out card: " +  selectedCard);
        }
        cardsState.updateMyHistory(selectedCard);
        return new PlayCardMessage(m.request_id, selectedCard);
      }
    }
    // challenge offered, accept or reject
    else if (m.request.equals("challenge_offered")) {
      if (cardsState.debugInfo)
        System.out.println("Accept Challenge <<<<<<<<<<<<<<<<<<");
      if (restMajorityGreaterThanThreshold(m.state, cardsState, 0.7)) {
        return new AcceptChallengeMessage(m.request_id);
      }
      return new RejectChallengeMessage(m.request_id);
      //return (Math.random() < 0.5)
      //    ? new AcceptChallengeMessage(m.request_id)
      //    : new RejectChallengeMessage(m.request_id);
    }
    return null;
  }
}
