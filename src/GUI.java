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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener{
	private int guessNum, codeSize, turnCount;
	private CodeBreaker ai;
	private Color[] colours;
	private JPanel topPanel, numPanel, keyPanel, guessPanel, bottomPanel;
	private JButton[] viewColours;
	private JButton[][] guessPegs, keyPegs;
	private JButton next;
	private boolean userPlays, gameOver;
	private String title;
	
	public GUI(int gn, int cs) {
		gameMode();
		ai = new CodeBreaker();
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
	
	private void gameMode() {
		Object[] options = {"Codebreaker", "Mastermind"};
		int dialog = JOptionPane.showOptionDialog(
				null,
				"Select a game mode",
				"Mastermind",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[1]);
		if(dialog == JOptionPane.YES_OPTION) {
			userPlays = true;
		}
		if(dialog == JOptionPane.NO_OPTION) {
			userPlays = false;
		}
		if(dialog == JOptionPane.CANCEL_OPTION) {
			dispose();
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
		topPanel = new JPanel();
		numPanel = new JPanel();
		keyPanel = new JPanel();
		guessPanel  = new JPanel();
		bottomPanel = new JPanel();
		
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
		
	}
	
	private void windowSetup() {
		setLayout(new BorderLayout());
		setTitle(title);
		setDefaultCloseOperation(3);
		setMinimumSize(new Dimension(350, 500));
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
