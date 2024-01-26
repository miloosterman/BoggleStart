package model;

import java.util.Random;

/**
 * Model the tray of dice in the game Boggle. A DiceTray can 
 * be constructed with a 4x4 array of characters for testing.
 * 
 * A 2nd constructor with no arguments can be added later to
 * simulate the shaking of 16 dice and selection of one side.
 * 
 * @author Milo Osterman
 */

public class DiceTray {

  private char[][] path;
  private char[][] theBoard;
  public static final char TRIED = '@';
  public static final char PART_OF_WORD = '!';
  private String attempt;
  private int index;
  public static final int SIZE = 4;
  public static final int NUMBER_SIDES = 6;

  private static final char[][] DICE  = {
		  {'L', 'R', 'Y', 'T', 'T', 'E'},
		  {'V', 'T', 'H', 'R', 'W', 'E'},
		  {'E', 'G', 'H', 'W', 'N', 'E'},
		  {'S', 'E', 'O', 'T', 'I', 'S'},
		  {'A', 'N', 'A', 'E', 'E', 'G'},
		  {'I', 'D', 'S', 'Y', 'T', 'T'},
		  {'O', 'A', 'T', 'T', 'O', 'W'},
		  {'M', 'T', 'O', 'I', 'C', 'U'},
		  {'A', 'F', 'P', 'K', 'F', 'S'},
		  {'X', 'L', 'D', 'E', 'R', 'I'},
		  {'H', 'C', 'P', 'O', 'A', 'S'},
		  {'E', 'N', 'S', 'I', 'E', 'U'},
		  {'Y', 'L', 'D', 'E', 'V', 'R'},
		  {'Z', 'N', 'R', 'N', 'H', 'L'},
		  {'N', 'M', 'I', 'H', 'U', 'Q'},
		  {'O', 'B', 'B', 'A', 'O', 'J'}
		 };
  private static final int NUM_DICE = DICE.length;

  /*
   * Default constructor to populate board randomly with the 16 official dice
   */
  public DiceTray() {
	  theBoard = new char[SIZE][SIZE];
	  boolean[] visited = new boolean[NUM_DICE];
	  int randomDie;
	  char letterFromDie;
	  
	  for (int r = 0; r < SIZE; r++) {
	      for (int c = 0; c < SIZE; c++) {
	    	  randomDie = getRandomVal(NUM_DICE);
	    	  while (visited[randomDie]) {
	    		  randomDie = getRandomVal(NUM_DICE);
	    	  }
	    	  visited[randomDie] = true;
	    	  letterFromDie = DICE[randomDie][getRandomVal(NUMBER_SIDES)];
	    	  theBoard[r][c] = letterFromDie;
	      }
	  }
  }
  /**
   * Construct a tray of dice using a hard coded 2D array of chars. Use this for
   * testing
   * 
   * @param newBoard The 2D array of characters used in testing
   */
  public DiceTray(char[][] newBoard) {
    theBoard = newBoard;
  }

  /**
   * Return true if search is word that can found on the board following the rules
   * of Boggle like the same letter can only be used once.
   * 
   * @param str A word that may be in the board by connecting consecutive letters
   *
   * @return True if search is found
   */
  public boolean found(String str) {
    if (str.length() < 3)
      return false;
    attempt = str.toUpperCase().trim();
    boolean found = false;
    for (int r = 0; r < SIZE; r++) {
      for (int c = 0; c < SIZE; c++)
        if (theBoard[r][c] == attempt.charAt(0)) {
          init();
          found = recursiveSearch(r, c);
          if (found) {
            return true;
          }
        }
    }
    return found;
  }

  // Keep a 2nd 2D array to remember the characters that have been tried
  private void init() {
    path = new char[SIZE][SIZE];
    for (int r = 0; r < SIZE; r++)
      for (int c = 0; c < SIZE; c++)
        path[r][c] = '.';
    index = 0;
  }

  // This is like the Obstacle course escape algorithm.
  // Now we are checking 8 possible directions (no wraparound)
  private boolean recursiveSearch(int r, int c) {
    boolean found = false;

    if (valid(r, c)) { // valid returns true if in range AND one letter was found
      path[r][c] = TRIED;
      if (theBoard[r][c] == 'Q')
        index += 2;
      else
        index++;

      // Look in 8 directions for the next character
      if (index >= attempt.length())
        found = true;
      else {
        found = recursiveSearch(r - 1, c - 1);
        if (!found)
          found = recursiveSearch(r - 1, c);
        if (!found)
          found = recursiveSearch(r - 1, c + 1);
        if (!found)
          found = recursiveSearch(r, c - 1);
        if (!found)
          found = recursiveSearch(r, c + 1);
        if (!found)
          found = recursiveSearch(r + 1, c - 1);
        if (!found)
          found = recursiveSearch(r + 1, c);
        if (!found)
          found = recursiveSearch(r + 1, c + 1);
        // If still not found, allow backtracking to use the same letter in a
        // different location later as in looking for "BATTLING" in this board
        //
        // L T T X // Mark leftmost T as untried after it finds a 2nd T but not the L.
        // I X A X
        // N X X B
        // G X X X
        //
        if (!found) {
          path[r][c] = '.'; // Rick used . to mark the 2nd 2D array as TRIED
          index--; // 1 less letter was found. Let algorithm find the right first (col 2)
        }
      } // End recursive case

      if (found) {
        // Mark where the letter was found. Not required, but could be used to
        // show the actual path of the word that was found.
        path[r][c] = theBoard[r][c];
      }
    }
    return found;
  }
 
  // Determine if a current value of row and columns can or should be tried
  private boolean valid(int r, int c) {
    return r >= 0 && r < SIZE && c >= 0 && c < SIZE && path[r][c] != TRIED && theBoard[r][c] == attempt.charAt(index);
  }
  
  private int getRandomVal(int upper) {
	  Random random = new Random();
      return random.nextInt(upper);
  }
  
  @Override
  public String toString() {
	  String result = "";
	  for (int r = 0; r < SIZE; r++) {
	      for (int c = 0; c < SIZE; c++) {
	    	  result += theBoard[r][c];
		      if (c < SIZE - 1) {
		    	  result += "  ";
		      }
	      }

		  result += "\n\n";
	  }
	  return result;
  }
}