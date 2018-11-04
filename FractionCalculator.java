import java.io.File;
import java.lang.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

/***
 * fraction calculator
 * Input is an equation with two fractions or whole numbers and an operator
 * Output is the equation with "=" and the answer with the fraction reduced
 * Uses an input file called fraction_equations.txt.
 * Could be easily modified for equations input from the console.
 * Runs until the user enters "quit".
 *
 */

public class FractionCalculator
{
    public static void main(String[] args)
    {
        String fractionOne;
        String fractionTwo;
        String operator;
        boolean result = false;
        String answer = null;

        //Scanner input = new Scanner(System.in);
        Scanner input = null;
        try //reads a file containing the input equations
        {
            input = new Scanner(new File("fraction_equations.txt"));
        } 
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } 

        while (input.hasNextLine())
        {

            while (result == false)
            {
                //System.out.println("Enter an expression or 'quit':"); 
                fractionOne = input.next();
                if (fractionOne.equals("quit")) //exits the program on "quit"whiting sconline data
                {
                    //System.out.println("quit");
                    // System.out.println("You have exited the calculator");
                    //System.exit(0);
                    result = true;
                }
                else
                {

                    int numeratorOne = parseNumerator(fractionOne);
                    int denominatorOne = parseDenominator(fractionOne);

                    operator = input.next();

                    fractionTwo = input.next();
                    int numeratorTwo = parseNumerator(fractionTwo);
                    int denominatorTwo = parseDenominator(fractionTwo);

                    if (operator.equals("+"))
                    {
                        answer = addFractions(numeratorOne, denominatorOne,numeratorTwo, 
                                denominatorTwo, operator);
                    }
                    if (operator.equals("-"))
                    {
                        answer = subtractFractions(numeratorOne, denominatorOne,numeratorTwo, 
                                denominatorTwo, operator);
                    }
                    if (operator.equals("*"))
                    {
                        answer = multiplyFractions(numeratorOne, denominatorOne,numeratorTwo, 
                                denominatorTwo, operator);
                    }
                    if (operator.equals("/"))
                    {
                        answer = divideFractions(numeratorOne, denominatorOne,numeratorTwo, 
                                denominatorTwo, operator);
                    }
                    System.out.println(numeratorOne + "/" + denominatorOne + " " + 
                            operator + " " + numeratorTwo + "/" + denominatorTwo + " = " + answer);
                }
            }
        }
    }
    public static int parseNumerator(String fraction)
    {
        int numerator = 0;
        int denominator;
        int wholeNum = 0;
        int numeratorInput;

        if(fraction.contains("_") && (fraction.contains("/")))//mixed fraction
        {
            wholeNum=Integer.parseInt(fraction.substring(0,fraction.indexOf("_")));
            //parse the numerator from the fractional part
            numeratorInput = Integer.parseInt(fraction.substring(fraction.indexOf("_")+1,fraction.indexOf("/")));
            denominator=Integer.parseInt(fraction.substring(fraction.indexOf("/")+1));
            if (wholeNum < 0)//negative
            {
                int numeratorSign = ( wholeNum * denominator ) + (-1 *numeratorInput); //making mixed number improper
                numerator =  numeratorSign;
            }
            else//positive
            {
                numerator = ( wholeNum * denominator ) + numeratorInput; //making mixed number improper
            }
        }
        else if (fraction.contains("/"))  //regular fraction
        {
            numerator=Integer.parseInt(fraction.substring(0, fraction.indexOf("/")));
            denominator=Integer.parseInt(fraction.substring(fraction.indexOf("/")+1));
        }
        else //whole number with no fraction
        {
            numerator=Integer.parseInt(fraction.substring(0));
            denominator = 1;
        }
        //System.out.println("numerator = " + numerator);
        //System.out.println("denominator = " + denominator);

        return numerator;
    }

    public static int parseDenominator(String fraction)
    {
        int denominator;
        if (fraction.contains("/"))
        {
            denominator=Integer.parseInt(fraction.substring(fraction.indexOf("/")+1));
        }
        else
        {
            denominator = 1;
        }
        return denominator;
    }

    public static String addFractions(int numeratorOne, int denominatorOne, 
            int numeratorTwo, int denominatorTwo, String operator)
    {
        String fractionSum = "";
        int commonDenominator = 1;
        int numeratorSum;
        int factor;
        if (denominatorOne == denominatorTwo)
        {
            commonDenominator = denominatorOne;
            numeratorSum = numeratorOne + numeratorTwo;
        }
        else
        {
            commonDenominator = denominatorOne * denominatorTwo;
            int commonNumeratorOne = numeratorOne * denominatorTwo;
            int commonNumeratorTwo = numeratorTwo * denominatorOne;
            numeratorSum = commonNumeratorOne + commonNumeratorTwo;
         }
        fractionSum =  gcfFraction (numeratorSum, commonDenominator);
        return fractionSum;
    }
    public static String subtractFractions (int numeratorOne, int denominatorOne, 
            int numeratorTwo, int denominatorTwo, String operator)
    {
        String fractionDifference;
        int commonDenominator;
        int numeratorDifference;
        int factor;
        if (denominatorOne == denominatorTwo)
        {
            commonDenominator = denominatorOne;
            numeratorDifference = numeratorOne - numeratorTwo;
        }
        else
        {
            commonDenominator = denominatorOne * denominatorTwo;
            int commonNumeratorOne = numeratorOne * denominatorTwo;
            int commonNumeratorTwo = numeratorTwo * denominatorOne;
            numeratorDifference = commonNumeratorOne - commonNumeratorTwo;
        }
        fractionDifference = gcfFraction(numeratorDifference, denominatorOne);
        return fractionDifference;
    }
    
    public static String multiplyFractions(int numeratorOne, int denominatorOne, 
            int numeratorTwo, int denominatorTwo, String operator)
    {
        int numeratorProduct = (numeratorOne * numeratorTwo);
        int denominatorProduct = (denominatorOne * denominatorTwo);
        String fractionProduct = gcfFraction(numeratorProduct, denominatorProduct);
        return fractionProduct;
    }
    
    public static String divideFractions(int numeratorOne, int denominatorOne, 
            int numeratorTwo, int denominatorTwo, String operator)
    {
        int numeratorDivision = (numeratorOne * denominatorTwo);
        int denominatorDivision = (numeratorTwo * denominatorOne);
        String fractionDivided = gcfFraction(numeratorDivision, denominatorDivision);
        return fractionDivided;
    }
    
    public static String gcfFraction (int num1, int num2)
    // arguments are numerator and denominator
    {
        /** finds greatest common factor, reduces the fraction if needed
         * and writes fraction in mixed number format if appropriate
         **/
        int numerator = num1;  //initialize numerator to use later
        int denominator = num2; //initialize denominator to use later
        int tempNum;
        while (num1 % num2 != 0)  // use Euclid's algorithm
        {
            tempNum = num2;
            num2 = num1 % num2;
            num1 = tempNum;
        }
        if (num2 < 0)
        {
            num2 = num2 * -1;
        }
        int factor = num2; //this is the GCF
        numerator = numerator/factor;  //reduce numerator
        denominator = denominator/factor;  //reduce denominator

        String fractionFinal = "";
        if (denominator != 1 ) 
        {
            if (denominator < 0) // move "-" from denominator to numerator
            {
                numerator = numerator * -1;
                denominator = denominator * -1;
            }
            // make answer mixed number if it is an improper fraction
            if (Math.abs(numerator) > Math.abs(denominator))
            {
                int wholeNum = numerator / denominator;
                numerator = Math.abs(numerator - (wholeNum * denominator));
                fractionFinal = wholeNum + "_" + numerator + "/" + denominator;
            }
            else  //if fraction is proper
            {
                fractionFinal = numerator + "/" + denominator;
            }
        }
        else if (denominator == 1)  //remove "/1" if denominator = 1
        {
            fractionFinal = numerator + "";
        }
        return fractionFinal;
    }
