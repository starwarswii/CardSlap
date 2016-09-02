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

		BufferedImage image = new BufferedImage(500,500, BufferedImage.TYPE_INT_RGB);

		graphics = image.createGraphics();

		graphics.setStroke(new BasicStroke(1));
		graphics.setFont(new Font("Calibri", Font.PLAIN, 75)); 
		

		JLabel display = new JLabel(new ImageIcon(image));
		
		
		addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				
				//System.out.println("aasa");
				for (Player a : players) {
					if (e.getKeyCode() == a.flipKeyCode) {
						//System.out.println("num"+a.playerNumber);
						
						if ((currentPlayerTurn % players.size()) == a.playerNumber) {
							a.flip();
							
							int faceCardValue = Rules.getFaceCardValue();
							
							if (faceCardValue != -1) {//if it is a facecard
								faceCardChances = faceCardValue;
								System.out.println("face card pTurn");
								currentPlayerTurn++;
							} 
							else if (faceCardChances > 1) {
									faceCardChances--;
								} else if (faceCardChances == 1) {
									faceCardChances--;;
									players.get((currentPlayerTurn-1) % players.size()).slap();
									System.out.println("bad face card turn+");
									currentPlayerTurn++;
								}
								else {
									System.out.println("normal turn+");
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

		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {}

		//setResizable(false);

		pack();
		setLocationRelativeTo(null);//puts the window in the center of the screen. Must be called after pack
		
		//Collections.shuffle(deck);
		//redraw();
		
		
	}
	
	//void activateComputer
	
	
	public void start() {
		setVisible(true);
		//Menu.displayMenu();
		//Menu.getSettings();
		players.add(new Player(KeyEvent.getExtendedKeyCodeForChar('q'), KeyEvent.getExtendedKeyCodeForChar('a'), 0));
		players.add(new ComputerPlayer(/*KeyEvent.getExtendedKeyCodeForChar('w'), KeyEvent.getExtendedKeyCodeForChar('s'), */1));
		//players.add(new ComputerPlayer(1));
		startAI();
		dealCards();
		redraw();
//		while(gameContinue()) {
//			
//			// nothing?		
//		}
//		System.out.println("Game over");
	}
	
	void startAI() {
		for (Player p : players) {
			if (p instanceof ComputerPlayer) {
				((ComputerPlayer)p).startAI();
			}
		}
	}
	
	public void dealCards() {
		Collections.shuffle(deck);
		while (deck.size() > 0){
			for(int loop = 0; loop < players.size(); loop++){
			players.get(loop).addCards(deck.remove(0));
							
			}
			
		
		}
	}

	
	public static BufferedImage getResource(String filename) {
		try {
			return ImageIO.read(CardSlap.class.getClassLoader().getResource("resources/"+filename));
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean gameContinue() {
		boolean go = false;
		for(int i = 0; i < players.size(); i++)
		{
			if(players.get(i).getCardCount() == 52){
				
				System.out.println("Game Over");
				System.out.println("Player " + i+1 + " wins!");
				System.exit(0);
				//return false;
			}
			else {
				go = true;
			}
		}
		return go;
	}
	
	
	
	public void redraw() {
		//ArrayList<Card> h = deck;
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		int x = 250;
		int y = 250;
		for (int i = 0; i < 3 && i < deck.size(); i++) {
			//System.out.println("j"+String.valueOf(deck.get(i).value));
			char[] number = String.valueOf(deck.get(i).value).toCharArray();
			switch (deck.get(i).value) {
			case 1:
				number = new char[] {'A'};
				break;
			case 11:
				number = new char[] {'J'};
				break;
			case 12:
				number = new char[] {'Q'};
				break;
			case 13:
				number = new char[] {'K'};
			}
			//System.out.println(number);
			graphics.setColor(deck.get(i).suit.getColor());
			//graphics.setColor(Color.BLACK);
			graphics.drawChars(number, 0, number.length, x, y);
			graphics.drawImage(IMAGES[deck.get(i).suit.ordinal()], x+75, y-100, 100, 100, this);
			y+=100;
			repaint();
		}
		
	}
	
	
}
