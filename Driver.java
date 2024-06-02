import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Driver {
    public static void main(String [] args) {
    	// Test Cases for add() 
    	Polynomial p1 = new Polynomial(new double[]{1, 2}, new int[]{1, 2});
    	Polynomial p2 = new Polynomial(new double[]{6, -2, 5}, new int[]{0, 1, 3});

    	Polynomial p3 = p1.add(p2);
    	assert(p3.evaluate(1.7) == 34.644999999999996);
    	assert(p3.hasRoot(1.7) == false);
    	System.out.println("Coeficients: " + Arrays.toString(p3.coefficients) + " " + "Exponents: " + Arrays.toString(p3.exponents));
    	
    	Polynomial p4 = new Polynomial(new double[]{3, 0, 4}, new int[]{0, 2, 3});
    	Polynomial p5 = new Polynomial(new double[]{2, -1}, new int[]{2, 1});

    	Polynomial p6 = p4.add(p5);
    	assert(p6.evaluate(1.7) == 26.731999999999996);
    	assert(p6.hasRoot(1.7) == false);
    	System.out.println("Coeficients: " + Arrays.toString(p6.coefficients) + " " + "Exponents: " + Arrays.toString(p6.exponents));
    	
    	Polynomial p7 = new Polynomial(new double[]{1, 1}, new int[]{1, 0});
    	Polynomial p8 = new Polynomial(new double[]{-1, -1}, new int[]{1, 0});
    	Polynomial p9 = p7.add(p8);
    	System.out.println("Coeficients: " + Arrays.toString(p9.coefficients) + " " + "Exponents: " + Arrays.toString(p9.exponents));
    	
    	// Test Cases for multiply()
    	Polynomial p10 = new Polynomial(new double[]{3, 2}, new int[]{2, 1});
    	Polynomial p11 = new Polynomial(new double[]{1, 1}, new int[]{2, 0});
    	
    	Polynomial p12 = p10.multiply(p11);
    	assert(p12.evaluate(1.7) == 3*Math.pow(1.7, 4) + 2*Math.pow(1.7, 3) + 3*Math.pow(1.7, 2) + 2*1.7);

    	Polynomial p13 = new Polynomial(new double[]{2, 4, 3}, new int[]{3, 2, 0});
    	Polynomial p14 = new Polynomial(new double[]{3, 1}, new int[]{2, 0});

    	Polynomial p15 = p13.multiply(p14);
    	assert(p15.evaluate(1.7) == 6*Math.pow(1.7, 5) + 8*Math.pow(1.7, 4) + 9*Math.pow(1.7, 2) + 4*Math.pow(1.7, 2) + 3);
  
    	// Test Cases for saveToFile()
    	Polynomial test1 = new Polynomial(new double[] {1.12, -2, 3}, new int[] {4, 3, 2});
		Polynomial test2 = new Polynomial(new double[] {-4.5, 99}, new int[] {1, 99});

		Polynomial result = test2.multiply(test1);
		result.saveToFile("multTest.txt");
		System.out.println("Coeficients: " + Arrays.toString(result.coefficients) + " " + "Exponents: " + Arrays.toString(result.exponents));

		System.out.println(result.evaluate(-23.5));
		result = test1.multiply(test2);
		
		System.out.println(result.evaluate(-23.5));
		result = test1.add(test2);
		

		System.out.println(result.evaluate(23.5));
		result = test2.add(test1);

		System.out.println(result.evaluate(23.5));
		result.saveToFile("addTest.txt");
		System.out.println("Coeficients: " + Arrays.toString(result.coefficients) + " " + "Exponents: " + Arrays.toString(result.exponents));
		
		// Test Cases for file constructor
		File f = new File("./addTest.txt");
		Polynomial test3 = new Polynomial(f);
		System.out.println("File Constructor Test");
		System.out.println("Coeficients: " + Arrays.toString(test3.coefficients) + " " + "Exponents: " + Arrays.toString(test3.exponents));
        
		File providedTest = new File("./providedTest.txt");
		test3 = new Polynomial(providedTest);
		System.out.println("File Constructor Test");
		System.out.println("Coeficients: " + Arrays.toString(test3.coefficients) + " " + "Exponents: " + Arrays.toString(test3.exponents));
        
        
        
		
		
		
		
    }
    	
}
