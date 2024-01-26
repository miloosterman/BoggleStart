package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class BoggleGame {
	private static HashSet<String> DICTIONARY = new HashSet<>();
	private static HashSet<String> DUPLICATES = new HashSet<>();

	public static void main(String[] args) {
		populateDictionary();
		
		char[][] letters = { { 'N', 'O', 'Y', 'S' }, { 'H', 'T', 'N', 'T' }, { 'E', 'K', 'E', 'S' },
				{ 'T', 'C', 'E', 'S' } };
		int score = 0;
		String endCommand = "zz";
		String[] guesses;
		ArrayList<String> foundWords = new ArrayList<>();
		ArrayList<String> incorrectWords = new ArrayList<>();
		DiceTray tray = new DiceTray(letters);
		
		guesses = intro(tray);

		for (int i = 0; i < guesses.length; i++) {
			if (guesses[i].equals(endCommand)) {
				printResults(foundWords, incorrectWords, score);

			} else {
				if (tray.found(guesses[i]) && validWord(guesses[i])) {
					foundWords.add(guesses[i]);
					DUPLICATES.add(guesses[i]);
					score++;
				} else {
					if (!DUPLICATES.contains(guesses[i])) {
						incorrectWords.add(guesses[i]);
					}
				}
			}
		}
	}
	
	private static String[] intro(DiceTray tray) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Play one game of Boggle:\n");
		System.out.println(tray);
		System.out.println("Enter words or ZZ to quit:");
		String[] guesses = sc.nextLine().toLowerCase().split(" ");
		sc.close();
		return guesses;
	}

	private static void printResults(ArrayList<String> foundWords, ArrayList<String> incorrectWords, int score) {
		System.out.print("Your score: " + score + "\n" + "Words you found:\n" + "================\n");
		for (int j = 0; j < foundWords.size(); j++) {
			System.out.print(foundWords.get(j) + " ");
			if (j + 1 % 11 == 0) {
				System.out.print("\n");
			}
		}
		System.out.print("\n\nIncorrect words:\n" + "================\n");
		for (int k = 0; k < incorrectWords.size(); k++) {
			System.out.print(incorrectWords.get(k) + " ");
			if (k + 1 % 11 == 0) {
				System.out.print("\n");
			}
		}

	}

	private static boolean validWord(String word) {
		if (!DICTIONARY.contains(word) || (word.length() < 3 || word.length() > 16) || DUPLICATES.contains(word)) {
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
