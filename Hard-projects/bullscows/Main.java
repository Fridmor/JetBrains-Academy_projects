package bullscows;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

class CodeException extends RuntimeException {
	public CodeException(String message) {
		super("Error: " + message);
	}
}

public class Main {
	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			startGame();
		} catch (CodeException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void startGame() {
		System.out.println("Input the length of the secret code:");
		int codeLength = setCodeLength();
		System.out.println("Input the number of possible symbols in the code:");
		int charRange = setCharRange(codeLength);
		String code = generateCode(codeLength, charRange);
		System.out.println("Okay, let's start a game!");
		int turn = 0;
		StringBuilder answer = new StringBuilder();
		StringBuilder result = new StringBuilder();
		while (result.indexOf("Congratulations!") == -1) {
			System.out.printf("Turn %d:\n", ++turn);
			answer.append(getAnswer(codeLength));
			result.append(getResult(code, codeLength, answer.toString()));
			System.out.print(result);
			answer.setLength(0);
			result.setLength(0);
		}
	}

	private static int setCodeLength() {
		int codeLength;
		try {
			codeLength = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			throw new CodeException("\"" + sc.nextLine() + "\" isn't a valid number.");
		}
		if (codeLength <= 0)
			throw new CodeException("length of the code must be a natural number.");
		if (codeLength > 36)
			throw new CodeException("maximum number of possible symbols in the code is 36 (0-9, a-z).");
		return codeLength;
	}

	private static int setCharRange(int codeLength) {
		int charRange;
		try {
			charRange = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			throw new CodeException("\"" + sc.nextLine() + "\" isn't a valid number.");
		}
		if (charRange < codeLength) {
			throw new CodeException(
					String.format("it's not possible to generate a code with a length of %d with %d unique symbols.",
							codeLength, charRange));
		}
		if (charRange > 36) {
			throw new CodeException("maximum number of possible symbols in the code is 36 (0-9, a-z).");
		}
		return charRange;
	}

	private static String generateCode(int codeLength, int charRange) {
		if (charRange > 10) {
			char charBound = (char) (97 + charRange - 11);
			System.out.printf("The secret is prepared: " + "*".repeat(codeLength) + " (0-9, a-%c).\n", charBound);
		} else {
			int intBound = charRange - 1;
			System.out.printf("The secret is prepared: " + "*".repeat(codeLength) + " (0-%d).\n", intBound);
		}
		Random rnd = new Random();
		StringBuilder rndNumber = new StringBuilder();
		while (rndNumber.length() < codeLength) {
			int tempNum = rnd.nextInt(charRange);
			if (tempNum < 10) {
				if (rndNumber.indexOf(String.valueOf(tempNum)) == -1)
					rndNumber.append(tempNum);
			} else {
				char tempChar = (char) (97 + tempNum - 10);
				if (rndNumber.indexOf(String.valueOf(tempChar)) == -1)
					rndNumber.append(tempChar);
			}
		}
		return rndNumber.toString();
	}

	private static String getAnswer(int codeLength) {
		String answer = sc.nextLine();
		if (!answer.matches("[0-9a-z]+"))
			throw new CodeException("Incorrect input!");
		if (answer.length() != codeLength)
			throw new CodeException("Error: the length of the answer and the length of the code must be equal.");
		return answer;
	}

	private static String getResult(String code, int codeLength, String answer) {
		char[] codeToArray = code.toCharArray();
		char[] answerToArray = answer.toCharArray();
		int bulls = 0;
		int cows = 0;
		for (int i = 0; i < codeLength; i++) {
			if (answerToArray[i] == codeToArray[i]) {
				bulls++;
				continue;
			}
			for (int j = 0; j < codeLength; j++) {
				if (answerToArray[i] == codeToArray[j]) {
					cows++;
					break;
				}
			}
		}
		if (bulls > 0 && cows > 0) {
			if (bulls > 1 && cows > 1) {
				return String.format("Grade: %d bulls and %d cows\n", bulls, cows);
			} else if (bulls > 1) {
				return String.format("Grade: %d bulls and %d cow\n", bulls, cows);
			} else if (cows > 1) {
				return String.format("Grade: %d bull and %d cows\n", bulls, cows);
			} else {
				return String.format("Grade: %d bull and %d cow\n", bulls, cows);
			}
		} else if (bulls > 0) {
			if (bulls == codeLength) {
				return String.format("Grade: %d bulls\nCongratulations! You guessed the secret code.", bulls);
			} else if (bulls > 1) {
				return String.format("Grade: %d bulls\n", bulls);
			} else {
				return String.format("Grade: %d bull\n", bulls);
			}
		} else if (cows > 0) {
			if (cows > 1) {
				return String.format("Grade: %d cows\n", cows);
			} else {
				return String.format("Grade: %d cow\n", cows);
			}
		} else {
			return "Grade: None\n";
		}
	}
}
