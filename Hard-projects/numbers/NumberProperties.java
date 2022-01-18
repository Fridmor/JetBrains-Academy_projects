package numbers;

final class NumberProperties {
	private NumberProperties() {
	}

	static boolean isEven(long number) {
		return number % 2 == 0;
	}

	static boolean isOdd(long number) {
		return number % 2 != 0;
	}

	static boolean isBuzz(long number) {
		return number % 7 == 0 || number % 10 == 7;
	}

	static boolean isDuck(long number) {
		while (number != 0) {
			if (number % 10 == 0) {
				return true;
			}
			number /= 10;
		}
		return false;
	}

	static boolean isPalindromic(long number) {
		long beforeReverse = number;
		long afterReverse = 0;
		while (number != 0) {
			afterReverse = afterReverse * 10 + number % 10;
			number /= 10;
		}
		return afterReverse == beforeReverse;
	}

	static boolean isGapful(long number) {
		String str = String.valueOf(number);
		return str.length() >= 3 && number % Integer.parseInt(str.charAt(0) + "" + str.charAt(str.length() - 1)) == 0;
	}

	static boolean isSpy(long number) {
		long sum = 0;
		long prod = 1;
		while (number != 0) {
			sum += number % 10;
			prod *= number % 10;
			number /= 10;
		}
		return sum == prod;
	}

	static boolean isSquare(long number) {
		return number % Math.sqrt(number) == 0;
	}

	static boolean isSunny(long number) {
		return isSquare(number + 1);
	}

	static boolean isJumping(long number) {
		String str = String.valueOf(number);
		for (int i = 0; i < str.length() - 1; i++) {
			if (Math.abs(str.charAt(i) - str.charAt(i + 1)) != 1) {
				return false;
			}
		}
		return true;
	}

	static boolean isHappy(long number) {
		StringBuilder sb = new StringBuilder(String.valueOf(number));
		while (true) {
			long sum = 0;
			for (int i = 0; i < sb.length(); i++) {
				int num = Integer.parseInt(sb.substring(i, i + 1));
				sum += num * num;
			}
			if (sum == 1)
				return true;
			if (sum == 4)
				return false;
			sb.replace(0, sb.length(), String.valueOf(sum));
		}
	}

	static boolean isSad(long number) {
		return !isHappy(number);
	}
}
