package machine;

import java.util.Scanner;

public class CoffeeMachine {
	private static final Scanner scanner = new Scanner(System.in);

	private static int waterAmountTotal = 400;
	private static int milkAmountTotal = 540;
	private static int beansNumTotal = 120;
	private static int cupsNumTotal = 9;
	private static int moneyAmountTotal = 550;

	private static CoffeeMachineState currentState = CoffeeMachineState.SELECT_ACTION;

	public static void main(String[] args) {
		while (currentState != CoffeeMachineState.EXIT) {
			coffeeMachineMenu(currentState);
		}
	}

	private static void coffeeMachineMenu(CoffeeMachineState currentState) {
		switch (currentState) {
		case SELECT_ACTION:
			selectAction();
			break;
		case BUY:
			buy();
			break;
		case FILL:
			fill();
			break;
		case TAKE:
			take();
			break;
		case REMAINING:
			remaining();
			break;
		case EXIT:
			break;
		default:
			System.out.println("Wrong action!");
		}
	}

	private static void selectAction() {
		System.out.println("Write action (buy, fill, take, remaining, exit):");
		String action = scanner.next();
		try {
		currentState = CoffeeMachineState.valueOf(action.toUpperCase());
		} catch (IllegalArgumentException e) {
			System.out.println("Wrong action!\n");
		}
	}

	private static void buy() {
		System.out.println();
		System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
		String option = scanner.next();
		switch (option) {
		case "1":
			selectCoffeeType(CoffeeType.ESPRESSO);
			System.out.println();
			currentState = CoffeeMachineState.SELECT_ACTION;
			break;
		case "2":
			selectCoffeeType(CoffeeType.LATTE);
			System.out.println();
			currentState = CoffeeMachineState.SELECT_ACTION;
			break;
		case "3":
			selectCoffeeType(CoffeeType.CAPUCCINO);
			System.out.println();
			currentState = CoffeeMachineState.SELECT_ACTION;
			break;
		case "back":
			currentState = CoffeeMachineState.SELECT_ACTION;
			System.out.println();
			break;
		default:
			System.out.println("Wrong option!");
		}
	}

	private static void selectCoffeeType(CoffeeType coffeeType) {
		boolean waterEnough = waterAmountTotal - coffeeType.getWaterAmountForCup() > 0;
		boolean milkEnough = milkAmountTotal - coffeeType.getMilkAmountForCup() > 0;
		boolean beansEnough = beansNumTotal - coffeeType.getBeansNumForCup() > 0;
		boolean cupsEnough = cupsNumTotal - 1 > 0;

		if (waterEnough && milkEnough && beansEnough && cupsEnough) {
			waterAmountTotal -= coffeeType.getWaterAmountForCup();
			milkAmountTotal -= coffeeType.getMilkAmountForCup();
			beansNumTotal -= coffeeType.getBeansNumForCup();
			cupsNumTotal -= 1;
			moneyAmountTotal += coffeeType.getMoneyAmountForCup();
			System.out.println("I have enough resources, making you a coffee!");
		} else {
			if (!waterEnough) {
				System.out.println("Sorry, not enough water!");
			}
			if (!milkEnough) {
				System.out.println("Sorry, not enough milk!");
			}
			if (!beansEnough) {
				System.out.println("Sorry, not enough coffee beans!");
			}
			if (!cupsEnough) {
				System.out.println("Sorry, not enough cups!");
			}
		}
	}

	private static void fill() {
		System.out.println();
		System.out.println("Write how many ml of water you want to add:");
		waterAmountTotal += scanner.nextInt();
		System.out.println("Write how many ml of milk you want to add:");
		milkAmountTotal += scanner.nextInt();
		System.out.println("Write how many grams of coffee beans you want to add:");
		beansNumTotal += scanner.nextInt();
		System.out.println("Write how many disposable cups of coffee you want to add:");
		cupsNumTotal += scanner.nextInt();
		System.out.println();
		currentState = CoffeeMachineState.SELECT_ACTION;
	}

	private static void take() {
		System.out.println();
		System.out.printf("I gave you $%d\n", moneyAmountTotal);
		moneyAmountTotal = 0;
		System.out.println();
		currentState = CoffeeMachineState.SELECT_ACTION;
	}

	private static void remaining() {
		System.out.println();
		printState();
		System.out.println();
		currentState = CoffeeMachineState.SELECT_ACTION;
	}

	private static void printState() {
		System.out.println("The coffee machine has:");
		System.out.printf("%d ml of water\n", waterAmountTotal);
		System.out.printf("%d ml of milk\n", milkAmountTotal);
		System.out.printf("%d g of coffee beans\n", beansNumTotal);
		System.out.printf("%d disposable cups\n", cupsNumTotal);
		System.out.printf("$%d of money\n", moneyAmountTotal);
	}

	private static enum CoffeeMachineState {
		SELECT_ACTION, BUY, FILL, TAKE, REMAINING, EXIT;
	}

	private static enum CoffeeType {
		ESPRESSO(250, 0, 16, 4), LATTE(350, 75, 20, 7), CAPUCCINO(200, 100, 12, 6);

		private final int waterAmountForCup;
		private final int milkAmountForCup;
		private final int beansNumForCup;
		private final int moneyAmountForCup;

		CoffeeType(int waterAmountForCup, int milkAmountForCup, int beansNumForCup, int moneyAmountForCup) {
			this.waterAmountForCup = waterAmountForCup;
			this.milkAmountForCup = milkAmountForCup;
			this.beansNumForCup = beansNumForCup;
			this.moneyAmountForCup = moneyAmountForCup;
		}

		public int getWaterAmountForCup() {
			return waterAmountForCup;
		}

		public int getMilkAmountForCup() {
			return milkAmountForCup;
		}

		public int getBeansNumForCup() {
			return beansNumForCup;
		}

		public int getMoneyAmountForCup() {
			return moneyAmountForCup;
		}
	}
}
