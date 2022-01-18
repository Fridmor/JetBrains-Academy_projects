package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		Player player1 = new Player();
		Player player2 = new Player();
		System.out.printf("Player %d, place your ships on the game field\n", player1.getId());
		System.out.println();
		printField(player1);
		placeShips(player1);
		clearScreen();
		System.out.printf("Player %d, place your ships on the game field\n", player2.getId());
		System.out.println();
		printField(player2);
		placeShips(player2);
		clearScreen();

		Player currPlayer = player1;
		Player currOpponent = player2;
		while (true) {
			System.out.println();
			printFieldWithFog(currOpponent);
			System.out.println("---------------------");
			printField(currPlayer);
			System.out.println();
				System.out.printf("Player %d, it's your turn:\n", currPlayer.getId());
			while (true) {
				try {
					shotShip(currOpponent);
					break;
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}
			clearScreen();
			Player temp = currPlayer;
			currPlayer = currOpponent;
			currOpponent = temp;
		}
	}

	private static void clearScreen() {
		System.out.println("Press Enter and pass the move to another player");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printField(Player player) {
		char[][] battlefield = player.getBattlefield();
		String separator = "  ";
		char character = 'A';
		for (int i = 0; i < battlefield.length; i++) {
			System.out.print(separator + (i + 1));
			separator = " ";
		}
		System.out.println();
		for (int i = 0; i < battlefield.length; i++) {
			System.out.print(character++);
			for (int j = 0; j < battlefield[i].length; j++) {
				System.out.print(separator + battlefield[i][j]);
			}
			System.out.println();
		}
	}

	private static void printFieldWithFog(Player player) {
		char[][] battlefield = player.getBattlefield();
		String separator = "  ";
		char character = 'A';
		for (int i = 0; i < battlefield.length; i++) {
			System.out.print(separator + (i + 1));
			separator = " ";
		}
		System.out.println();
		for (int i = 0; i < battlefield.length; i++) {
			System.out.print(character++);
			for (int j = 0; j < battlefield[i].length; j++) {
				if (battlefield[i][j] == 'O') {
					System.out.print(separator + '~');
				} else {
					System.out.print(separator + battlefield[i][j]);
				}
			}
			System.out.println();
		}
	}

	private static void placeShips(Player player) {
		System.out.println();
		for (Ship ship : player.getShips()) {
			System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getSize());
			while (true) {
				try {
					placeShip(player.getBattlefield(), ship);
					break;
				} catch (IllegalArgumentException e) {
					System.out.println("\n" + e.getMessage());
				}
			}
			System.out.println();
			printField(player);
			System.out.println();
		}
	}

	private static void placeShip(char[][] battlefield, Ship ship) {
		System.out.println();
		String coordinate1 = scanner.next();
		String coordinate2 = scanner.next();
		int row1 = coordinate1.charAt(0) - 65;
		int column1 = Integer.parseInt(coordinate1.substring(1)) - 1;
		int row2 = coordinate2.charAt(0) - 65;
		int column2 = Integer.parseInt(coordinate2.substring(1)) - 1;
		if (row1 != row2 && column1 != column2) {
			throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
		}
		if (row1 == row2) {
			if (Math.abs(column1 - column2) + 1 != ship.getSize()) {
				throw new IllegalArgumentException(
						String.format("Error! Wrong length of the %s! Try again:", ship.getName()));
			}
		}
		if (column1 == column2) {
			if (Math.abs(row1 - row2) + 1 != ship.getSize()) {
				throw new IllegalArgumentException(
						String.format("Error! Wrong length of the %s! Try again:", ship.getName()));
			}
		}
		if (!checkCellsIsEmpty(battlefield, row1, column1, row2, column2)) {
			throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
		}
		int startRow;
		int endRow;
		int startColumn;
		int endColumn;
		if (row1 < row2) {
			startRow = row1;
			endRow = row2;
		} else {
			startRow = row2;
			endRow = row1;
		}
		if (column1 < column2) {
			startColumn = column1;
			endColumn = column2;
		} else {
			startColumn = column2;
			endColumn = column1;
		}
		int[][] coordinates = ship.getCoordinates();
		int k = 0;
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startColumn; j <= endColumn; j++) {
				battlefield[i][j] = 'O';
				coordinates[k][0] = i;
				coordinates[k][1] = j;
				k++;
			}
		}
	}

	private static void shotShip(Player player) {
		System.out.println();
		String coordinate = scanner.next();
		System.out.println();
		char[][] battlefield = player.getBattlefield();
		int row = coordinate.charAt(0) - 65;
		int column = Integer.parseInt(coordinate.substring(1)) - 1;
		if ((-1 < row && row < battlefield.length) && (-1 < column && column < battlefield[0].length)) {
			if (battlefield[row][column] == 'O' || battlefield[row][column] == 'X') {
				battlefield[row][column] = 'X';
				if (checkBattlefieldIsEmpty(battlefield)) {
					System.out.print("You sank the last ship. You won. Congratulations!");
					System.exit(0);
				} else if (checkCellsIsEmpty(battlefield, row, column)) {
					System.out.print("You sank a ship!");
				} else {
					System.out.print("You hit a ship!");
				}
			} else {
				battlefield[row][column] = 'M';
				System.out.print("You missed.");
			}
		} else {
			throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
		}
		System.out.println();
	}

	private static boolean checkBattlefieldIsEmpty(char[][] battlefield) {
		for (int i = 0; i < battlefield.length; i++) {
			for (int j = 0; j < battlefield[i].length; j++) {
				if (battlefield[i][j] == 'O') {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean checkCellsIsEmpty(char[][] battlefield, int row1, int column1, int row2, int column2) {
		int startRow;
		int endRow;
		int startColumn;
		int endColumn;
		if (row1 < row2) {
			startRow = row1 == 0 ? row1 : row1 - 1;
			endRow = row2 == battlefield.length - 1 ? row2 : row2 + 1;
		} else {
			startRow = row2 == 0 ? row2 : row2 - 1;
			endRow = row1 == battlefield.length - 1 ? row1 : row1 + 1;
		}
		if (column1 < column2) {
			startColumn = column1 == 0 ? column1 : column1 - 1;
			endColumn = column2 == battlefield[0].length - 1 ? column2 : column2 + 1;
		} else {
			startColumn = column2 == 0 ? column2 : column2 - 1;
			endColumn = column1 == battlefield[0].length - 1 ? column1 : column1 + 1;
		}
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startColumn; j <= endColumn; j++) {
				if (battlefield[i][j] == 'O') {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean checkCellsIsEmpty(char[][] battlefield, int row, int column) {
		int startRow = row == 0 ? row : row - 1;
		int endRow = row == battlefield.length - 1 ? row : row + 1;
		int startColumn = column == 0 ? column : column - 1;
		int endColumn = column == battlefield[0].length - 1 ? column : column + 1;
		for (int i = startRow; i <= endRow; i++) {
			for (int j = startColumn; j <= endColumn; j++) {
				if (battlefield[i][j] == 'O') {
					return false;
				}
			}
		}
		return true;
	}
}
