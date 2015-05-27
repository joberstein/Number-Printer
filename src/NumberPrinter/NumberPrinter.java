package NumberPrinter;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import static NumberPrinter.NumberConstants.*;

/*
 * A class to print out a mathematical number in English words that is entered in by a user.
 * Limitations: Long values are the highest numbers accepted.
 * 				Can only print out whole numbers.
 * @author Jesse Oberstein
 * @version May 26th, 2015
 */
public class NumberPrinter {
	
	public static void main(String args[]) {
		NumberPrinter np = new NumberPrinter();
		String input = np.parseInput(System.in);
		System.out.println("\nYour number in writing:\n\t" + np.printNumber(input) + ".\n");
	}
	
	public String parseInput(InputStream in) {
		Scanner scan;
		long number = 0;
		while (true) {
			try {
				scan = new Scanner(in);
				System.out.print("Please enter a whole number: ");
				number = scan.nextLong();
				break;
			}
			catch (InputMismatchException e) {
				System.out.print("ERROR: It appears you have either not entered a number, your number is not whole, "
						+ "or your number may be too long.\n\n");
			}
		}
		scan.close();
		return Long.toString(number);
	}
	
	public String printNumber(String number) {
		String result = "";
		
		if (isNegative(number)) {
			result += NEGATIVE;
			number = number.substring(1);
		}
		int numLen = number.length();
		
		if (numLen > 12) {
			result += parseTrillion(number);
		}
		else if (numLen > 9) {
			result += parseBillion(number);
		}
		else if (numLen > 6) {
			result += parseMillion(number);
		}
		else if (numLen > 3) {
			result += parseThousand(number);
		}
		else {
			result += parseBase(number);
		}
		return capitalize(removeTrailingComma(result.trim()));
	}
	
	private boolean isNegative(String number) {
		long longNum = Long.parseLong(number);
		return longNum < 0;
	}
	
	private String capitalize(String result) {
		String capital = result.substring(0, 1).toUpperCase();
		return capital + result.substring(1);
	}
	
	private String removeTrailingComma(String result) {
		if (result.endsWith(",")) {
			  return result.substring(0, result.length() - 1);
		}
		else {
			return result;
		}
	}
	
	private String parseTrillion(String number) {
		String result = "";
		int trillionIndex = number.length() - 12;
		result += parseBase(number.substring(0, trillionIndex));
		result += parseCorrectBlankSuffix(number, TRILLION) + parseBillion(number.substring(trillionIndex)).trim();
		return result;
	}
	
	private String parseBillion(String number) {
		String result = "";
		int billionIndex = number.length() - 9;
		result += parseBase(number.substring(0, billionIndex));
		result += parseCorrectBlankSuffix(number, BILLION) + parseMillion(number.substring(billionIndex)).trim();
		return result;
	}
	
	private String parseMillion(String number) {
		String result = "";
		int millionIndex = number.length() - 6;
		result += parseBase(number.substring(0, millionIndex));
		result += parseCorrectBlankSuffix(number, MILLION) + parseThousand(number.substring(millionIndex)).trim();
		return result;
	}

	private String parseThousand(String number) {
		String result = "";
		int thousandIndex = number.length() - 3;
		result += parseBase(number.substring(0, thousandIndex));
		result += parseCorrectBlankSuffix(number, THOUSAND) + parseHundred(number.substring(thousandIndex)).trim();
		return result;
	}
	
	private String parseHundred(String number) {
		String result = "";
		int hundredsDigit = Integer.parseInt(number.substring(0, 1));
		result += parseOnesOrTens(Integer.toString(hundredsDigit));
		result += parseCorrectBlankSuffix(number, HUNDRED) + parseOnesOrTens(number.substring(1));
		return result;
	}
	
