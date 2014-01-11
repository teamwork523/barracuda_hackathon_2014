package com.barracuda.contest2013;

import java.util.ArrayList;
import java.util.Date;

public class BruteForceSearch {
	
	int sttotalcase;
	int sttotalwincase;
	int sttotaltiecase;
	int sttotallosecase;
	int[] stwincase;
	int[] stwincasetheyfirst;
	double winratio;
	double tieratio;
	double loseratio;
	double[] stwincaseratio;
	double[] stwincasetheyfirstratio;
	int bestwin;
	int bestwintheyfirst;
	static boolean usingrand=false;
	
	
	public BruteForceSearch(){
		sttotalcase=0;
		sttotalwincase=0;
		sttotaltiecase=0;
		sttotallosecase=0;
		stwincase=new int[5];
		stwincaseratio=new double[5];
		stwincasetheyfirst=new int[5];
		stwincasetheyfirstratio=new double[5];
		clearst();
	}
	void clearst(){
		sttotalcase=0;
		sttotalwincase=0;
		sttotaltiecase=0;
		sttotallosecase=0;
		for (int i=0;i<5;i++)
		  stwincase[i]=0;	
		for (int i=0;i<5;i++)
	      stwincasetheyfirst[i]=0;		
	}
	
	void evalWithOppCard(MoveMessage mm, Cards cc, int[] oppcards, int oppcardsnum){
	/*	System.out.print("old ones: ");
		for (int i=0;i<oppcardsnum;i++)
			System.out.print(oppcards[i]+" ");
		System.out.println();
		System.out.print("enumrate ones: ");
		for (int i=oppcardsnum;i<oppcards.length;i++)
			System.out.print(oppcards[i]+" ");
		System.out.println();
		*/
		ArrayList<Integer> myavalcard=new ArrayList<Integer>(cc.myAvailCards);
		ArrayList<Integer> oppcard=new ArrayList<Integer>();
		for (int i=oppcardsnum;i<oppcards.length;i++)
			oppcard.add(oppcards[i]);
		
	//	calclld(myavalcard,oppcard,0,0);
		int mytrick=mm.state.your_tricks;
		int opptrick=mm.state.their_tricks;		
	
		if (mm.request.equals("request_card")) {
			if (mm.state.card==0){
			//me first
				calclld(myavalcard,oppcard,mytrick,opptrick);
				
			}
			else {
			//their first
				int cardot=mm.state.card;
				for (int i=0;i<myavalcard.size();i++){
					int mycard=myavalcard.get(i);
					ArrayList<Integer> newmyavalcard=new ArrayList<Integer>();
					for (int j=0;j<myavalcard.size();j++) 
						if (j!=i)
							newmyavalcard.add(myavalcard.get(j));


					int ewincase=sttotalwincase;
					if (mycard>cardot) calclld(newmyavalcard,oppcard,mytrick+1,opptrick);
					if (mycard<cardot) calclld(newmyavalcard,oppcard,mytrick,opptrick+1);
					if (mycard==cardot) calclld(newmyavalcard,oppcard,mytrick,opptrick);
					stwincasetheyfirst[i]+=sttotalwincase-ewincase;
			        	
				}
			}
	//		mm.state.can_challenge) 

		}
		else if (mm.request.equals("challenge_offered")) {
			if ((oppcard.size()-1)==myavalcard.size()){
				int cardot=cc.myHistory.get(cc.myHistory.size()-1);
				for (int i=0;i<oppcard.size();i++){
					int oppcardnum=oppcard.get(i);
					ArrayList<Integer> newoppcard=new ArrayList<Integer>();
					for (int j=0;j<oppcard.size();j++) 
						if (j!=i)
							newoppcard.add(oppcard.get(j));


					if (cardot>oppcardnum) calclld(myavalcard,newoppcard,mytrick+1,opptrick);
					if (cardot<oppcardnum) calclld(myavalcard,newoppcard,mytrick,opptrick+1);
					if (cardot==oppcardnum) calclld(myavalcard,newoppcard,mytrick,opptrick);
			        	
				}
			}
			else if (oppcard.size()==myavalcard.size()){
				calclld(myavalcard,oppcard,mytrick,opptrick);
			}
			else {
				System.out.print("error in challenge_offered case. The number of two sides does not match");
			}
			
		}
	//	*/
		
	}
	
	
	boolean genNextPerm(int a[], int n){
	    int j = n - 2;
	    while (j >= 0 && a[j] >= a[j+1]) --j;
	 
	    if (j < 0) return false;
	 
	    int i=n-1;
	    while (a[j] >= a[i]) --i;
	 
	    int t=a[j]; a[j]=a[i]; a[i]=t;
	 
	    int l=j+1, r=n-1; 
	    while (l < r){
	        t=a[l]; a[l]=a[r]; a[r]=t;
	        ++l;
	        --r;
	    }	 
	    return true;
	}
	