/*
    //calculate greatest common factor
    public static int gcf(int num1,int num2)
    {
        int factor;
        int count = 0;
        int tempNum;
        while (num1 % num2 != 0)  // use Euclid's algorithm
        {
            tempNum = num2;
            num2 = num1 % num2;
            num1 = tempNum;
        }
        if (num2 < 0)
        {
            num2 = num2 * -1;
        }
        return num2;
    }
    
    public static String reduceFraction (int numerator, int denominator, int factor)
    {
        numerator = numerator/factor;
        denominator = denominator/factor;
        String fraction = numerator + "/" + denominator;
        return fraction;
    }
    
    public static String formatAnswer(int numeratorSumDif, int commonDenominator)
    {
        //format final answer to remove denominator = 1
        //format final answer to mixed number if appropriate
        String fractionFinal = "";
        if (commonDenominator != 1 )
        {
            
                
            if (Math.abs(numeratorSumDif) > Math.abs(commonDenominator))
            {
                int wholeNum = numeratorSumDif / commonDenominator;
                int numerator = Math.abs(numeratorSumDif - (wholeNum * commonDenominator));
                fractionFinal = wholeNum + "_" + numerator + "/" + commonDenominator;
            }
            else
            {
                fractionFinal = numeratorSumDif + "/" + commonDenominator;
            }
        }
        else if (commonDenominator == 1)
        {
            fractionFinal = numeratorSumDif + "";
        }
        return fractionFinal;
    }
    
    public static String printFraction (int num1, int num2)
    {
        int numerator = num1;
        int denominator = num2;
        int tempNum;
        while (num1 % num2 != 0)  // use Euclid's algorithm
        {
            tempNum = num2;
            num2 = num1 % num2;
            num1 = tempNum;
        }
        if (num2 < 0)
        {
            num2 = num2 * -1;
        }
        int factor = num2;
        numerator = numerator/factor;
        denominator = denominator/factor;
        String fraction = numerator + "/" + denominator;
        //return num2;
        String fractionFinal = "";
        if (denominator != 1 )
        {
            if (Math.abs(numerator) > Math.abs(denominator))
            {
                int wholeNum = numerator / denominator;
                numerator = Math.abs(numerator - (wholeNum * denominator));
                fractionFinal = wholeNum + "_" + numerator + "/" + denominator;
            }
            else
            {
                fractionFinal = numerator + "/" + denominator;
            }
        }
        else if (denominator == 1)
        {
            fractionFinal = numerator + "";
        }
        return fractionFinal;
    }
*/    

}

