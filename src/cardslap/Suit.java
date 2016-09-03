package cardslap;

import java.awt.Color;

public enum Suit {
	
	HEARTS (0),
	CLUBS (1),
	DIAMONDS (2),
	SPADES (3);
	
	final int suit;
	
	Suit(int suit) {
		this.suit = suit;
	}
	
	public Color getColor() {
		if (ordinal() == 0 || ordinal() == 2) {
			return Color.RED;
		} else {
			return Color.BLACK;
		}
	}
}
