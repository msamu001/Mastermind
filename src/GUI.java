import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener{
	private int guessNum, codeSize, turnCount;
	private AI ai;
	private Color[] colours;
	private JPanel topPanel, numPanel, keyPanel, guessPanel, bottomPanel;
	private JButton[] viewColours;
	private JButton[][] guessPegs, keyPegs;
	private JButton next;
	private boolean userPlays, gameOver;
	private String title;
	
	public GUI(int gn, int cs) {
		gameMode();
		ai = new AI();
		guessNum = gn;
		codeSize = cs;
		turnCount = 0;
		colours = new Color[]{Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.pink};
		title = "Mastermind";
		panelSetup();
		buttonSetup();
		windowSetup();
		setVisible(true);
	}
	
	// Allows user to choose which game mode to play
	private void gameMode() {
		Object[] options = {"Codebreaker", "Mastermind"}; // Option names
		
		// Options box
		int dialog = JOptionPane.showOptionDialog(
				null,
				"Select a game mode",
				"Mastermind",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[1]);
		
		//  User plays as the Decoder
		if(dialog == JOptionPane.YES_OPTION) {
			userPlays = true;
		}
		
		// User plays as the Mastermind
		if(dialog == JOptionPane.NO_OPTION) {
			userPlays = false;
		}
	}
	
	private void restartGame() {
		int dialog = JOptionPane.showConfirmDialog(
				null,
				"Would you like to play again?",
				"Play again?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if(dialog == JOptionPane.YES_OPTION) {
			dispose();
			GUI gui = new GUI(guessNum, codeSize);
		}
		if(dialog == JOptionPane.NO_OPTION) {
			dispose();
		}
	}
	
	private void panelSetup() {
		// Initialize panels
		topPanel = new JPanel();
		numPanel = new JPanel();
		keyPanel = new JPanel();
		guessPanel  = new JPanel();
		bottomPanel = new JPanel();
		
		// Button layout 
		topPanel.setLayout(new FlowLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(20,28,0,12));
		topPanel.setVisible(true);		
		numPanel.setLayout(new GridLayout(guessNum,1,0,10));
		numPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		numPanel.setVisible(true);		
		keyPanel.setLayout(new GridLayout(guessNum,codeSize,0,10));
		keyPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,40));
		keyPanel.setPreferredSize(new Dimension(160,400));
		keyPanel.setVisible(true);		
		guessPanel.setLayout(new GridLayout(guessNum,codeSize,0,10));
		guessPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		guessPanel.setVisible(true);		
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(2,20,15,20));
		bottomPanel.setVisible(true);
	}
	
	private void buttonSetup() {
		// Initialize buttons
		viewColours = new JButton[6];
		guessPegs = new JButton[guessNum][codeSize];
		keyPegs = new JButton[guessNum][codeSize];
		next = new JButton("Start");
		
		next.addActionListener(this); // Changes button from "Start" to "next"
		bottomPanel.add(next); // Adds button to the bottom of the window
		
		// Set colour of buttons
		for(int i = 0; i < 6; i++) {
			viewColours[i] = new JButton();
			viewColours[i].setBackground(colours[i]);
			viewColours[i].setEnabled(true);
			topPanel.add(viewColours[i]);
		}
		
		// button layout & functionality
		for(int i = 0; i < guessNum; i++) {
			numPanel.add(new JLabel(String.valueOf(i+1)), JLabel.CENTER);
			for(int j = 0; j < codeSize; j++) {
				guessPegs[i][j] = new JButton();
				if(userPlays) {
					guessPegs[i][j].addActionListener(new guessColour());
					if(i > 0) {
						guessPegs[i][j].setEnabled(false);
					} else {
						guessPegs[i][j].setBackground(colours[0]);
					}
				}
				guessPanel.add(guessPegs[i][j]);
				
				keyPegs[i][j] = new JButton();
				if(!userPlays) {
					keyPegs[i][j].addActionListener(new keyColour());
				}
				keyPegs[i][j] = new JButton();
				keyPanel.add(keyPegs[i][j]);
			}
		}
	}
	
	private void windowSetup() {
		setLayout(new BorderLayout());
		setTitle(title);
		setDefaultCloseOperation(3);
		setMinimumSize(new Dimension(380, 500));
		add(topPanel, "North");
		add(numPanel, "West");
		add(guessPanel, "Center");
		add(keyPanel, "East");
		add(bottomPanel, "South");
	}
	
	private class guessColour implements ActionListener {
		public int colourChk;
		
		private guessColour() {
			colourChk = 0;
		}
		
		public void actionPerformed(ActionEvent e) {
			colourChk = (colourChk+1) % 6; // Increments each click. Cycles through 6 colours
			((JButton)(e.getSource())).setBackground(colours[colourChk]);
		}
	}
	
	private class keyColour implements ActionListener {
		public int colourChk;
		private Color[] colourSlct;
		
		private keyColour() {
			colourChk = 0;
			colourSlct = new Color[]{Color.lightGray, Color.black, Color.white};
		}
		
		public void actionPerformed(ActionEvent e) {
			colourChk = (colourChk+1) % 3; // Increments each click. Cycles through 3 colours
			((JButton)(e.getSource())).setBackground(colourSlct[colourChk]);
		}
	}
	
	// Game funtions
	public void actionPerformed(ActionEvent e) {
		int[] key, userGuess, aiGuess;
		int bPeg;
		int wPeg;
		
		// Ends game
		if(turnCount == 10 && !userPlays) {
			gameOver = true;
		}
		
		// Restarts game
		if(gameOver) {
			restartGame();
		}
		
		// Change start button to next
		if(turnCount == 0) {
			next.setText("Next");
		}
		
		// 
		if(userPlays) {
			userGuess = new int[codeSize];
			for(int i = 0; i < codeSize; i++) {
				guessPegs[turnCount][i].setEnabled(false); // stops previous codes being changed
				// allows next code to be changed
				if(turnCount < 9) {
					guessPegs[turnCount+1][i].setBackground(colours[0]);
					guessPegs[turnCount+1][i].setEnabled(true);
				}
				// saves user's guess
				for(int j = 0; j < colours.length; j++) {
					if(guessPegs[turnCount][i].getBackground().equals(colours[j])) {
						userGuess[i] = j;
					}
				}
			}
			key = ai.calcKey(userGuess);
			// Adds black pegs
			for(int j = 0; j < key.length; j++) {
				keyPegs[turnCount][j].setBackground(Color.black);
			}
			// Adds white pegs
			for(int j = 0; j < key.length; j++) {
				keyPegs[turnCount][j].setBackground(Color.white);
			}
			// End game if user wins
			if(key[0] == 4) {
				JOptionPane.showConfirmDialog(
						null,
						"You Win",
						"Mastermind",
						JOptionPane.YES_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				restartGame();
			}
			// Ends game if user is out of attemps
			if(turnCount == 9) {
				JOptionPane.showConfirmDialog(
						null,
						"You Lose",
						"Mastermind",
						JOptionPane.YES_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				restartGame();
			}
		} else {
			bPeg = 0;
			wPeg = 0;
			for(int i = 0; i < codeSize; i++) {
				aiGuess = ai.calcGuess(turnCount);
				guessPegs[turnCount][i].setBackground(colours[aiGuess[i]]);
				keyPegs[turnCount][i].setBackground(Color.lightGray);
				keyPegs[turnCount][i].setEnabled(true);
				
				if(turnCount > 0) {
					if(keyPegs[turnCount-1][i].getBackground().equals(Color.black)); {
						bPeg++;
					}
					if(keyPegs[turnCount-1][i].getBackground().equals(Color.white)); {
						wPeg++;
					}
					keyPegs[turnCount-1][i].setEnabled(true);
					ai.addKey(bPeg, wPeg);
				}
			}
		}
		turnCount++;
	}
}
