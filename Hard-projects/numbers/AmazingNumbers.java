package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AmazingNumbers {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		StringBuilder input = new StringBuilder();
		List<String> request, numbers, properties;
		System.out.println("Welcome to Amazing Numbers!");
		printInstructions();
		while (true) {
			System.out.print("Enter a request: ");
			input.append(scanner.nextLine().trim().toUpperCase());
			if (input.length() == 0) {
				printInstructions();
				continue;
			}
			if (input.toString().equals("0")) {
				System.out.println("\nGoodbye!");
				break;
			}
			request = List.of(input.toString().split("\\s+"));
			if (request.size() > 2) {
				numbers = request.subList(0, 2);
				properties = request.subList(2, request.size());
				if (checkNumbers(numbers) && checkProperties(properties)) {
					printProperties(Long.parseLong(numbers.get(0)), Integer.parseInt(numbers.get(1)), properties);
				}
			} else if (request.size() > 1) {
				numbers = request.subList(0, 2);
				if (checkNumbers(numbers)) {
					printProperties(Long.parseLong(numbers.get(0)), Integer.parseInt(numbers.get(1)));
				}
			} else {
				numbers = request.subList(0, 1);
				if (checkNumbers(numbers)) {
					printProperties(Long.parseLong(numbers.get(0)));
				}
			}
			input.setLength(0);
		}
		scanner.close();
	}

	private static void printInstructions() {
		System.out.println();
		System.out.println("Supported requests:");
		System.out.println("- enter a natural number to know its properties;");
		System.out.println("- enter two natural numbers to obtain the properties of the list:");
		System.out.println("  * the first parameter represents a starting number;");
		System.out.println("  * the second parameter shows how many consecutive numbers are to be processed;");
		System.out.println("- two natural numbers and properties to search for;");
		System.out.println("- a property preceded by minus must not be present in numbers;");
		System.out.println("- separate the parameters with one space;");
		System.out.println("- enter 0 to exit.");
		System.out.println();
	}

	private static boolean checkNumbers(List<String> numbers) {
		if (numbers.size() > 1) {
			if (!numbers.get(0).matches("\\d+") && !numbers.get(1).matches("\\d+")) {
				System.out.println("\nBoth parameters should be a natural number or zero.\n");
			} else if (!numbers.get(0).matches("\\d+")) {
				System.out.println("\nThe first parameter should be a natural number or zero.\n");
			} else if (!numbers.get(1).matches("\\d+")) {
				System.out.println("\nThe second parameter should be a natural number.\n");
			} else {
				return true;
			}
			return false;
		} else {
			if (!numbers.get(0).matches("\\d+")) {
				System.out.println("\nThe first parameter should be a natural number or zero.\n");
			} else {
				return true;
			}
			return false;
		}
	}

	private static boolean checkProperties(List<String> properties) {
		List<String> wrongProperties = new ArrayList<String>();
		for (String property : properties) {
			try {
				Property.valueOf(property.replaceFirst("-", ""));
			} catch (IllegalArgumentException e) {
				wrongProperties.add(property.replaceFirst("-", ""));
			}
		}
		if (wrongProperties.isEmpty()) {
			return !isMutuallyExclusive(properties);
		} else if (wrongProperties.size() == 1) {
			System.out.printf("\nThe property %s is wrong.\n", wrongProperties.toString());
			System.out.printf("Available properties: %s\n\n", Arrays.toString(Property.values()));
			return false;
		} else {
			System.out.printf("\nThe properties %s are wrong.\n", wrongProperties.toString());
			System.out.printf("Available properties: %s\n\n", Arrays.toString(Property.values()));
			return false;
		}
	}

	private static boolean isMutuallyExclusive(List<String> properties) {
		for (String property : properties) {
			if (properties.contains("-".concat(property))) {
				System.out.printf("\nThe request contains mutually exclusive properties: [%s, %s]\n", property,
						"-".concat(property));
				System.out.println("There are no numbers with these properties.\n");
				return true;
			}
		}
		if (properties.contains("EVEN") && properties.contains("ODD")) {
			System.out.println("\nThe request contains mutually exclusive properties: [EVEN, ODD]");
			System.out.println("There are no numbers with these properties.\n");
		} else if (properties.contains("DUCK") && properties.contains("SPY")) {
			System.out.println("\nThe request contains mutually exclusive properties: [DUCK, SPY]");
			System.out.println("There are no numbers with these properties.\n");
		} else if (properties.contains("SQUARE") && properties.contains("SUNNY")) {
			System.out.println("\nThe request contains mutually exclusive properties: [SQUARE, SUNNY]");
			System.out.println("There are no numbers with these properties.\n");
		} else if (properties.contains("HAPPY") && properties.contains("SAD")) {
			System.out.println("\nThe request contains mutually exclusive properties: [HAPPY, SAD]");
			System.out.println("There are no numbers with these properties.\n");
		} else if (properties.contains("-EVEN") && properties.contains("-ODD")) {
			System.out.println("\nThe request contains mutually exclusive properties: [-EVEN, -ODD]");
			System.out.println("There are no numbers with these properties.\n");
		} else if (properties.contains("-DUCK") && properties.contains("-SPY")) {
			System.out.println("\nThe request contains mutually exclusive properties: [-DUCK, -SPY]");
			System.out.println("There are no numbers with these properties.\n");
		} else if (properties.contains("-HAPPY") && properties.contains("-SAD")) {
			System.out.println("\nThe request contains mutually exclusive properties: [-HAPPY, -SAD]");
			System.out.println("There are no numbers with these properties.\n");
		} else {
			return false;
		}
		return true;
	}

	private static void printProperties(long number) {
		System.out.println();
		System.out.printf("Properties of %,d\n", number);
		System.out.printf("%13s %b\n", "even:", NumberProperties.isEven(number));
		System.out.printf("%13s %b\n", "odd:", NumberProperties.isOdd(number));
		System.out.printf("%13s %b\n", "buzz:", NumberProperties.isBuzz(number));
		System.out.printf("%13s %b\n", "duck:", NumberProperties.isDuck(number));
		System.out.printf("%13s %b\n", "palindromic:", NumberProperties.isPalindromic(number));
		System.out.printf("%13s %b\n", "gapful:", NumberProperties.isGapful(number));
		System.out.printf("%13s %b\n", "spy:", NumberProperties.isSpy(number));
		System.out.printf("%13s %b\n", "square:", NumberProperties.isSquare(number));
		System.out.printf("%13s %b\n", "sunny:", NumberProperties.isSunny(number));
		System.out.printf("%13s %b\n", "jumping:", NumberProperties.isJumping(number));
		System.out.printf("%13s %b\n", "happy:", NumberProperties.isHappy(number));
		System.out.printf("%13s %b\n", "sad:", NumberProperties.isSad(number));
		System.out.println();
	}

	private static void printProperties(long number, int amt) {
		System.out.println();
		for (int i = 0; i < amt; i++) {
			propertiesList(number);
			number++;
		}
		System.out.println();
	}

	private static void printProperties(long number, int amt, List<String> properties) {
		System.out.println();
		while (amt > 0) {
			boolean check = true;
			for (String property : properties) {
				if (property.startsWith("-")) {
					if (checkNumberProperty(number, Property.valueOf(property.replaceFirst("-", "")))) {
						check = false;
						break;
					}
				} else {
					if (!checkNumberProperty(number, Property.valueOf(property))) {
						check = false;
						break;
					}
				}
			}
			if (check) {
				propertiesList(number);
				amt--;
			}
			number++;
		}
		System.out.println();
	}

	private static boolean checkNumberProperty(long number, Property property) {
		switch (property) {
		case EVEN:
			return NumberProperties.isEven(number);
		case ODD:
			return NumberProperties.isOdd(number);
		case BUZZ:
			return NumberProperties.isBuzz(number);
		case DUCK:
			return NumberProperties.isDuck(number);
		case PALINDROMIC:
			return NumberProperties.isPalindromic(number);
		case GAPFUL:
			return NumberProperties.isGapful(number);
		case SPY:
			return NumberProperties.isSpy(number);
		case SQUARE:
			return NumberProperties.isSquare(number);
		case SUNNY:
			return NumberProperties.isSunny(number);
		case JUMPING:
			return NumberProperties.isJumping(number);
		case HAPPY:
			return NumberProperties.isHappy(number);
		case SAD:
			return NumberProperties.isSad(number);
		default:
			return false;
		}
	}

	private static void propertiesList(long number) {
		List<String> propertiesList = new ArrayList<String>();
		if (NumberProperties.isEven(number)) {
			propertiesList.add("even");
		}
		if (NumberProperties.isOdd(number)) {
			propertiesList.add("odd");
		}
		if (NumberProperties.isBuzz(number)) {
			propertiesList.add("buzz");
		}
		if (NumberProperties.isDuck(number)) {
			propertiesList.add("duck");
		}
		if (NumberProperties.isPalindromic(number)) {
			propertiesList.add("palindromic");
		}
		if (NumberProperties.isGapful(number)) {
			propertiesList.add("gapful");
		}
		if (NumberProperties.isSpy(number)) {
			propertiesList.add("spy");
		}
		if (NumberProperties.isSquare(number)) {
			propertiesList.add("square");
		}
		if (NumberProperties.isSunny(number)) {
			propertiesList.add("sunny");
		}
		if (NumberProperties.isJumping(number)) {
			propertiesList.add("jumping");
		}
		if (NumberProperties.isHappy(number)) {
			propertiesList.add("happy");
		}
		if (NumberProperties.isSad(number)) {
			propertiesList.add("sad");
		}
		System.out.printf("%,16d is %s\n", number, propertiesList.toString().replaceAll("\\[|\\]", ""));
	}

	private static enum Property {
		EVEN, ODD, DUCK, SPY, SQUARE, SUNNY, HAPPY, SAD, BUZZ, PALINDROMIC, GAPFUL, JUMPING;
	}
}
