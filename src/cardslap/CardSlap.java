package cardslap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class CardSlap extends JFrame {
	
	Graphics2D graphics;
	static CardSlap game;
	int currentPlayerTurn = 0;
	int difficulty = 0;
	static final int WIDTH = 500;
	static final int HEIGHT = 500;
	int faceCardChances = 0;
	
	static final BufferedImage[] IMAGES = {
		getResource("hearts.png"),
		getResource("clubs.png"),
		getResource("diamonds.png"),
		getResource("spades.png")
	};
	boolean gameOver = false;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ArrayList<Card> deck = new ArrayList(Arrays.asList(new Card[] {
		new Card(1, Suit.HEARTS), new Card(1, Suit.CLUBS), new Card(1, Suit.DIAMONDS), new Card(1, Suit.SPADES),
		new Card(2, Suit.HEARTS), new Card(2, Suit.CLUBS), new Card(2, Suit.DIAMONDS), new Card(2, Suit.SPADES),
		new Card(3, Suit.HEARTS), new Card(3, Suit.CLUBS), new Card(3, Suit.DIAMONDS), new Card(3, Suit.SPADES),
		new Card(4, Suit.HEARTS), new Card(4, Suit.CLUBS), new Card(4, Suit.DIAMONDS), new Card(4, Suit.SPADES),
		new Card(5, Suit.HEARTS), new Card(5, Suit.CLUBS), new Card(5, Suit.DIAMONDS), new Card(5, Suit.SPADES),
		new Card(6, Suit.HEARTS), new Card(6, Suit.CLUBS), new Card(6, Suit.DIAMONDS), new Card(6, Suit.SPADES),
		new Card(7, Suit.HEARTS), new Card(7, Suit.CLUBS), new Card(7, Suit.DIAMONDS), new Card(7, Suit.SPADES),
		new Card(8, Suit.HEARTS), new Card(8, Suit.CLUBS), new Card(8, Suit.DIAMONDS), new Card(8, Suit.SPADES),
		new Card(9, Suit.HEARTS), new Card(9, Suit.CLUBS), new Card(9, Suit.DIAMONDS), new Card(9, Suit.SPADES),
		new Card(10, Suit.HEARTS), new Card(10, Suit.CLUBS), new Card(10, Suit.DIAMONDS), new Card(10, Suit.SPADES),
		new Card(11, Suit.HEARTS), new Card(11, Suit.CLUBS), new Card(11, Suit.DIAMONDS), new Card(11, Suit.SPADES),
		new Card(12, Suit.HEARTS), new Card(12, Suit.CLUBS), new Card(12, Suit.DIAMONDS), new Card(12, Suit.SPADES),
		new Card(13, Suit.HEARTS), new Card(13, Suit.CLUBS), new Card(13, Suit.DIAMONDS), new Card(13, Suit.SPADES)		
	}));
	public ArrayList<Player> players = new ArrayList<Player>();
	
	public static void main(String[] args) {
		game = new CardSlap();
		game.start();
		
	}
	
	public CardSlap() {
		super("Card Slap");
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		
		graphics = image.createGraphics();
		
		graphics.setStroke(new BasicStroke(5));
		graphics.setFont(new Font("Calibri", Font.PLAIN, 75));
		
		JLabel display = new JLabel(new ImageIcon(image));
		
		addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				game.redraw();
				for (Player a : players) {
					if (e.getKeyCode() == a.flipKeyCode) {
						
						if ((currentPlayerTurn % players.size()) == a.playerNumber) {
							a.flip();
							
							int faceCardValue = Rules.getFaceCardValue();
							
							if (faceCardValue != -1) {// if it is a facecard
								game.redraw();
								faceCardChances = faceCardValue;
								currentPlayerTurn++;
								game.redraw();
							} else if (faceCardChances > 1) {
								game.redraw();
								faceCardChances--;
								game.redraw();
							} else if (faceCardChances == 1) {
								faceCardChances--;
								game.redraw();
								players.get(getLastPlayer()).slap();
								currentPlayerTurn++;
							} else {
								currentPlayerTurn++;
							}
						}
					} else if (e.getKeyCode() == a.slapKeyCode) {
						if (Rules.isValidSlap()) {
							a.slap();
						} else {
							a.invalidSlap();
						}
					}
				}
				game.redraw();
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane.add(display);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		setResizable(false);
		
		pack();
		setLocationRelativeTo(null);// puts the window in the center of the screen. Must be called after pack
		
	}
	
	public void start() {
		Thread repaintPlz = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				redraw();
				gameContinue();
			}
		});
		repaintPlz.start();
		
		JOptionPane.showMessageDialog(this, "Welcome to CardSlap!\n\nThis IS CardSlap!\n\nIt's basically Egyptian Rat Screw. Press OK to set options and then start playing!");
		
		int playerCount = 0;
		while (true) {
			String output = JOptionPane.showInputDialog("How many players?");
			
			try {
				playerCount = Integer.parseInt(output);
				if (playerCount > 1) {
					break;
				}
			} catch (Exception e) {
			}
			System.out.println(output);
		}
		
		String[] keyGroups = null;
		while (true) {
			
			String output = JOptionPane.showInputDialog("Input the keys in pairs of two per play separated by commas");
			if (output.matches("(\\w{2}(,|$))+")) {
				keyGroups = output.split(",");
				break;
			}
		}
		
		String[] computerValues = null;
		while (true) {
			
			String output = JOptionPane.showInputDialog("Input the computer players values (0 or 1), separated by commas");
			if (output.matches("([01](,|$))+")) {
				computerValues = output.split(",");
				break;
			}
		}
		
		for (int i = 0; i < playerCount; i++) {
			if (computerValues[i].equals("1")) {
				players.add(new ComputerPlayer(i));
			} else {
				players.add(new Player(KeyEvent.getExtendedKeyCodeForChar(keyGroups[i].charAt(0)), KeyEvent.getExtendedKeyCodeForChar(keyGroups[i].charAt(1)), i));
			}
		}
		
		setVisible(true);
		// Menu.displayMenu();
		// Menu.getSettings();
		// players.add(new Player(KeyEvent.getExtendedKeyCodeForChar('q'), KeyEvent.getExtendedKeyCodeForChar('a'), 0));
		// players.add(new Player(KeyEvent.getExtendedKeyCodeForChar('w'), KeyEvent.getExtendedKeyCodeForChar('s'), 1));
		// players.add(new ComputerPlayer(1));
		// players.add(new ComputerPlayer(0));
		startAI();
		
		dealCards();
		redraw();
	}
	
	void startAI() {
		for (Player p : players) {
			if (p instanceof ComputerPlayer) {
				((ComputerPlayer) p).startAI();
			}
		}
	}
	
	public void dealCards() {
		Collections.shuffle(deck);
		while (deck.size() > 0) {
			for (int loop = 0; loop < players.size(); loop++) {
				try {
					players.get(loop).addCards(deck.remove(0));
				} catch (Exception e) {
				}
				
			}
			
		}
	}
	
	public static BufferedImage getResource(String filename) {
		try {
			return ImageIO.read(CardSlap.class.getClassLoader().getResource("resources/" + filename));
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean gameContinue() {
		boolean go = false;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getCardCount() == 52) {
				JOptionPane.showMessageDialog(this, "Game Over\nPlayer " + (i + 1) + " wins!");
				System.exit(0);
				// return false;
			} else {
				go = true;
			}
		}
		return go;
	}
	
	public void setCurrentPlayerToNextNonZeroPlayer() {
		currentPlayerTurn++;
		while (players.get(currentPlayerTurn % players.size()).hand.size() == 0) {
			currentPlayerTurn++;
		}
	}
	
	public int getLastPlayer() {
		if (currentPlayerTurn % players.size() == 0) {
			return players.size() - 1;
		} else {
			return (currentPlayerTurn - 1) % players.size();
		}
	}
	
	public void redraw() {
		graphics.setColor(new Color(53, 94, 59));
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		int x = 250;
		int y = 250;
		for (int i = 0; i < 3 && i < deck.size(); i++) {
			char[] number = String.valueOf(deck.get(i).value).toCharArray();
			switch (deck.get(i).value) {
			case 1:
				number = new char[] { 'A' };
				break;
			case 11:
				number = new char[] { 'J' };
				break;
			case 12:
				number = new char[] { 'Q' };
				break;
			case 13:
				number = new char[] { 'K' };
			}
			graphics.setColor(deck.get(i).suit.getColor());
			graphics.setFont(new Font("Calibri", Font.PLAIN, 75));
			graphics.setColor(Color.WHITE);
			graphics.fillRect(x - 50, y - 100, 250, 100);
			graphics.setColor(Color.BLACK);
			graphics.drawRect(x - 50, y - 100, 250, 100);
			graphics.setColor(deck.get(i).suit.getColor());
			graphics.drawChars(number, 0, number.length, x, y - 20);
			graphics.drawImage(IMAGES[deck.get(i).suit.ordinal()], x + 75, y - 100, 100, 100, this);
			y += 100;
		}
		x = 30;
		y = 30;
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Calibri", Font.PLAIN, 20));
		char[] debug = ("Chances to Get Face Card: " + faceCardChances).toCharArray();
		graphics.drawChars(debug, 0, debug.length, x, y);
		y += 30;
		for (int i = 0; i < players.size(); i++) {
			char[] chars = ("Player" + (i + 1) + " Cards: " + players.get(i).hand.size()).toCharArray();
			graphics.drawChars(chars, 0, chars.length, x, y);
			y += 30;
		}
		
		graphics.setFont(new Font("Calibri", Font.PLAIN, 20));
		debug = ("Pile Count: " + deck.size()).toCharArray();
		graphics.drawChars(debug, 0, debug.length, x, y);
		y += 30;
		
		graphics.setFont(new Font("Calibri", Font.PLAIN, 20));
		try {
			debug = ("Player " + ((currentPlayerTurn % players.size()) + 1) + "'s Turn").toCharArray();
			graphics.drawChars(debug, 0, debug.length, x, y);
			y += 30;
		} catch (Exception e) {}
		
		repaint();
	}
	
}
