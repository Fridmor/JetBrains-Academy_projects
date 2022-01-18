package cinema;

import java.util.Scanner;

public class CinemaRoomManager {
	private static final Scanner scanner = new Scanner(System.in);

	private static int ticketsPurchased;
	private static int ticketsTotal;
	private static int incomeCurrent;
	private static int incomeTotal;

	public static void main(String[] args) {
		System.out.println("Enter the number of rows:");
		int rowNum = scanner.nextInt();
		System.out.println("Enter the number of seats in each row:");
		int seatsNum = scanner.nextInt();
		System.out.println();

		ticketsTotal = rowNum * seatsNum;
		if (ticketsTotal <= 60) {
			incomeTotal = rowNum * seatsNum * 10;
		} else {
			if (rowNum % 2 == 0) {
				incomeTotal = rowNum / 2 * seatsNum * 10 + rowNum / 2 * seatsNum * 8;
			} else {
				incomeTotal = rowNum / 2 * seatsNum * 10 + (rowNum + 1) / 2 * seatsNum * 8;
			}
		}

		char[][] cinemaRoom = new char[rowNum][seatsNum];
		for (int i = 0; i < cinemaRoom.length; i++) {
			for (int j = 0; j < cinemaRoom[i].length; j++) {
				cinemaRoom[i][j] = 'S';
			}
		}

		menu(cinemaRoom);
	}

	private static void menu(char[][] cinemaRoom) {
		System.out.println("1. Show the seats");
		System.out.println("2. Buy a ticket");
		System.out.println("3. Statistics");
		System.out.println("0. Exit");
		int option = scanner.nextInt();
		System.out.println();
		switch (option) {
		case 1:
			showSeats(cinemaRoom);
			System.out.println();
			menu(cinemaRoom);
			break;
		case 2:
			buyTicket(cinemaRoom);
			System.out.println();
			menu(cinemaRoom);
			break;
		case 3:
			showStatistics();
			System.out.println();
			menu(cinemaRoom);
			break;
		case 0:
			return;
		default:
			System.out.println("Incorrect case!");
			System.out.println();
			menu(cinemaRoom);
		}
	}

	private static void showSeats(char[][] cinemaRoom) {
		System.out.println("Cinema:");
		System.out.print(" ");
		for (int i = 1; i <= cinemaRoom[0].length; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
		for (int i = 0; i < cinemaRoom.length; i++) {
			System.out.print(i + 1);
			for (int j = 0; j < cinemaRoom[i].length; j++) {
				System.out.print(" " + cinemaRoom[i][j]);
			}
			System.out.println();
		}
	}

	private static void buyTicket(char[][] cinemaRoom) {
		System.out.println("Enter a row number:");
		int row = scanner.nextInt();
		System.out.println("Enter a seat number in that row:");
		int seat = scanner.nextInt();

		if (row > cinemaRoom.length || seat > cinemaRoom[0].length) {
			System.out.println("Wrong input!");
			System.out.println();
			buyTicket(cinemaRoom);
			return;
		}

		if (cinemaRoom[row - 1][seat - 1] == 'B') {
			System.out.println("That ticket has already been purchased!");
			System.out.println();
			buyTicket(cinemaRoom);
			return;
		}

		cinemaRoom[row - 1][seat - 1] = 'B';
		ticketsPurchased++;

		if (ticketsTotal <= 60) {
			System.out.printf("Ticket price: $10");
			System.out.println();
			incomeCurrent += 10;
		} else {
			if (row <= cinemaRoom.length / 2) {
				System.out.printf("Ticket price: $10");
				System.out.println();
				incomeCurrent += 10;
			} else {
				System.out.printf("Ticket price: $8");
				System.out.println();
				incomeCurrent += 8;
			}
		}
		System.out.println();
	}

	private static void showStatistics() {
		double ticketsPercent = (double) ticketsPurchased / ticketsTotal * 100;
		System.out.printf("Number of purchased tickets: %d", ticketsPurchased);
		System.out.println();
		System.out.printf("Percentage: %.2f%%", ticketsPercent);
		System.out.println();
		System.out.printf("Current income: $%d", incomeCurrent);
		System.out.println();
		System.out.printf("Total income: $%d", incomeTotal);
		System.out.println();
	}
}
