package game;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class UIController extends JFrame implements ActionListener, KeyListener {

	/**
	 *  Default Serial Version
	 */
	private static final long serialVersionUID = 1L;


	private JPanel contentPane;
	private JPanel gamePane;
	private JPanel startPane;
	private JPanel gameOverPane;
	private JPanel loginPane;
	private JPanel boardPane;
	private JPanel scorePane;

	private CardLayout contentCardLayout;

	private ArrayList<JLabel> scores;
	 
	private ArrayList<JButton> respawnButtons;

	private JComboBox<Integer> comboBoxNumberOfPlayers;

	private JLabel[] invalidLoginDetails;
	private JTextField[] usernames;
	private JPasswordField[] passwords;


	public UIController() {

		this.setTitle("Multiplayer Snake");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(950, 755);
		this.setResizable(false);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.contentPane);
		
		addKeyListener(this);

		this.contentCardLayout = new CardLayout(0, 0);

		this.contentPane.setLayout(this.contentCardLayout);

		this.contentPane.add(this.createStartPane(), "startPane");

		this.contentCardLayout.show(this.contentPane, "startPane");

		this.contentPane.add(this.createGameOverPane(), "gameOver");

		JButton btnQuit = new JButton("Quit");
		btnQuit.setActionCommand("Quit");
		btnQuit.addActionListener(this);
		gameOverPane.add(btnQuit, BorderLayout.SOUTH);

		this.setVisible(true);

	}

	private JPanel createStartPane() {

		this.startPane = new JPanel();
		GridBagLayout gbl_startPane = new GridBagLayout();
		gbl_startPane.columnWidths = new int[]{0, 0, 0};
		gbl_startPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_startPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_startPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		this.startPane.setLayout(gbl_startPane);

		JLabel labelTitle = new JLabel("Multiplayer Snake");
		labelTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 36));
		GridBagConstraints gbc_labelTitle = new GridBagConstraints();
		gbc_labelTitle.gridwidth = 2;
		gbc_labelTitle.insets = new Insets(0, 0, 5, 0);
		gbc_labelTitle.gridx = 0;
		gbc_labelTitle.gridy = 0;
		this.startPane.add(labelTitle, gbc_labelTitle);

		JLabel labelNumberOfPlayers = new JLabel("Select the number of players:");
		GridBagConstraints gbc_labelNumberOfPlayers = new GridBagConstraints();
		gbc_labelNumberOfPlayers.anchor = GridBagConstraints.EAST;
		gbc_labelNumberOfPlayers.insets = new Insets(0, 0, 5, 5);
		gbc_labelNumberOfPlayers.gridx = 0;
		gbc_labelNumberOfPlayers.gridy = 1;
		this.startPane.add(labelNumberOfPlayers, gbc_labelNumberOfPlayers);

		this.comboBoxNumberOfPlayers = new JComboBox<Integer>();
		this.comboBoxNumberOfPlayers.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4}));
		this.comboBoxNumberOfPlayers.setSelectedIndex(0);
		GridBagConstraints gbc_comboBoxNumberOfPlayers = new GridBagConstraints();
		gbc_comboBoxNumberOfPlayers.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxNumberOfPlayers.anchor = GridBagConstraints.WEST;
		gbc_comboBoxNumberOfPlayers.gridx = 1;
		gbc_comboBoxNumberOfPlayers.gridy = 1;
		this.startPane.add(this.comboBoxNumberOfPlayers, gbc_comboBoxNumberOfPlayers);

		JButton btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridwidth = 2;
		gbc_btnStart.insets = new Insets(0, 0, 0, 5);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 2;
		btnStart.addActionListener(this);
		btnStart.setActionCommand("Start");
		this.startPane.add(btnStart, gbc_btnStart);
		return this.startPane;

	}

	private JPanel createLoginPane() {

		this.loginPane = new JPanel();
		GridBagLayout gbl_loginPane = new GridBagLayout();
		gbl_loginPane.columnWidths = new int[]{0, 0, 0};
		gbl_loginPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_loginPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_loginPane.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		this.loginPane.setLayout(gbl_loginPane);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 36));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.gridwidth = 2;
		gbc_lblLogin.insets = new Insets(0, 0, 5, 0);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 0;
		this.loginPane.add(lblLogin, gbc_lblLogin);

		for (int i = 0; i < Game.numberOfPlayers; i++) {

			JPanel playerLoginPane = createPlayerLoginPane(i);
			GridBagConstraints gbc_playerLoginPane = new GridBagConstraints();
			gbc_playerLoginPane.insets = new Insets(0, 0, 0, 5);
			int x = i % 2 == 0 ? 0 : 1;
			int y = i < 2 ? 1 : 2;
			gbc_playerLoginPane.gridx = x;
			gbc_playerLoginPane.gridy = y;
			this.loginPane.add(playerLoginPane, gbc_playerLoginPane);

		}

		JButton btnLogin = new JButton("Login");
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogin.anchor = GridBagConstraints.NORTH;
		gbc_btnLogin.gridwidth = 2;
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 5;
		btnLogin.addActionListener(this);
		btnLogin.setActionCommand("Login");
		this.loginPane.add(btnLogin, gbc_btnLogin);

		return this.loginPane;

	}

	private JPanel createPlayerLoginPane(int player) {

		JPanel playerLoginPane = new JPanel();
		GridBagLayout gbl_playerLoginPane = new GridBagLayout();
		gbl_playerLoginPane.columnWidths = new int[]{0};
		gbl_playerLoginPane.rowHeights = new int[]{0};
		gbl_playerLoginPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_playerLoginPane.rowWeights = new double[]{Double.MIN_VALUE};
		playerLoginPane.setLayout(gbl_playerLoginPane);

		JLabel lblPlayer = new JLabel("Player " + (player+1));
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.gridwidth = 2;
		gbc_lblPlayer.insets = new Insets(50, 0, 5, 0);
		gbc_lblPlayer.gridx = 0;
		gbc_lblPlayer.gridy = 0;
		playerLoginPane.add(lblPlayer, gbc_lblPlayer);

		this.invalidLoginDetails[player] = new JLabel("Invalid username or password");
		this.invalidLoginDetails[player].setForeground(Color.RED);
		this.invalidLoginDetails[player].setVisible(false);
		GridBagConstraints gbc_lblInvalidLogin = new GridBagConstraints();
		gbc_lblInvalidLogin.gridwidth = 2;
		gbc_lblInvalidLogin.insets = new Insets(0, 0, 5, 0);
		gbc_lblInvalidLogin.gridx = 0;
		gbc_lblInvalidLogin.gridy = 1;
		playerLoginPane.add(this.invalidLoginDetails[player], gbc_lblInvalidLogin);

		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 2;
		playerLoginPane.add(lblUsername, gbc_lblUsername);

		this.usernames[player] = new JTextField();
		GridBagConstraints gbc_textFieldUsername = new GridBagConstraints();
		gbc_textFieldUsername.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldUsername.anchor = GridBagConstraints.WEST;
		gbc_textFieldUsername.gridx = 1;
		gbc_textFieldUsername.gridy = 2;
		this.usernames[player].setColumns(10);
		this.usernames[player].setText("user" + (player + 1));
		playerLoginPane.add(this.usernames[player], gbc_textFieldUsername);

		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		playerLoginPane.add(lblPassword, gbc_lblPassword);

		this.passwords[player] = new JPasswordField();
		GridBagConstraints gbc_textFieldPassword = new GridBagConstraints();
		gbc_textFieldPassword.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPassword.anchor = GridBagConstraints.WEST;
		gbc_textFieldPassword.gridx = 1;
		gbc_textFieldPassword.gridy = 3;
		this.passwords[player].setColumns(10);
		this.passwords[player].setText("password" + (player + 1));
		playerLoginPane.add(this.passwords[player], gbc_textFieldPassword);

		return playerLoginPane;
	}

	private JPanel createGameOverPane() {

		this.gameOverPane = new JPanel();
		this.gameOverPane.setLayout(new BorderLayout(0, 0));
		JLabel gameOverLabel = new JLabel("Game Over!");
		gameOverLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 52));
		gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.gameOverPane.add(gameOverLabel, BorderLayout.CENTER);
		return this.gameOverPane;

	}

	private JPanel createGamePane() {

		this.gamePane = new JPanel();
		this.gamePane.setLayout(new BorderLayout(0, 0));

		this.gamePane.add(createScorePane(), BorderLayout.WEST);

		this.gamePane.add(createBoardPane(), BorderLayout.CENTER);

		return this.gamePane;
	}

	private JPanel createScorePane() {

		this.scores = new ArrayList<JLabel>();
		this.respawnButtons = new ArrayList<JButton>();

		this.scorePane = new JPanel();
		this.scorePane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		GridBagLayout gbl_scorePane = new GridBagLayout();
		gbl_scorePane.columnWidths = new int[]{0, 0, 0};
		gbl_scorePane.rowHeights = new int[]{0, 0, 0};
		gbl_scorePane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_scorePane.rowWeights = new double[]{0.0, 0.0, 0.0};
		this.scorePane.setLayout(gbl_scorePane);


		JLabel labelScores = new JLabel("Scores");
		labelScores.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		GridBagConstraints gbc_labelScores = new GridBagConstraints();
		gbc_labelScores.anchor = GridBagConstraints.NORTH;
		gbc_labelScores.insets = new Insets(0, 0, 5, 0);
		gbc_labelScores.gridwidth = 2;
		gbc_labelScores.gridx = 0;
		gbc_labelScores.gridy = 0;
		this.scorePane.add(labelScores, gbc_labelScores);

		for (HumanPlayer player : Game.humanPlayers.values()) {

			JLabel labelPlayerScore = new JLabel(player.getName() + ": " + player.getScore());
			JButton btnRespawn = new JButton("Respawn");
			GridBagConstraints gbc_labelPlayerScore = new GridBagConstraints();
			GridBagConstraints gbc_btnRespawn = new GridBagConstraints();
			gbc_labelPlayerScore.insets = new Insets(0, 10, 0, 10);
			gbc_btnRespawn.insets = new Insets(0, 10, 0, 10);
			if (player.getPlayerID() == Game.numberOfPlayers) {
				gbc_labelPlayerScore.weighty = 1.0;
				gbc_btnRespawn.weighty = 1.0;
				gbc_labelPlayerScore.anchor = GridBagConstraints.NORTH;
				gbc_btnRespawn.anchor = GridBagConstraints.NORTH;
			}
			gbc_labelPlayerScore.gridx = 0;
			gbc_btnRespawn.gridx = 1;
			gbc_labelPlayerScore.gridy = player.getPlayerID() + 1;
			gbc_btnRespawn.gridy = player.getPlayerID() + 1;
			labelPlayerScore.setForeground(Game.COLORS[player.getPlayerID()]);
			this.scorePane.add(labelPlayerScore, gbc_labelPlayerScore);
			btnRespawn.addActionListener(this);
			btnRespawn.setActionCommand("Respawn" + player.getPlayerID());
			btnRespawn.setEnabled(false);
			this.respawnButtons.add(btnRespawn);
			this.scorePane.add(btnRespawn, gbc_btnRespawn);
			this.scores.add(labelPlayerScore);

		}
		
		JLabel[] keys = {
				new JLabel("user1: arrow keys"),
				new JLabel("user2: w,a,s,d keys"),
				new JLabel("user3: t,f,g,h keys"),
				new JLabel("user4: i,j,k,l keys")
		};
		
		for (int i = 0; i < Game.numberOfPlayers; i++) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.gridwidth = 2;
			gbc.gridx = 0;
			gbc.gridy = i + 5;
			gbc.anchor = GridBagConstraints.SOUTH;
			keys[i].setForeground(Game.COLORS[i+1]);
			this.scorePane.add(keys[i], gbc);
		}
		
		JButton btnSimSnakes = new JButton("Add 100 Simulated Snakes");
		GridBagConstraints gbc_btnSimSnakes = new GridBagConstraints();
		gbc_btnSimSnakes.insets = new Insets(0, 0, 5, 0);
		gbc_btnSimSnakes.gridwidth = 2;
		gbc_btnSimSnakes.gridx = 0;
		gbc_btnSimSnakes.gridy = Game.numberOfPlayers * 2 + 2;
		btnSimSnakes.addActionListener(this);
		btnSimSnakes.setActionCommand("AddSimSnakes");
		this.scorePane.add(btnSimSnakes, gbc_btnSimSnakes);
		
		return this.scorePane;
	}

	private JPanel createBoardPane() {

		this.boardPane = new JPanel();
		this.boardPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.boardPane.setLayout(new GridLayout(Game.board.getCols(), Game.board.getRows(), 1, 1));

		this.updateGameBoard();

		return this.boardPane;
	}

	public void showGameBoard() {
		if (!Game.humanPlayers.isEmpty()) {
			// All users logged
			Game.gameStarted = true;

			// Check if all players have successfully logged in
			for (int i = 1; i <= Game.numberOfPlayers; i++) {

				if (!Game.humanPlayers.containsKey(i) || !Game.humanPlayers.get(i).getClient().isAuthenticated()) {
					Game.gameStarted = false;
				}
			}

			// Show game pane if all have logged in
			if (Game.gameStarted) {
				this.contentPane.add(this.createGamePane(), "gamePane");
				this.contentCardLayout.show(this.contentPane, "gamePane");

				// Add KeyListener to game panel and make it the focus of the window for key events
				gamePane.addKeyListener(this);
				gamePane.requestFocusInWindow();
				Game.initGame();
			}
		}
	}

	public void setInvalidLoginDetails(int playerID, boolean valid) {
		this.invalidLoginDetails[playerID - 1].setVisible(!valid);
		this.usernames[playerID - 1].setEnabled(!valid);
		this.passwords[playerID - 1].setEnabled(!valid);
	}
	
	public void setRespawnButtonEnabled(int id, boolean value) {
		this.respawnButtons.get(id - 1).setEnabled(value);
	}

	private void updateScores() {
		for (int i = 1; i <= Game.numberOfPlayers; i++) {
			if (Game.humanPlayers.containsKey(i)) {
				Font f = this.scores.get(i-1).getFont();
				Map attributes = f.getAttributes();
				
				attributes.put(TextAttribute.STRIKETHROUGH, false);
				this.scores.get(i - 1).setFont(new Font(attributes));
				this.scores.get(i - 1).setText(Game.humanPlayers.get(i).getName() + ": " + Game.humanPlayers.get(i).getScore());
			} else {
				Font f = this.scores.get(i-1).getFont();
				Map attributes = f.getAttributes();
				attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
				this.scores.get(i - 1).setFont(new Font(attributes));
			}
		}
	}

	private void updateGameBoard() {

		this.boardPane.removeAll();
		this.boardPane.revalidate();

		for (int i = 0; i < Game.board.getRows() * Game.board.getCols(); i++) {
			this.boardPane.add(Game.board.getCell(i).getPanel());
		}

	}

	public void update() {
		this.updateScores();
		this.updateGameBoard();
	}

	public void gameOver() {
		this.contentCardLayout.show(contentPane, "gameOver");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// If start button was clicked
		if (e.getActionCommand().equals("Start")) {
			// Number of players
			Game.numberOfPlayers = (int) this.comboBoxNumberOfPlayers.getSelectedItem();
			// Initialize arrays
			this.invalidLoginDetails = new JLabel[Game.numberOfPlayers];
			this.usernames = new JTextField[Game.numberOfPlayers];
			this.passwords = new JPasswordField[Game.numberOfPlayers];
			// Create login pane
			this.contentPane.add(this.createLoginPane(), "loginPane");

			// Show login pane
			this.contentCardLayout.show(this.contentPane, "loginPane");

			// Login button clicked
		} else if (e.getActionCommand().equals("Login")) {

			// Reset all invalid details label
			for (JLabel lbl : this.invalidLoginDetails) {
				lbl.setVisible(false);
			}

			// Get the usernames and passwords from the textfields
			for (int i = 0; i < Game.numberOfPlayers; i++) {

				// Create new Client thread
				if (usernames[i].isEnabled()) {
					Game.createClient(i + 1, this.usernames[i].getText(), new String(this.passwords[i].getPassword()));
				}

			}

		} else if (e.getActionCommand().equals("Respawn1")) {
			gamePane.requestFocusInWindow();
			Game.clients.get(1).createPlayer();
			this.setRespawnButtonEnabled(1, false);
		} else if (e.getActionCommand().equals("Respawn2")) {
			gamePane.requestFocusInWindow();
			Game.clients.get(2).createPlayer();
			this.setRespawnButtonEnabled(2, false);
		} else if (e.getActionCommand().equals("Respawn3")) {
			gamePane.requestFocusInWindow();
			Game.clients.get(3).createPlayer();
			this.setRespawnButtonEnabled(3, false);
		} else if (e.getActionCommand().equals("Respawn4")) {
			gamePane.requestFocusInWindow();
			Game.clients.get(4).createPlayer();
			this.setRespawnButtonEnabled(4, false);
		} else if (e.getActionCommand().equals("AddSimSnakes")) {
			gamePane.requestFocusInWindow();
			Game.createSimulatedPlayers();
			((JButton)e.getSource()).setEnabled(false);
		} else if (e.getActionCommand().equals("Quit")) {
			System.exit(0);
		}
		
		

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (Game.humanPlayers.containsKey(1)) {
			if (key == KeyEvent.VK_UP) Game.humanPlayers.get(1).addMove(Snake.Direction.UP);
			else if (key == KeyEvent.VK_DOWN) Game.humanPlayers.get(1).addMove(Snake.Direction.DOWN);
			else if (key == KeyEvent.VK_LEFT) Game.humanPlayers.get(1).addMove(Snake.Direction.LEFT);
			else if (key == KeyEvent.VK_RIGHT) Game.humanPlayers.get(1).addMove(Snake.Direction.RIGHT);
		}

		if (Game.humanPlayers.containsKey(2)) {
			if (key == KeyEvent.VK_W) Game.humanPlayers.get(2).addMove(Snake.Direction.UP);
			else if (key == KeyEvent.VK_S) Game.humanPlayers.get(2).addMove(Snake.Direction.DOWN);
			else if (key == KeyEvent.VK_A) Game.humanPlayers.get(2).addMove(Snake.Direction.LEFT);
			else if (key == KeyEvent.VK_D) Game.humanPlayers.get(2).addMove(Snake.Direction.RIGHT);
		}

		if (Game.humanPlayers.containsKey(3)) {
			if (key == KeyEvent.VK_T) Game.humanPlayers.get(3).addMove(Snake.Direction.UP);
			else if (key == KeyEvent.VK_G) Game.humanPlayers.get(3).addMove(Snake.Direction.DOWN);
			else if (key == KeyEvent.VK_F) Game.humanPlayers.get(3).addMove(Snake.Direction.LEFT);
			else if (key == KeyEvent.VK_H) Game.humanPlayers.get(3).addMove(Snake.Direction.RIGHT);
		}

		if (Game.humanPlayers.containsKey(4)) {
			if (key == KeyEvent.VK_I) Game.humanPlayers.get(4).addMove(Snake.Direction.UP);
			else if (key == KeyEvent.VK_K) Game.humanPlayers.get(4).addMove(Snake.Direction.DOWN);
			else if (key == KeyEvent.VK_J) Game.humanPlayers.get(4).addMove(Snake.Direction.LEFT);
			else if (key == KeyEvent.VK_L) Game.humanPlayers.get(4).addMove(Snake.Direction.RIGHT);
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
