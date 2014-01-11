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
    int requiredWinTricksCount = state.your_tricks - state.their_tricks;
    
    for (int i = 0; i < hands.length; i++) {
      if (cards.getBiggerProb(hands[i]) > threshold) {
        requiredWinTricksCount++;
      }
    }
    
    return (requiredWinTricksCount > 0) ? true : false;
  }
}