	private String parseCorrectBlankSuffix(String number, String suffix) {
		int hundredsDigit = Integer.parseInt(number.substring(0, 1));
		int tensDigit = Integer.parseInt(number.substring(1, 2));
		int onesDigit = Integer.parseInt(number.substring(2, 3));
		
		if (onesDigit == 0 && tensDigit == 0 && hundredsDigit == 0) {
			return " ";
		}
		else if (suffix != HUNDRED) {
			return suffix + ",\n\t";
		}
		else {
			if (hundredsDigit == 0) {
				return " ";
			}
			else if (onesDigit == 0 && tensDigit == 0) {
				return HUNDRED + " ";
			}
			else {
				return HUNDRED + " and ";
			}
		}
	}
	
	private String parseBase(String number) {
		int numLen = number.length();
		if (numLen == 3) {
			return parseHundred(number);
		}
		else {
			return parseOnesOrTens(number);
		}
	}

	private String parseOnesOrTens(String number) {
		String result = "";
		int integer = Integer.parseInt(number),
			firstDigit = integer,
			secondDigit = integer;
		boolean isTwoDigits = isTwoDigits(number);
		
		if (isTwoDigits) {
			firstDigit = Integer.parseInt(number.substring(0, 1));
			secondDigit = Integer.parseInt(number.substring(1, 2));
		}
		
		switch (firstDigit) {
			case 0: return isTwoDigits ? parseOnesOrTens(Integer.toString(secondDigit)) : "";
			case 1: return isTwoDigits ? parseTeens(integer) : ONE; 
			case 2: result += determineOnesOrTensBehavior(number, isTwoDigits, TWO, TWENTY);
					integer = determineOnesNumber(number, isTwoDigits, 2);
	 		 		break;
			case 3: result += determineOnesOrTensBehavior(number, isTwoDigits, THREE, THIRTY);
					integer = determineOnesNumber(number, isTwoDigits, 3);
			 		break;
			case 4: result += determineOnesOrTensBehavior(number, isTwoDigits, FOUR, FORTY);
					integer = determineOnesNumber(number, isTwoDigits, 4);
			 		break;
			case 5: result += determineOnesOrTensBehavior(number, isTwoDigits, FIVE, FIFTY);
					integer = determineOnesNumber(number, isTwoDigits, 5);
			 		break;
			case 6: result += determineOnesOrTensBehavior(number, isTwoDigits, SIX, SIXTY);
					integer = determineOnesNumber(number, isTwoDigits, 6);
			 		break;
			case 7: result += determineOnesOrTensBehavior(number, isTwoDigits, SEVEN, SEVENTY);
					integer = determineOnesNumber(number, isTwoDigits, 7);
			 		break;
			case 8: result += determineOnesOrTensBehavior(number, isTwoDigits, EIGHT, EIGHTY);
					integer = determineOnesNumber(number,isTwoDigits, 8);
			 		break;
			case 9: result += determineOnesOrTensBehavior(number, isTwoDigits, NINE, NINETY);
					integer = determineOnesNumber(number, isTwoDigits, 9);
					break;
			default: throw new RuntimeException("Cannot parse given ones or tens digit.");
		}
		
		if (isTwoDigits) {
			result += parseOnesOrTens(Integer.toString(integer));
		}
		return result;
	}
	
	private boolean isTwoDigits(String number) {
		return number.length() == 2;
	}
	
	private String determineOnesOrTensBehavior(String number, boolean isTwoDigits, String ones, String tens) {
		return isTwoDigits ? tens : ones;
	}
	
	private int determineOnesNumber(String number, boolean isTwoDigits, int caseNumber) {
		int integer = Integer.parseInt(number);
		return isTwoDigits ? (integer - (10 * caseNumber)) : integer;
	}
	
	private String parseTeens(int number) {
		switch (number) {
			case 10: return TEN;
			case 11: return ELEVEN;
			case 12: return TWELVE;
			case 13: return THIRTEEN;
			case 14: return FOURTEEN;
			case 15: return FIFTEEN;
			case 16: return SIXTEEN;
			case 17: return SEVENTEEN;
			case 18: return EIGHTEEN;
			case 19: return NINETEEN;
			default: throw new RuntimeException("Cannot parse given teen value.");
		}
	}
}
