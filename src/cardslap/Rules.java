package cardslap;

public class Rules {

	
	public static boolean isValidSlap() {
		try {
			if(pair()){
				return true;
			}
			else if(sandwich()){
				return true;
			}
			else if(sameSuit()){
				return true;
			}
			
		} catch (Exception e) {}
		return false;
	}
	
	
	
	static boolean pair() {
		if (CardSlap.game.deck.get(0).value == CardSlap.game.deck.get(1).value)
			return true;
		else
			return false;
	}
	
	static boolean sandwich() {
		if (CardSlap.game.deck.get(0).value == CardSlap.game.deck.get(2).value)
			return true;
		else
			return false;
	}
	
	static boolean sameSuit(){
		if(checkSuit()){
			return true;
		}
		else{
			return false;
		}
	}

	static private boolean checkSuit(){
		if(CardSlap.game.deck.get(0).suit == CardSlap.game.deck.get(1).suit){
			if(CardSlap.game.deck.get(1).suit == CardSlap.game.deck.get(2).suit){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	static int getFaceCardValue() {
		switch (CardSlap.game.deck.get(0).value) {
		case 1:
			return 4;
		case 11:
			return 1;
		case 10:
			return 2;
		case 13:
			return 3;
		default:
			return -1;
		}
	}
	 
}
