package com.joberstein.numbers;
import static com.joberstein.numbers.NumberConstants.*;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * A class to print out a mathematical number in English words that is entered in by a user.
 * Limitations: Long values are the highest numbers accepted.
 * @author Jesse Oberstein
 * @version August 8th, 2015
 */
public class NumberPrinter {
	
	public static final int INTEGER_DIGIT_LIMIT = 15;
	public static final int DECIMAL_DIGIT_LIMIT = 6;
	public static final String COMMA_AND_LINE_BREAK = ",<br>";
	
	
	public String isValidInput(String input, int maxInputLength) {
		try {
			input = this.removeAllCommas(input.trim());
			
			if (maxInputLength == DECIMAL_DIGIT_LIMIT) {
				if (input.isEmpty() || (Long.valueOf(input) == 0)) {
					return "";
				}
			}
			else {
				if (input.isEmpty()) {
					return input;
				}
			}
			
			Long.parseLong(input);
			
			if (input.length() > maxInputLength) {
				throw new RuntimeException("Input number must be less than" + maxInputLength + "digits.");
			}
			
			return input;
		}
		catch (NumberFormatException e) {
			System.out.println("ERROR: Please enter a valid number.");
			return "NaN";
		}
		catch (RuntimeException e) {
			System.out.println("ERROR: Please input a number less than" + maxInputLength + "digits.");
			return "too long";
		}
	}
	
	public String formatInteger(String input) {
		if (input.isEmpty()) {
			return "0";
		}
		else {
			input = this.removeAllCommas(input);
			
			if (this.isNegative(input)) {
				String nonNegative = input.substring(1);
				return "-" + this.insertProperCommas(nonNegative);
			}
			else {
				return this.insertProperCommas(input);
			}
		}
	}
	
	public String formatDecimal(String input) {
		if (input.isEmpty() || (Long.valueOf(input) == 0)) {
			return "0";
		}
		else {
			return input;
		}
	}
	
