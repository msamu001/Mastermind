import javax.swing.*;

public class Mastermind {
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	GUI game = new GUI(10,4);
}
