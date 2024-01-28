/*
 * @Author: Milo Osterman
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class BoggleGame {
	private HashSet<String> dictionary;
	private HashSet<String> duplicates;
	private int score;
	private DiceTray tray;
	private TreeSet<String> allWords;
	private TreeSet<String> foundWords;
	private TreeSet<String> incorrectWords;
	private int roboTally = 0;
	private TreeSet<String> missedWords;

	public BoggleGame() {
		populateDictionary();
		duplicates = new HashSet<>();
		score = 0;
		tray = new DiceTray();
		allWords = computerResults(tray);
		foundWords = new TreeSet<>();
		incorrectWords = new TreeSet<>();
		missedWords = new TreeSet<>();
	}
	
	public int getScore() {
		return score;
	}
	
	public TreeSet<String> getFound(){
		return foundWords;
	}
	
	public TreeSet<String> getIncorrect(){
		return incorrectWords;
	}
	
	public TreeSet<String> getMissed

	public void play(String[] guesses) {
		for (int i = 0; i < guesses.length; i++) {
			String guess = guesses[i].toLowerCase();
			if (tray.found(guess) && validWord(guess, allWords)) {
				foundWords.add(guess);
				duplicates.add(guess);
				updateScore(guess);
			} else {
				if (!duplicates.contains(guess)) {
					incorrectWords.add(guess);
				}
			}
		}

	}

	private void updateScore(String guess) {
		switch (guess.length()) {
		case 3:
			score++;
			break;
		case 4:
			score++;
			break;
		case 5:
			score += 2;
			break;
		case 6:
			score += 3;
			break;
		case 7:
			score += 5;
			break;
		default:
			score += 11;
		}

	}

	public String intro() {
		return "Play one game of Boggle:\n" + tray + "Enter words or ZZ to quit:\n";

	}

	private String[] getGuesses(Scanner sc) {
		String[] guesses = sc.nextLine().split(" ");
		return guesses;
	}

	public String results() {
		String res = "";
		res += "Your score: " + score + "\nWords you found:\n" + "================\n";
		int i = 0;
		for (String word : foundWords) {
			res += word + " ";
			if ((i + 1) % 10 == 0) {
				res += "\n";
			}
			i++;
		}
		i = 0;
		res += "\n\nIncorrect words:\n" + "================\n";
		for (String word : incorrectWords) {
			res += word + " ";
			if ((i + 1) % 10 == 0) {
				res += "\n";
			}
			i++;
		}
		i = 0;
		int roboTally = 0;
		TreeSet<String> wordsNotFound = new TreeSet<>();
		for (String word : allWords) {
			if (!foundWords.contains(word)) {
				wordsNotFound.add(word);
				roboTally++;
			}
		}
		res += "\n\nYou could have found " + roboTally + " more words.\n"
				+ "The computer found all of your words plus these:\n"
				+ "================================================\n";
		for (String word : wordsNotFound) {
			res += word + " ";
			if ((i + 1) % 10 == 0) {
				res += "\n";
			}
			i++;
		}

		return res;

	}

	private TreeSet<String> computerResults(DiceTray tray) {
		TreeSet<String> total = new TreeSet<>();
		for (String word : dictionary) {
			if (tray.found(word)) {
				total.add(word);
			}
		}
		return total;
	}

	private boolean validWord(String word, TreeSet<String> allWords) {
		if (!allWords.contains(word) || (word.length() < 3 || word.length() > 16) || duplicates.contains(word)) {
			return false;
		}
		return true;
	}

	private void populateDictionary() {
		HashSet<String> dictionary = new HashSet<>();
		File file = new File("/Users/milo/Desktop/UASemester4/CSC335/BoggleStart/BoggleWords.txt");
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				dictionary.add(sc.nextLine().toLowerCase());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.dictionary = dictionary;

	}

}