	private void calclld(ArrayList<Integer> myavalcard,	ArrayList<Integer> theircard, int mytrick, int opptrick) {
		if (theircard.size()!=myavalcard.size()){
			for (int i=0;i<myavalcard.size();i++)
				System.out.print(myavalcard.get(i)+" ");
			System.out.print(";");
			for (int i=0;i<theircard.size();i++)
				System.out.print(theircard.get(i)+" ");
			System.out.print(";");
			System.out.print(theircard.size()+","+myavalcard.size()+" error in calclld(). The number of two sides does not match");
			return;
		}
		int arrlen=myavalcard.size();
		int[] orderarr=new int[5];
		for (int i=0;i<arrlen;i++) orderarr[i]=i;

	//	System.out.println();
		do{
			int tempmytrick=mytrick;
			int tempopptrick=opptrick;
			for (int i=0;i<arrlen;i++) {
			//	System.out.print(myavalcard.get(orderarr[i])+" ");
			   if (myavalcard.get(orderarr[i]) > theircard.get(i)) 	tempmytrick++;
			   if (myavalcard.get(orderarr[i]) < theircard.get(i)) 	tempopptrick++;
			}
		//	System.out.println();
	/*		System.out.println();
			System.out.print("my choice: ");
			for (int i=0;i<arrlen;i++) {
				System.out.print(myavalcard.get(orderarr[i])+" ");
			}
			System.out.println();
			System.out.print("their choice: ");
			for (int i=0;i<arrlen;i++) {
				System.out.print(theircard.get(i)+" ");
			}
			System.out.println();
			*/
			sttotalcase++;
			if (tempmytrick>tempopptrick){
			//we win				
				sttotalwincase++;
				stwincase[orderarr[0]]++;		
	//			System.out.println();
	//			System.out.println("win");		
			}
			if (tempmytrick<tempopptrick){
			//we lose			
				sttotallosecase++;	
	//			System.out.println();
	//			System.out.println("lose");	
				
			}
			if (tempmytrick==tempopptrick){
			//tie				
				sttotaltiecase++;	
		//		System.out.println();
		//		System.out.println("tie");	
				
			}

			
		}
		while (genNextPerm(orderarr, arrlen));
		
		
		
	}

	void getResult(MoveMessage mm, Cards cc){
		ArrayList<Integer> oph=new ArrayList<Integer>();
		int[] tempcr=new int[13];
		for (int i=0;i<13;i++)	tempcr[i]=0;
		int oppcardsnum=cc.oppoHistory.size();

		int[] opharr=new int[5];
		for (int i=0;i<oppcardsnum;i++)
			opharr[i]=cc.oppoHistory.get(i);
		
		
		clearst();
		
		if (oppcardsnum<5){
			for (int i5=0;i5<13;i5++){        	
        		if (tempcr[i5]>=cc.cardRemain[i5]) continue;
        		opharr[4]=(i5+1);
        		tempcr[i5]++;
        		
        		if (oppcardsnum==4){
        			evalWithOppCard(mm,cc,opharr,oppcardsnum);
        		}
        		else {        		
	        		for (int i4=i5;i4<13;i4++){
	        			if (tempcr[i4]>=cc.cardRemain[i4]) continue;
	            		opharr[3]=(i4+1);
	            		tempcr[i4]++;
	            		
	            		if (oppcardsnum==3){
	            			evalWithOppCard(mm,cc,opharr,oppcardsnum);
	            		}
	            		else {
		            		for (int i3=i4;i3<13;i3++){
		            			if (tempcr[i3]>=cc.cardRemain[i3]) continue;
		                		opharr[2]=(i3+1);
		                		tempcr[i3]++;
		                		
		                		if (oppcardsnum==2){
			            			evalWithOppCard(mm,cc,opharr,oppcardsnum);
			            		}
			            		else {
			                		for (int i2=i3;i2<13;i2++){
			                			if (tempcr[i2]>=cc.cardRemain[i2]) continue;
			                    		opharr[1]=(i2+1);
			                    		tempcr[i2]++;
			                    		
			                    		if (oppcardsnum==1){
					            			evalWithOppCard(mm,cc,opharr,oppcardsnum);
					            		}
					            		else {
				                    		for (int i1=i2;i1<13;i1++){
				                    			if (tempcr[i1]>=cc.cardRemain[i1]) continue;
				                        		opharr[0]=(i1+1);
				                        		tempcr[i1]++;
				                        		
				                        		evalWithOppCard(mm,cc,opharr,oppcardsnum);
				                        		
				                        		tempcr[i1]--;
				                    		}
					            		}
			                    		tempcr[i2]--;
			                		}
			            		}
		                		tempcr[i3]--;
		                		
		            		}
	            		}
	            		
	            		tempcr[i4]--;
	        			
	        		}
        		}
        		
        		tempcr[i5]--;        		
        		
        	}
        	
        }
		
	}
	
