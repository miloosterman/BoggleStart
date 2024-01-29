package view_controller;

import java.util.Arrays;
import java.util.Scanner;

import model.BoggleGame;

public class BoggleConsole {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BoggleGame game = new BoggleGame();
		System.out.println(game.intro());
		String[] guesses;
		boolean endFlag = false;
		while (!endFlag) {
			guesses = sc.nextLine().split(" ");
			for (int i = 0; i < guesses.length; i++) {
				if (guesses[i].equals("ZZ")) {
					endFlag = true;
					guesses = Arrays.copyOfRange(guesses, 0, i);
				}
			}
			game.play(guesses);
		}
		System.out.println(game.results());
		sc.close();
	}

}