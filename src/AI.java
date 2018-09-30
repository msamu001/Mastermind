import java.util.ArrayList;
import java.util.Random;

public class AI {
	private int[] code;
	private ArrayList<int[]> guessLog, keyLog, solutions;
	private Random rand;
	
	public AI() {
		solutions = initCodes();
		code = genCode();
		guessLog = new ArrayList<int[]>();
		keyLog = new ArrayList<int[]>();
	}
	
	public void addKey(int black, int white) {
		keyLog.add(new int[]{black, white});
	}
	
	public int[] calcKey(int[] userGuess) {
		return matchChk(code, userGuess);
	}
	
	public int[] calcGuess(int turnCount) {
		return null;
	}
	
	// Creates every code combination then returns as a list
	private ArrayList<int[]> initCodes() {
		ArrayList<int[]> temp = new ArrayList<>();
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 6; j++) {
				for(int k = 0; k < 6; k++) {
					for(int l = 0; l < 6; l++) {
						temp.add(new int[]{i,j,k,l});
					}
				}
			}
		}
		return temp;
	}
	
	private int[] genCode() {
		rand = new Random();
		return solutions.get(rand.nextInt(1296)-1);
	}
	
	private int[] matchChk(int[] crntCode, int[] prevGuess) {
		int[] keyChk = new int[2];
		char[][] match = new char[crntCode.length][prevGuess.length];
		for(int i = 0; i < crntCode.length; i++) {
			for(int j = 0; j < prevGuess.length; j++) {
				if(crntCode[i] == prevGuess[j]) {
					if(i == j) {
						match[i][j] = 'B'; // correct colour, correct location
					} else {
						match[i][j] = 'W'; // correct colour, wrong location
 					}
				}
			}
		}
		// Calculate number of black and white keys
		for(int i = 0; i < crntCode.length; i++) {
			if(match[i][i] == 'B') {
				keyChk[0]++;
			} else {
				for(int j = 0; j < crntCode.length; j++) {
					if(match[i][j] == 'W') {
						// Check for black key in column
						if((match[i][j] == 'B' && crntCode[i] == crntCode[j]) == false) {
							keyChk[1]++;
							break;
						}
					}
				}
			}
		}
		return keyChk;
	}
}
