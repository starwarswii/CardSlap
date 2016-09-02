package cardslap;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
	
	ArrayList<Card> hand;
	int flipKeyCode;
	int slapKeyCode;
	int playerNumber;
	
	
	public Player(int flipKey, int slapKey, int playerNumber) {
		hand = new ArrayList<Card>();
		flipKeyCode = flipKey;
		slapKeyCode = slapKey;
		this.playerNumber = playerNumber;
	}
	
	void flip() {//TODO flip and slap should redraw screen
		if (hand.size() > 0) {
			CardSlap.game.deck.add(0, hand.remove(0));
		}
		CardSlap.game.redraw();
	}
	
	void slap() {
		Collections.shuffle(CardSlap.game.deck);
		hand.addAll(CardSlap.game.deck);
		CardSlap.game.deck.clear();
		CardSlap.game.redraw();
	}
	
	void addCards(Card card) {
		hand.add(hand.size(), card);
	}
	void addCards(ArrayList<Card> cards) {
		hand.addAll(hand.size(), cards);
	}
	
	int getCardCount() {
		return hand.size();
	}
	void invalidSlap(){
		try {
			CardSlap.game.deck.add(hand.remove(0));
		} catch (Exception e) {}
		
		
	}
	
	
}
