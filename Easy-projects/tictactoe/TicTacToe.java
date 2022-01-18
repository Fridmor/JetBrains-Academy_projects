package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		char[][] grid = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
		
		printGrid(grid);
		
		char turn = 'X';
		while (isPossible(grid)) {
			System.out.print("Enter the coordinates: ");
			try {
				int i = scanner.nextInt();
				int j = scanner.nextInt();
				if ((1 <= i && i <= 3) && (1 <= j && j <= 3)) {
					if (grid[--i][--j] == (' ')) {
						switch (turn) {
						case 'X':
							grid[i][j] = 'X';
							turn = 'O';
							break;
						case 'O':
							grid[i][j] = 'O';
							turn = 'X';
							break;
						}
						printGrid(grid);
					} else {
						System.out.println("This cell is occupied! Choose another one!");
					}
				} else {
					System.out.println("Coordinates should be from 1 to 3!");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nYou should enter numbers!\n");
				scanner.nextLine();
			}
		}
		
		System.out.println(findWinner(grid));
		
		scanner.close();
	}

	private static void printGrid(char[][] grid) {
		System.out.println("---------");
		System.out.printf("| %c %c %c |\n", grid[0][0], grid[0][1], grid[0][2]);
		System.out.printf("| %c %c %c |\n", grid[1][0], grid[1][1], grid[1][2]);
		System.out.printf("| %c %c %c |\n", grid[2][0], grid[2][1], grid[2][2]);
		System.out.println("---------");
	}

	private static String findWinner(char[][] grid) {
		boolean xWins = playerWins(grid, 'X');
		boolean oWins = playerWins(grid, 'O');
		if (xWins) {
			return "X wins";
		}
		if (oWins) {
			return "O wins";
		}
		return hasEmpty(grid) ? "Game not finished" : "Draw";
	}

	private static boolean playerWins(char[][] grid, char player) {
		/* check rows and columns */
		for (int i = 0; i < 3; i++) {
			if (grid[i][0] == player && grid[i][1] == player && grid[i][2] == player
					|| grid[0][i] == player && grid[1][i] == player && grid[2][i] == player) {
				return true;
			}
		}
		/* check diagonals */
		if (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player
				|| grid[0][2] == player && grid[1][1] == player && grid[2][0] == player) {
			return true;
		}
		return false;
	}

	private static boolean isPossible(char[][] grid) {
		if (!hasEmpty(grid)) {
			return false;
		}
		
		boolean xWins = playerWins(grid, 'X');
		boolean oWins = playerWins(grid, 'O');
		if (xWins || oWins) {
			return false;
		}
		
		int countX = 0;
		int countO = 0;
		for (char[] cells : grid) {
			for (char cell : cells) {
				if (cell == 'X') {
					countX++;
				}
				if (cell == 'O') {
					countO++;
				}
			}
		}
		
		return Math.abs(countX - countO) < 2;
	}

	private static boolean hasEmpty(char[][] grid) {
		for (char[] cells : grid) {
			for (char cell : cells) {
				if (cell == ' ') {
					return true;
				}
			}
		}
		return false;
	}
}
