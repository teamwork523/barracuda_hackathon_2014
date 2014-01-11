package com.barracuda.contest2013;

public class Strategy {
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
  
  // TODO: 
  /******************************************************/
  /************** Challenge Strategy ********************/
  /******************************************************/
  
  public static boolean majorityGreaterThanThreshold(int[] hands, 
                                                     GameState state, 
                                                     Cards cards,
                                                     double threshold) {
    int 
    return true;
  }
}
