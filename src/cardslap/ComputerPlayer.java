package cardslap;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;

public class ComputerPlayer extends Player {
Robot robot;
Random randInt;
	public ComputerPlayer(/*int flipKey, int slapKey, */int playerNumber) {
		super(KeyEvent.VK_HIRAGANA, KeyEvent.VK_KANJI, playerNumber);
		try {
		robot = new Robot();
		} catch (Exception e) {
		}
		randInt = new Random();
	}
	public void think() {
		int pTurn = CardSlap.game.currentPlayerTurn;
		int numP = CardSlap.game.players.size();
		int turn = pTurn % numP;
		if(Rules.isValidSlap()){
			System.out.println("good slap");
			cSlap();
		}
		else if (turn==playerNumber) {//(CardSlap.game.currentPlayerTurn % CardSlap.game.players.size()) == playerNumber) {
			cFlip();
			System.out.println("flip");
		}
		else {
			int number = randInt.nextInt(35)+1;
			if (number==42) {
				System.out.println("bad slap");
				cSlap();
			}
		}
	}
	
	void cSlapB() {
		if (Rules.isValidSlap()) {
			super.slap();
		} else {
			invalidSlap();
		}
	}
	
	void cSlap() {
		robot.keyPress(KeyEvent.VK_KANJI);
		robot.keyRelease(KeyEvent.VK_KANJI);
		CardSlap.game.redraw();
	}
	void cFlip() {
		if (hand.size() > 0) {
			robot.keyPress(KeyEvent.VK_HIRAGANA);
			robot.keyRelease(KeyEvent.VK_HIRAGANA);
		}
		else {
			CardSlap.game.currentPlayerTurn++;
		}
		CardSlap.game.redraw();
	}
	
	void startAI() {
		int maxDelay = 1300;
		int minDelay = 1;
		Thread thread = new Thread(() -> {
			int time = 0;
			while(!CardSlap.game.gameOver) {
				try {
					time = randInt.nextInt(maxDelay-minDelay+1)+minDelay;
				Thread.sleep(time+867);
				} catch (Exception e) {
				}
				think();
			}
			
		});
		thread.start();
		
	}
	
	

}
