import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener{
	private int guessNum, codeSize, turnCount;
	private CodeBreaker ai;
	private Color[] colours;
	private JPanel topPanel, numPanel, keyPand, guessPanel, bottomPanel;
	private JButton[] viewColours;
	private JButton[][] guessPegs, keyPegs;
	private JButton next;
	private boolean userPlays, smartAI, gameOver;
	
	public GUI(int gn, int cs) {
		gameMode();
		ai = new CodeBreaker();
		guessNum = gn;
		codeSize = cs;
		turnCount = 0;
		colours = new Color[]{Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.pink};
		panelSetup();
		buttonSetup();
		windowSetup();
		setVisible(true);
	}
	
	private void gameMode() {
		Object[] options = {"Codebreaker", "Naive AI", "Smart AI"};
		int dialog = JOptionPane.showOptionDialog(
				null,
				"Select a game mode",
				"Mastermind",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[2]);
		if(dialog == JOptionPane.YES_OPTION) {
			userPlays = true;
		}
		if(dialog == JOptionPane.NO_OPTION) {
			smartAI = false;
		}
		if(dialog == JOptionPane.CANCEL_OPTION) {
			smartAI = true;
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
	}
	
	private void buttonSetup() {
		
	}
	
	private void windowSetup() {
		
	}
	
	private class guessColour implements ActionListener {
		public int colourChk;
		
		private guessColour() {
			colourChk = 0;
		}
		
		public void actionPerformed(ActionEvent e) {
			colourChk = (colourChk+1) % 6;
			((JButton)(e.getSource())).setBackground(colours[colourChk]);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
	}
}
