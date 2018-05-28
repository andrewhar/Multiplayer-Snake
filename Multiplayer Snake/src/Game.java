import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JPanel;

import java.util.Random;

public class Game {
	
	public static int numberOfPlayers;
	public static GameBoard board;
	public static MapDB db;
	private static UIController ui;
	
	public static Server server;
	
	public static ArrayList<HumanPlayer> humanPlayers;
	
	public static ArrayBlockingQueue<HumanPlayer> buffer_HumanPlayers; 
	
	public static boolean gameStarted = false;
	
	public static boolean gameOver = false;
	
	public static void main(String[] args) {

		board = new GameBoard(100, 100);
		
		ui = new UIController();
		
		humanPlayers = new ArrayList<HumanPlayer>();
		
		buffer_HumanPlayers = new ArrayBlockingQueue<HumanPlayer>(4);
		
		db = new MapDB();
		
		server = new Server();
		
		
		
	}
	
	/**
	 * Logs in the players
	 * @param credentials Credentials of the players
	 * @return 
	 * @return boolean[] containing the results of the login process
	 */
	public static void createPlayers (Credentials[] credentials) {
		
		for (int i = 0; i < Game.numberOfPlayers; i++) {
			
			HumanPlayer player = new HumanPlayer(i, credentials[i]);
			// *** CAN'T SYSOUT HERE FOR SOME REASON. GET AN OUTOFBOUNDSEXCEPTION **************** 
			int playerAlreadyAdded = humanPlayers.indexOf(player);
			
			if (playerAlreadyAdded == -1) {
				humanPlayers.add(player);
			} else {
				humanPlayers.set(playerAlreadyAdded, player);
			}
			
			try {
				buffer_HumanPlayers.put(player);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static UIController getUI() {
		return ui;
	}
	
	public static HumanPlayer getHumanPlayer(int id) {
		Iterator<HumanPlayer> playerIterator = Game.humanPlayers.iterator();
		while (playerIterator.hasNext()) {
			HumanPlayer player = playerIterator.next();
			if (player.getPlayerID() == id) {
				return player;
			}
		}
		return null; // if there are no human players
	}
	
	public static void initGame() {

		for (int i = 0; i < humanPlayers.size(); i++) {
			// create a new snake and give it a random colour. Then, assign it to a player
			Snake playerSnake = humanPlayers.get(i).getSnake(); 
			if (i == 0) {
				playerSnake.addBodyPart(90, 10, 0, false);
				playerSnake.addBodyPart(89, 10, 0, false);
				playerSnake.addBodyPart(88, 10, 0, true);
				playerSnake.setCurrentDirection(Snake.Direction.LEFT);
			} else if (i == 1) {
				playerSnake.addBodyPart(10, 11, 1, false);
				playerSnake.addBodyPart(10, 12, 1, false);
				playerSnake.addBodyPart(10, 13, 1, true);
				playerSnake.setCurrentDirection(Snake.Direction.DOWN);
			} else if (i == 2) {
				playerSnake.addBodyPart(10, 90, 2, false);
				playerSnake.addBodyPart(11, 90, 2, false);
				playerSnake.addBodyPart(12, 90, 2, true);
				playerSnake.setCurrentDirection(Snake.Direction.RIGHT);
			} else if (i == 3) {
				playerSnake.addBodyPart(90, 91, 3, false);
				playerSnake.addBodyPart(90, 92, 3, false);
				playerSnake.addBodyPart(90, 93, 3, true);
				playerSnake.setCurrentDirection(Snake.Direction.UP);
			}
		}
		
		// place apple in a random cell
		
		// note: 
		// food isn't placed and values for each cell are not set
		
	}
	

	
	
}
