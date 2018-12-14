import java.util.Arrays;
import java.util.Scanner;
public class FracCalc {
    /**
     * Prompts user for input, passes that input to produceAnswer, then outputs the result.
     * @param args - unused
     */
    public static void main(String[] args) {
    	//System.out.println(greatestCommonDivisor(64, 24));
    	Scanner userInput = new Scanner(System.in); //accepts user input
    	String nextInput = ""; 
    	while(!nextInput.equals("quit")) { //Reads user input and calculates until reader types "quit"
    		nextInput = userInput.nextLine(); //reads readers next input
    		if(!nextInput.equals("quit")) { //calculate if the user input was not "quit"
    			System.out.println(produceAnswer(nextInput)); //prints answer calculated by produceAnswer method if it's a whole number
    		}
    	}
    }
    /**
     * produceAnswer - This function takes a String 'input' and produces the result.
     * @param input - A fraction string that needs to be evaluated.  For your program, this will be the user input.
     *      Example: input ==> "1/2 + 3/4"
     * @return the result of the fraction after it has been calculated.
     *      Example: return ==> "1_1/4"
     */
    public static String produceAnswer(String input) { //calculates the fraction expression user enters
    	String firstFrac = input.substring(0, input.indexOf(" ")); //finds first fraction which ends before the first space 	
    	String cutInput = input.substring(input.indexOf(" ")+1); //forms a new String without the firstOperand     	
    	String operator = cutInput.substring(0, 1); //finds operator    	
    	String secondFrac = cutInput.substring(2); //finds second fraction which starts after the operator and space
    	//Two arrays to store the whole number (index 0), numerator (index 1), and denominator (index 2) of the fractions 
    	if ((firstFrac.equals("0") || secondFrac.equals("0")) && (operator.equals("*") || operator.equals("/"))) {
    		return "0"; //returns 0 if dividing or multiplying 0
    	}
    	int[] parse1stFrac = new int[3]; //2 arrays to store the whole, numerator, and denominator of the fraction inputs at indexes
    	int[] parse2ndFrac = new int[3];
    	int[] answer = new int[3]; //Array to store the final answer
    	parseFrac(firstFrac, parse1stFrac);
    	parseFrac(secondFrac, parse2ndFrac);
        calculate(answer, parse1stFrac, parse2ndFrac, operator);
        if(returnStringAnswer(answer).contains("/")) { //if the answer String is not a whole number
        	reduceAnswer(answer); //reduces fraction
        	if(Math.abs(answer[1]) > Math.abs(answer[2])) { //if numerator is bigger than denominator
        		mixedFrac(answer); //converts it into mixed fraction
        	}
        } 
        return returnStringAnswer(answer);
    }    
    /**
     * parseFrac - parses fraction into 3 integer values and stores into array
     * @param frac - String value of the fraction
     * @param parseFrac - array of the fraction to store the integer values
     */
    public static void parseFrac(String frac, int[] parseFrac) { 
    	if(!frac.contains("_")) { //if the String fraction does not contain a whole number
    		parseFrac[0] = 0; //no whole number
    	} else {
    		parseFrac[0] = Integer.parseInt(frac.substring(0, frac.indexOf("_"))); //sets index 0 of array fraction as the integer whole number
    	}	
    	if(!frac.contains("/")&& !frac.contains("_")) { //if the fraction is a whole number
    		parseFrac[0] = Integer.parseInt(frac);
    		parseFrac[1] = 0;
    		parseFrac[2] = 1;
    	} else if (frac.contains("_") && frac.contains("/")){ //if the fraction a whole number and fraction
    		parseFrac[1] = Integer.parseInt(frac.substring(frac.indexOf("_")+1, frac.indexOf("/"))); //sets index 1 of array as numerator by checking between the "_" and "/" symbols
    		parseFrac[2] = Integer.parseInt(frac.substring(frac.indexOf("/")+1)); //sets index 2 of array fraction as the denominator
    		improperFrac(parseFrac);
    	} else { //if the fraction has no whole number
    		parseFrac[1] = Integer.parseInt(frac.substring(0, frac.indexOf("/"))); //sets index 1 of array as numerator by checking before the "/" symbol
    		parseFrac[2] = Integer.parseInt(frac.substring(frac.indexOf("/")+1)); //sets index 2 of array fraction as the denominator
    	} 
    	System.out.println(Arrays.toString(parseFrac)); //used to check where the code went wrong
    }
    /**
     * returnStringAnswer - returns the fraction answer as a String
     * @param answer - answer array containing the fraction answer
     * @return - a String of the fraction answer
     */
    public static String returnStringAnswer(int[] answer) { 
    	if(answer[0] !=0 && answer[1] == 0) { //whole number
    		return "" + answer[0];  
    	} else if (answer[0] ==0) { //no whole number
    		return answer[1] + "/" + answer[2];
    	} else { //mixed number
    		return answer[0] + "_" + answer[1] + "/" + answer[2];
    	}
    }
    /** 
     * improperFrac - converts a mixed fraction into an improper fraction
     * @param frac - array of the fraction
     */
    public static void improperFrac(int[] frac) {
    	if(frac[0] < 0) { //if the fraction is negative
    		frac[1] = frac[0]*frac[2] - frac[1]; //puts the whole number values into the numerator
        	frac[0] = 0;
    	} else { //if the fraction is positive
    		frac[1] = frac[1] + frac[0]*frac[2]; //puts the whole number values into the numerator
    		frac[0] = 0;
    	}
    }
    /**
     * calculate - Evaluates the user input and calculates the answer depending on the operator 
     * @param frac1 - first fraction array
     * @param frac2 - second fraction array
     * @param operator - plus, minus, multiply, or divide
     * @return a String of the fraction answer
     */
    public static void calculate(int[] answer, int[] frac1, int[] frac2, String operator) {
		if(operator.equals("+")) { //plus
			plus(answer, frac1, frac2);
    	} else if (operator.equals("-")) { //minus
    		minus(answer, frac1, frac2);
    	} else if (operator.equals("*")) { //multiply
    		multiply(answer, frac1, frac2);
    	} else { //divide
    		divide(answer, frac1, frac2);
    	}
		System.out.println("improper frac answer : " + returnStringAnswer(answer)); //to check where code went wrong
    }
    public static void plus(int[] answer, int[] frac1, int[] frac2) { 
    	if((frac1[2] == 1 && frac1[1] == 0) && (frac2[2]==1 && frac2[1] == 0)) { //if fractions are both whole numbers
			answer[0] = frac1[0] + frac2[0]; //stores answer in answer array
		} else { 
			if(frac1[2] == frac2[2]) { //if the denominators of both fractions are the same
				answer[2] = frac1[2];
				answer[1] = frac1[1] + frac2[1];
			} else { 
				answer[2] = frac1[2] * frac2[2]; 
				answer[1] = (frac1[1] * frac2[2]) + (frac2[1] * frac1[2]); 
			}
		}
    }
    public static void minus(int[] answer, int[] frac1, int[] frac2) {
    	if((frac1[2] == 1 && frac1[1] == 0) && (frac2[2]==1 && frac2[1] == 0)) { //if fractions are both whole numbers
			answer[0] = frac1[0] - frac2[0];
		} else {
			if(frac1[2] == frac2[2]) { //if the denominators of both fractions are the same
				answer[2] = frac1[2];
				answer[1] = frac1[1] - frac2[1];
			} else { 
				answer[2] = frac1[2] * frac2[2]; 
				answer[1] = (frac1[1] * frac2[2]) - (frac2[1] * frac1[2]); 
			}
		}
    }
    public static void multiply(int[] answer, int[] frac1, int[] frac2) {
    	if((frac1[2] == 1 && frac1[1] == 0) && (frac2[2]==1 && frac2[1] == 0)) { //if user input are both whole numbers
			answer[0] = frac1[0] * frac2[0];
    	} else if ((frac1[2] == 1 && frac1[1] == 0) && frac2[1]!= 0) { //if only first fraction is a whole number 
    		answer[1] = frac1[0] * frac2[1]; 
    		answer[2] = frac2[2];
    	} else if ((frac2[2] == 1 && frac2[1] == 0) && frac1[1]!=0) { //if only second fraction is a whole number 
    		answer[1] = frac2[0] * frac1[1]; 
    		answer[2] = frac1[2];
		} else {
			answer[1] = frac1[1] * frac2[1];
			answer[2] = frac1[2] * frac2[2];
		}
    }
    public static void divide(int[] answer, int[] frac1, int[] frac2) {
    	if((frac1[2] == 1 && frac1[1] == 0) && (frac2[2]==1 && frac2[1] == 0)) { //if fractions are both whole numbers
			answer[1] = frac1[0];
			answer[2] = frac2[0];
    	} else if ((frac1[2] == 1 && frac1[1] == 0) && frac2[1]!= 0) { //if only first fraction is a whole number 
    		answer[1] = frac2[1]; 
    		answer[2] = frac2[2] * frac1[0];
    	} else if ((frac2[2] == 1 && frac2[1] == 0) && frac1[1]!=0) { //if only second fraction is a whole number 
    		answer[1] = frac1[1]; 
    		answer[2] = frac1[2] * frac2[0];
		} else {
			answer[1] = frac1[1] * frac2[2];
			answer[2] = frac1[2] * frac2[1];
		}
    }    
    /**
     * reduceAnswer - reduces improper fraction to lowest terms 
     * @param answer array
     */
    public static void reduceAnswer(int[] answer) {
    	int divisor = greatestCommonDivisor(answer[1], answer[2]); 
    	answer[1] /= divisor; //reduces numerator and denominator by common divisor
    	answer[2] /= divisor;
    }
    /**
     * mixedFrac - converts improper fractions into whole number and simplified fraction 
     * @param answer array
     */
    public static void mixedFrac(int[] answer) {
    	int wholeNum = answer[1] / answer[2];
    	int numerator = answer[1] % answer[2]; 
    	System.out.println("whole num " + wholeNum);
    	System.out.println("num " + numerator);
    	if(numerator < 0 || answer[2] < 0) { //whole number becomes negative if any number is negative
    		answer[0] = Math.abs(wholeNum) * -1;
    	} else if (numerator < 0 && answer[2] < 0){ //positive whole number if both numerator and denominator have negative signs
    		answer[0] = Math.abs(wholeNum);
    	} else {
    		answer[0] = wholeNum;
    	}    		
    	answer[2] = Math.abs(answer[2]); //numerators and denominators are never be negative
    	answer[1] = Math.abs(numerator);
    }
    /**
     * greatestCommonDivisor - Find the largest integer that evenly divides two integers.
     *      Use this helper method in the Final Checkpoint to reduce fractions.
     *      Note: There is a different (recursive) implementation in BJP Chapter 12.
     * @param a - First integer.
     * @param b - Second integer.
     * @return The GCD.
     */
    public static int greatestCommonDivisor(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min != 0) {
            int tmp = min;
            min = max % min;
            max = tmp;
        }
        return max;
    } 
    /**
     * leastCommonMultiple - Find the smallest integer that can be evenly divided by two integers.
     *      Use this helper method in Checkpoint 3 to evaluate expressions.
     * @param a - First integer.
     * @param b - Second integer.
     * @return The LCM.
     */
    public static int leastCommonMultiple(int a, int b) {
        int gcd = greatestCommonDivisor(a, b);
        return (a*b)/gcd;
    }
}