	void generatestratio(MoveMessage m, Cards cd){
		int maxcard=0;
		for (int i=0;i<cd.myAvailCards.size();i++)
		    if (cd.myAvailCards.get(i)>maxcard){
		    	maxcard=cd.myAvailCards.get(i);
		    }
		
		int undecideround=cd.myAvailCards.size();
		if (undecideround<(5-cd.oppoHistory.size()))
			undecideround=(5-cd.oppoHistory.size());
		
		
		winratio=sttotalwincase*1.0/sttotalcase;
		tieratio=sttotaltiecase*1.0/sttotalcase;
		loseratio=sttotallosecase*1.0/sttotalcase;
		bestwin=0;
		System.out.println("winratio: "+winratio);
		System.out.println("tieratio: "+tieratio);
		System.out.println("loseratio: "+loseratio);
		double max=0;
		int sumwincase=0;
		for (int i=0;i<cd.myAvailCards.size();i++){
		    stwincaseratio[i]=stwincase[i]*1.0/sttotalcase;
		    sumwincase+=stwincase[i];
	//	    System.out.print(cd.myAvailCards.get(i)+","+stwincaseratio[i]+" ");
		    if (stwincase[i]>=max 
		    		&& (cd.myAvailCards.size()<5 || cd.myAvailCards.get(i)<12)
		    		&& (cd.myAvailCards.get(i)!=maxcard || maxcard<12 || undecideround<=2)
		    		){
		    	max=stwincase[i];
		    	bestwin=i;
		    }
		}
		
		if (usingrand){
			int rand=(int)(Math.random() * sumwincase);
			System.out.println("rand: "+rand+ " sumwincase:"+sumwincase);
			int randsum=0;
			int i=0;
			for (;i<cd.myAvailCards.size() && randsum<=rand; i++)
				randsum+=stwincase[i];	
			i--;
			bestwin=i;			
		}
		
		
		
		
	//	if (bestwin < cd.myAvailCards.size())
	//	System.out.println("; best: "+ cd.myAvailCards.get(bestwin)+" "+max);
		
		bestwintheyfirst=0;
		max=0;
		sumwincase=0;
		for (int i=0;i<cd.myAvailCards.size();i++){
		    stwincasetheyfirstratio[i]=stwincasetheyfirst[i]*1.0/sttotalcase;
		    sumwincase+=stwincasetheyfirst[i];
		    System.out.print(cd.myAvailCards.get(i)+","+stwincasetheyfirstratio[i]+" ");
		    if (stwincasetheyfirst[i]>max 
		    		&& (cd.myAvailCards.size()<5 || cd.myAvailCards.get(i)<12)
		    		&& (cd.myAvailCards.get(i)!=maxcard || maxcard<12 || undecideround<=2)
		    		){
		    	max=stwincasetheyfirst[i];
		    	bestwintheyfirst=i;
		    }
		}
		
		if (usingrand){
			int rand=(int)(Math.random() * sumwincase);
			System.out.println("rand: "+rand+ " sumwincase:"+sumwincase);
			int randsum=0;
			int i=0;
			for (;i<cd.myAvailCards.size() && randsum<=rand; i++)
				randsum+=stwincasetheyfirst[i];	
			i--;
			bestwintheyfirst=i;			
		}
		
		if (bestwintheyfirst < cd.myAvailCards.size())
		System.out.println("; best: "+ cd.myAvailCards.get(bestwintheyfirst)+" "+max);
		
	}
	
/*	int findTheBestCard(Cards cd, int oppocard){
		
		bestwintheyfirst=0;
		int max=0;
		for (int i=0;i<cd.myAvailCards.size();i++){
		    if (stwincasetheyfirst[i]>max && cd.myAvailCards.get(i)>=oppocard){
		    	max=stwincasetheyfirst[i];
		    	bestwintheyfirst=i;
		    }
		}
		
	}
	*/
	
