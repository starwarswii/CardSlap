package cardslap;

import java.util.*;

public class Menu {
	static Scanner getKey;
	static int playerNum;
	
	public Menu() {
		getKey = new Scanner(System.in);
	}

	public static void displayMenu() {
		System.out.println("Welcome To _________ !");
		System.out.println();
		System.out.println("Please select the number of players and difficulties");
	}

	static public int playerNumSelect() {
		System.out.println("How many players?");
		playerNum = getKey.nextInt();
		return playerNum;
	}

	static public void displayDifficulties() {
		System.out.println("Difficulties?");
		System.out.println();
		System.out.println("1. Impossible");
		System.out.println("2. god");
		System.out.println("3. champion");
		System.out.println("4. human");
		System.out.println();
	}

	static public int difficultieSelection(){
		System.out.println("now, your choice? (1 - 4)");
		int diff = getKey.nextInt();
		if(diff > 0 && diff <= 4)
			return diff;
		else{
			System.out.println("input is invlad, please input numner 1-4");
			return -1;
		}
	}
	
	static public int[] getSettings(){
		getKey = new Scanner(System.in);
		int numPlayers = playerNumSelect();
		displayDifficulties();
		int difficulty = difficultieSelection();
		int[] settings = {numPlayers, difficulty};
		return settings; 
		
	}
	
	
	
	
	
	
}