	public String buildInteger(String number) {
		String result = "";
		int numLen = number.length();
		
		if (numLen <= 0) {
			return "";
		}
		
		if (Long.valueOf(number) == 0) {
			return capitalize(NumberConstants.ZERO.trim());
		}
		
		if (isNegative(number)) {
			result += NEGATIVE;
			number = number.substring(1);
		}
		
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
	
	public String buildDecimal(String number) {
		String result = "";
		int numLen = number.length();
		boolean isOne = (number.isEmpty()) ? false : (Long.valueOf(number) == 1);
		
		
		switch (numLen) {
			case 0: return "";
			case 1: result += parseOnesOrTens(number) + " "; 
					result += isOne ? TENTH : TENTHS;
				    break;
			case 2: result += parseOnesOrTens(number) + " ";
					result += isOne ? HUNDRETH : HUNDRETHS;
		    		break;
			case 3: result += parseHundred(number) + " ";
					result += isOne ? THOUSANDTH : THOUSANDTHS;
		    		break;
			case 4: result += parseThousand(number) + " ";
					result += isOne ? TEN_THOUSANDTH : TEN_THOUSANDTHS;
		    		break;
			case 5: result += parseThousand(number) + " ";
					result += isOne ? HUNDRED_THOUSANDTH : HUNDRED_THOUSANDTHS;
		    		break;
			case 6: result += parseThousand(number) + " ";
					result += isOne ? MILLIONTH : MILLIONTHS;
		    		break;
    		default: throw new RuntimeException("Decimal cannot be greater than 6 digits.");
		}
		
		return capitalize(removeTrailingComma(result.trim()));
	}
	
	private String removeAllCommas(String input) {
		return input.replaceAll(",", "");
	}
	
	private String insertProperCommas(String input) {
		for (int i = input.length(); i > 3; i -= 3) {
			input = input.substring(0, i - 3) + "," + input.substring(i - 3);
		}
		return input;
	}
	
	private boolean isNegative(String number) {
		long longNum = Long.parseLong(number);
		return longNum < 0;
	}
	
	private String capitalize(String result) {
		String capital = result.substring(0, 1).toUpperCase();
		return capital + result.substring(1);
	}
	
	private String removeTrailingComma(String string) {
		return string.endsWith(COMMA_AND_LINE_BREAK) ? 
				string.substring(0, string.length() - COMMA_AND_LINE_BREAK.length()) : 
				string;
	}
	
	private String parseTrillion(String number) {
		int trillionIndex = number.length() - 12;
		
		return parseBase(number.substring(0, trillionIndex))
				+ parseCorrectBlankSuffix(number, TRILLION) 
				+ parseBillion(number.substring(trillionIndex)).trim();
	}
	
	private String parseBillion(String number) {
		int billionIndex = number.length() - 9;
		
		return parseBase(number.substring(0, billionIndex))
			+ parseCorrectBlankSuffix(number, BILLION) 
			+ parseMillion(number.substring(billionIndex)).trim();
	}
	
	private String parseMillion(String number) {
		int millionIndex = number.length() - 6;
		
		return parseBase(number.substring(0, millionIndex))
				+ parseCorrectBlankSuffix(number, MILLION) 
				+ parseThousand(number.substring(millionIndex)).trim();
	}

	private String parseThousand(String number) {
		int thousandIndex = number.length() - 3;
		
		return parseBase(number.substring(0, thousandIndex)) 
				+ parseCorrectBlankSuffix(number, THOUSAND) 
				+ parseHundred(number.substring(thousandIndex)).trim();
	}
	
	private String parseHundred(String number) {
		int hundredsDigit = Integer.parseInt(number.substring(0, 1));
		
		return parseOnesOrTens(Integer.toString(hundredsDigit))
				+ parseCorrectBlankSuffix(number, HUNDRED)
				+ parseOnesOrTens(number.substring(1));
	}
	
	private String parseCorrectBlankSuffix(String number, String suffix) {
		int hundredsDigit = Integer.parseInt(number.substring(0, 1));
		int tensDigit = Integer.parseInt(number.substring(1, 2));
		int onesDigit = Integer.parseInt(number.substring(2, 3));
		
		if (onesDigit == 0 && tensDigit == 0 && hundredsDigit == 0) {
			return " ";
		}
		else if (suffix != HUNDRED) {
			return suffix + COMMA_AND_LINE_BREAK;
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
		int integer = Integer.parseInt(number);
		int firstDigit = Integer.parseInt(number.substring(0, 1));
		int secondDigit = -1;
		boolean isTens = number.length() == 2;
		
		if (isTens) {
			secondDigit = Integer.parseInt(number.substring(1, 2));
		}
		
		switch (firstDigit) {
			case 0: return isTens ? parseOnesOrTens(Integer.toString(secondDigit)) : "";
			case 1: return isTens ? parseTeens(integer) : ONE; 
			case 2: result += determineOnesOrTensBehavior(number, isTens, TWO, TWENTY);
					integer = determineOnesNumber(number, isTens, 2);
	 		 		break;
			case 3: result += determineOnesOrTensBehavior(number, isTens, THREE, THIRTY);
					integer = determineOnesNumber(number, isTens, 3);
			 		break;
			case 4: result += determineOnesOrTensBehavior(number, isTens, FOUR, FORTY);
					integer = determineOnesNumber(number, isTens, 4);
			 		break;
			case 5: result += determineOnesOrTensBehavior(number, isTens, FIVE, FIFTY);
					integer = determineOnesNumber(number, isTens, 5);
			 		break;
			case 6: result += determineOnesOrTensBehavior(number, isTens, SIX, SIXTY);
					integer = determineOnesNumber(number, isTens, 6);
			 		break;
			case 7: result += determineOnesOrTensBehavior(number, isTens, SEVEN, SEVENTY);
					integer = determineOnesNumber(number, isTens, 7);
			 		break;
			case 8: result += determineOnesOrTensBehavior(number, isTens, EIGHT, EIGHTY);
					integer = determineOnesNumber(number,isTens, 8);
			 		break;
			case 9: result += determineOnesOrTensBehavior(number, isTens, NINE, NINETY);
					integer = determineOnesNumber(number, isTens, 9);
					break;

		}
		
		if (isTens) {
			result += (secondDigit != 0) ? "-" : " ";
			result += parseOnesOrTens(Integer.toString(integer));
		}
		return result;
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
	
	
	
	
///////////////////////////////////////////////////////
/// MAIN METHOD TO RUN APPLICATION IN AN IDE.
///////////////////////////////////////////////////////
	
	public static void main(String args[]) {
		NumberPrinter np = new NumberPrinter();
		String input = np.parseInput(System.in);
		System.out.println("\nYour number in writing:\n\t" + np.buildInteger(input) + ".\n");
	}
	
	private String parseInput(InputStream in) {
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
				System.out.print("ERROR: Please enter a number.\n");
			}
			catch (NumberFormatException e) {
				System.out.println("ERROR: Please enter a whole number, or one "
						+ "that is less than 15 digits.");
			}
		}
		scan.close();
		return Long.toString(number);
	}
}