	boolean goodchancetoaccept(MoveMessage m, Cards cd){
		int maxcard=0;
		for (int i=0;i<cd.myAvailCards.size();i++)
	    if (cd.myAvailCards.get(i)>maxcard){
	    	maxcard=cd.myAvailCards.get(i);
	    }
		int maxcardnum=0;
		for (int i=0;i<cd.myAvailCards.size();i++)
		    if (cd.myAvailCards.get(i)==maxcard){
		    	maxcardnum++;
		    }
		
		if (cd.myAvailCards.size()>1){
			int bigcardnum=0;
			int bigvalue=10;
			for (int i=0;i<cd.myAvailCards.size();i++){
				if (cd.myAvailCards.get(i)>=bigvalue)
					bigcardnum++;
			}
			if (bigcardnum>cd.myAvailCards.size()/2)
				return true;
		}
		
		if (m.state.your_points>=9){
			int bigcardnum=0;
			int bigvalue=9;
			for (int i=0;i<cd.myAvailCards.size();i++){
				if (cd.myAvailCards.get(i)>=bigvalue)
					bigcardnum++;
			}
			if (bigcardnum>cd.myAvailCards.size()/2)
				return true;
		}
		
		int undecideround=cd.myAvailCards.size();
		if (undecideround<(5-cd.oppoHistory.size()))
			undecideround=(5-cd.oppoHistory.size());
		
		if ((m.state.your_tricks-m.state.their_tricks)>= undecideround)
			return true;
		
		if (maxcard==13 && (maxcardnum+(m.state.your_tricks-m.state.their_tricks))>= undecideround)
			return true;
		
		if (m.state.their_points==9)
			return true;
		
		return false;
		
	}
	boolean goodcardfrombegining(MoveMessage m, Cards cd){
		if (cd.myAvailCards.size()>1){
			int bigcardnum=0;
			int bigvalue=11;
			for (int i=0;i<cd.myAvailCards.size();i++){
				if (cd.myAvailCards.get(i)>=bigvalue)
					bigcardnum++;
			}
			if (bigcardnum>cd.myAvailCards.size()/2) {
				return true;
			}
		}
		return false;
		
	}
	
	
	public PlayerMessage handleMessage(MoveMessage m, Cards cd) {
		System.out.println();	
		System.out.println("new game " + m.state.game_id);
		System.out.print("my card: ");
		for (int i=0;i<cd.myHistory.size();i++){
			System.out.print(cd.myHistory.get(i)+" ");
		}
		System.out.print(" | ");
		for (int i=0;i<cd.myAvailCards.size();i++){
			System.out.print(cd.myAvailCards.get(i)+" ");
		}
		System.out.println();
		System.out.print("opp card: ");
		for (int i=0;i<cd.oppoHistory.size();i++){
			System.out.print(cd.oppoHistory.get(i)+" ");
		}
		System.out.println();
		
		    long tobegin=(new Date()).getTime();
			getResult(m, cd);
			System.out.println("time: "+ ((new Date()).getTime()-tobegin));
			generatestratio(m, cd);
			
			if (cd.oppoHistory.size()>=5 && m.request.equals("request_card")){
				System.out.println("cd.oppoHistory.size()>=5");
				int lastmycard=cd.myAvailCards.get(cd.myAvailCards.size()-1);
				int lastoppocard=cd.oppoHistory.get(cd.oppoHistory.size()-1);
				int curmytrick=m.state.your_tricks;
				int curopptrick=m.state.their_tricks;
				if (lastmycard>lastoppocard) curmytrick++;
				if (lastmycard<lastoppocard) curopptrick++;
				
				if (curmytrick>curopptrick){
				//we will win
					if (m.state.can_challenge){
					  System.out.println("Decision: offer challenge");
				      cd.enableChallangeRequest();
					  return new OfferChallengeMessage(m.request_id);
					}
					else {
				      cd.updateMyHistory(lastmycard);
						System.out.println("Decision: PlayCardMessage "+lastmycard);
					  return new PlayCardMessage(m.request_id, lastmycard);
					}
				}
				else {
					if (m.state.can_challenge){
						//bluffing
						if (curmytrick==curopptrick && lastoppocard<5){
							System.out.println("bluffing Decision: offer challenge");
					        cd.enableChallangeRequest();
						    return new OfferChallengeMessage(m.request_id);
						}
						if (m.state.their_points==9){
							System.out.println("bluffing Decision: offer challenge");
					        cd.enableChallangeRequest();
						    return new OfferChallengeMessage(m.request_id);
						}
					}
					
					cd.updateMyHistory(lastmycard);
					System.out.println("Decision: PlayCardMessage "+lastmycard);
					return new PlayCardMessage(m.request_id, lastmycard);
				}
				
			}
			else {
			
			if (m.request.equals("request_card")) {				
				if (m.state.can_challenge){
				//can challenge
					if (winratio>0.7 && m.state.your_points >= 7){
						System.out.println("Decision: offer challenge");
						cd.enableChallangeRequest();
						return new OfferChallengeMessage(m.request_id);
					}	
					
					if (goodcardfrombegining(m,cd)){
						System.out.println("Decision: offer challenge");
						cd.enableChallangeRequest();
						return new OfferChallengeMessage(m.request_id);
					}
					
					//bluffing
					if (loseratio>0.7 && m.state.their_points > m.state.your_points+2){
						System.out.println("Decision: offer challenge");
						cd.enableChallangeRequest();
						return new OfferChallengeMessage(m.request_id);
					}
					
					//bluffing
					if (loseratio>0.6 && (cd.allCardNum-cd.hiddenNum+)  m.state.their_points > m.state.your_points+2){
						System.out.println("Decision: offer challenge");
						cd.enableChallangeRequest();
						return new OfferChallengeMessage(m.request_id);
					}
				}
				
				if (m.state.card==0){
				//we first
					int bw=cd.myAvailCards.get(bestwin);
					System.out.println("Decision: PlayCardMessage "+cd.myAvailCards.get(bestwin)+" "+bestwin);
					cd.updateMyHistory(bw);					
					return new PlayCardMessage(m.request_id, bw);									
				}
				else {
				//they first
					int bw=cd.myAvailCards.get(bestwintheyfirst);
					System.out.println("Decision: PlayCardMessage "+cd.myAvailCards.get(bestwintheyfirst)+" "+bestwintheyfirst);
					//overide with BA
				//	bw = findTheBestCard(cd, cd.oppoHistory.get(cd.oppoHistory.size()-1));
					bw = refineBW(bw,m,cd);
					cd.updateMyHistory(bw);					
					return new PlayCardMessage(m.request_id, bw);
				}				
			}
			else if (m.request.equals("challenge_offered")) {
				if ((winratio+tieratio) > 0.85 || goodchancetoaccept(m,cd)){
					System.out.println("Decision: Accept challenge");
					return new AcceptChallengeMessage(m.request_id);
				}
				else {
					System.out.println("Decision: Reject challenge");
					return new RejectChallengeMessage(m.request_id);
				}	
			}
			}
			
			return null;
	}
	private int refineBW(int bw, MoveMessage m, Cards cd) {
		int theircard=cd.oppoHistory.get(cd.oppoHistory.size()-1);
		if (bw<theircard){
			int mincard=bw;
			for (int i=0;i<cd.myAvailCards.size();i++)
		    if (cd.myAvailCards.get(i)<mincard){
		    	mincard=cd.myAvailCards.get(i);
		    }
            return mincard;
		}
		return bw;
	}

}
