package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class BoggleGame {
	private static HashSet<String> DICTIONARY = new HashSet<>();
	private static HashSet<String> DUPLICATES = new HashSet<>();
	private static int SCORE = 0;

	public static void main(String[] args) {
		populateDictionary();

		char[][] letters = { { 'N', 'O', 'Y', 'S' }, { 'H', 'T', 'N', 'T' }, { 'E', 'K', 'E', 'S' },
				{ 'T', 'C', 'E', 'S' } };
		String endCommand = "zz";
		String[] guesses;
		DiceTray tray = new DiceTray(letters);
		TreeSet<String> foundWords = new TreeSet<>();
		TreeSet<String> incorrectWords = new TreeSet<>();
		TreeSet<String> allWords = computerResults(tray);
		boolean endFlag = false;
		Scanner sc = new Scanner(System.in);

		intro(tray);
		while (!endFlag) {
			guesses = getGuesses(sc);
			for (int i = 0; i < guesses.length; i++) {
				if (guesses[i].equals(endCommand)) {
					endFlag = true;
					allWords = computerResults(tray);
					printResults(foundWords, incorrectWords, allWords);
					computerResults(tray);

				} else {
					if (tray.found(guesses[i]) && validWord(guesses[i], allWords)) {
						foundWords.add(guesses[i]);
						DUPLICATES.add(guesses[i]);
						updateScore(guesses[i]);
					} else {
						if (!DUPLICATES.contains(guesses[i])) {
							incorrectWords.add(guesses[i]);
						}
					}
				}
			}
		}
		sc.close();
	}

	private static void updateScore(String guess) {
		switch (guess.length()) {
		case 3:
			SCORE++;
			break;
		case 4:
			SCORE++;
			break;
		case 5:
			SCORE += 2;
			break;
		case 6:
			SCORE += 3;
			break;
		case 7:
			SCORE += 5;
			break;
		default:
			SCORE += 11;
		}

	}

	private static void intro(DiceTray tray) {
		System.out.println("Play one game of Boggle:\n");
		System.out.println(tray);
		System.out.println("Enter words or ZZ to quit:");
	}

	private static String[] getGuesses(Scanner sc) {
		String[] guesses = sc.nextLine().toLowerCase().split(" ");
		return guesses;
	}

	private static void printResults(TreeSet<String> foundWords, TreeSet<String> incorrectWords,
			TreeSet<String> allWords) {
		System.out.print("Your score: " + SCORE + "\n" + "Words you found:\n" + "================\n");
		int i = 0;
		for (String word : foundWords) {
			System.out.print(word + " ");
			if ((i + 1) % 10 == 0) {
				System.out.print("\n");
			}
			i++;
		}
		i = 0;
		System.out.print("\n\nIncorrect words:\n" + "================\n");
		for (String word : incorrectWords) {
			System.out.print(word + " ");
			if ((i + 1) % 10 == 0) {
				System.out.print("\n");
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
		System.out.print("\n\nYou could have found " + roboTally + " more words.\n"
				+ "The computer found all of your words plus these:\n"
				+ "================================================\n");
		for (String word : wordsNotFound) {
			System.out.print(word + " ");
			if ((i + 1) % 10 == 0) {
				System.out.print("\n");
			}
			i++;
		}

	}

	private static TreeSet<String> computerResults(DiceTray tray) {
		TreeSet<String> total = new TreeSet<>();
		for (String word : DICTIONARY) {
			if (tray.found(word)) {
				total.add(word);
			}
		}
		return total;
	}

	private static boolean validWord(String word, TreeSet<String> allWords) {
		if (!allWords.contains(word) || (word.length() < 3 || word.length() > 16) || DUPLICATES.contains(word)) {
			return false;
		}
		return true;
	}

	private static void populateDictionary() {
		File file = new File("/Users/milo/Desktop/UASemester4/CSC335/BoggleStart/BoggleWords.txt");
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				DICTIONARY.add(sc.nextLine().toLowerCase());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
