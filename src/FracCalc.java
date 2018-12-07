import java.util.Scanner;
public class FracCalc {
    /**
     * Prompts user for input, passes that input to produceAnswer, then outputs the result.
     * @param args - unused
     */
    public static void main(String[] args) {
    	Scanner userInput = new Scanner(System.in);
    	String nextInput = ""; 
    	while(!nextInput.equals("quit")) { //Reads user input and calculates until reader types "quit"
    		nextInput = userInput.nextLine(); //reads readers next input
    		if(!nextInput.equals("quit")) { //calculate if the user input was not "quit"
    			System.out.println(produceAnswer(nextInput)); //passes input to produceAnswer method and prints answer calculated by produceAnswer method
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
    // Checkpoint 1: Return the second operand. Example "4/5 * 1_2/4" returns "1_2/4".
    // Checkpoint 2: Return the second operand as a string representing each part.
    //               Example "4/5 * 1_2/4" returns "whole:1 numerator:2 denominator:4".
    // Checkpoint 3: Evaluate the formula and return the result as a fraction.
    //               Example "4/5 * 1_2/4" returns "6/5".
    //               Note: Answer does not need to be reduced, but it must be correct.
    // Final project: All answers must be reduced.
    //               Example "4/5 * 1_2/4" returns "1_1/5".
    public static String produceAnswer(String input) { //calculates the fraction expression user enters
    	String firstFrac = input.substring(0, input.indexOf(" ")); //finds first fraction which ends before the first space 	
    	String cutInput = input.substring(input.indexOf(" ")+1); //forms a new String without the firstOperand     	
    	String operator = cutInput.substring(0, 1); //finds operator    	
    	String secondFrac = cutInput.substring(2); //finds second fraction which starts after the operator and space
    	//Two arrays to store the whole number (index 0), numerator (index 1), and denominator (index 2) of the fractions 
    	int[] parse1stFrac = new int[3];
    	int[] parse2ndFrac = new int[3];
    	parseFrac(firstFrac, parse1stFrac); 
    	parseFrac(secondFrac, parse2ndFrac);
        return stringFracVals(parse2ndFrac);
    }    
    public static void parseFrac(String frac, int[] parseFrac) { //parses fraction into 3 integer values and stores into array
    	if(!frac.contains("_")) { //checks if the String fraction contains a whole number
    		parseFrac[0] = 0; //no whole number
    	} else {
    		parseFrac[0] = Integer.parseInt(frac.substring(0, frac.indexOf("_"))); //sets index 0 of array fraction as the integer whole number
    	}	
    	if(!frac.contains("/")&& !frac.contains("_")) { //checks if the fraction is a whole number
    		parseFrac[0] = Integer.parseInt(frac);
    		parseFrac[1] = 0;
    		parseFrac[2] = 1;
    	} else if (frac.contains("_") && frac.contains("/")){ //if the fraction is not just a whole number
    		parseFrac[1] = Integer.parseInt(frac.substring(frac.indexOf("_")+1, frac.indexOf("/"))); //sets index 1 of array as numerator by checking between the "_" and "/" symbols
    		parseFrac[2] = Integer.parseInt(frac.substring(frac.indexOf("/")+1)); //sets index 2 of array fraction as the denominator
    	} else {
    		parseFrac[1] = Integer.parseInt(frac.substring(0, frac.indexOf("/"))); //sets index 1 of array as numerator by checking before the "/" symbol
    		parseFrac[2] = Integer.parseInt(frac.substring(frac.indexOf("/")+1)); //sets index 2 of array fraction as the denominator
    	} 
    }
    public static String stringFracVals(int[] frac) { //returns a string containing the whole, numerator, and denominator of a fraction
    	return "whole:" + frac[0] + " numerator:" + frac[1] + " denominator:" + frac[2]; //prints out the whole, numerator, and denominator values of a fraction
    }
    // TODO: Fill in the space below with helper methods
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
