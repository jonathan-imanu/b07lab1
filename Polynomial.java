import java.lang.Math;
import java.util.Set;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

class Polynomial{
    double[] coefficients;
    int[] exponents;

    public Polynomial(){
        this.coefficients = null;
        this.exponents = null;
    }

    public Polynomial(double[] coefs, int[] expos){
        this.coefficients = coefs;
        this.exponents = expos;
    }
    
    public Polynomial(File filename){
    	String poly = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            poly = reader.readLine();
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        
        double[] tempC = new double[poly.length()];
        int[] tempE = new int[poly.length()];
        
        poly = poly.replaceAll("\\+", "~");
        poly = poly.replaceAll("\\-", "~-");
        if (poly.charAt(0) == '~') {
        	poly = poly.substring(1);
        }
        int length = 0;
        
        for(String func : poly.split("~")){
        	if(func.length() != 0 && func.contains("x")){
        		String[] res = func.split("x");
        		if (Double.parseDouble(res[0]) != 0.0) {
        			tempC[length] = Double.parseDouble(res[0]);
            		tempE[length] = Integer.parseInt(res[1]);
        		}
        	}
        	else if (func.length() != 0 && Double.parseDouble(func) != 0.0) {
    			tempC[length] = Double.parseDouble(func);
        		tempE[length] = 0;
        		
        	}
        	length++;
        }
        
        double[] coefs = new double[length];
        int[] expos = new int[length];
        
        System.arraycopy(tempC, 0, coefs, 0, length);
        System.arraycopy(tempE, 0, expos, 0, length);
        
        this.coefficients = coefs;
        this.exponents = expos;
    }

    public Polynomial add(Polynomial newPolynomial){
    	if(newPolynomial == null) {
    		return new Polynomial(this.coefficients, this.exponents);
    	}
    	
        int degree1 = this.coefficients.length;
        int degree2 = newPolynomial.coefficients.length;

        double[] tempCoefficients = new double[degree1 + degree2];
        int[] tempExponents = new int[degree1 + degree2];

        int uniqueTerms = 0;

        for(int i = 0; i < degree1; i++){
            tempCoefficients[uniqueTerms] = this.coefficients[i];
            tempExponents[uniqueTerms] = this.exponents[i];
            uniqueTerms++;
        }

        for(int j = 0; j < degree2; j++){
            boolean found = false;
            for (int i = 0; i < degree1; i++){
                if (this.exponents[i] == newPolynomial.exponents[j]) {
                    tempCoefficients[i] += newPolynomial.coefficients[j];
                    found = true;
                    break;
                }
            }
            if(!found){
                tempCoefficients[uniqueTerms] = newPolynomial.coefficients[j];
                tempExponents[uniqueTerms] = newPolynomial.exponents[j];
                uniqueTerms++;
            }
        }

        int numberOfZeroC = 0;
        for(int i = 0; i < tempCoefficients.length; i++){
            if(tempCoefficients[i] == 0){
                numberOfZeroC++;
            }
        }
        
        int length = degree1 + degree2 - numberOfZeroC;
        double[] newCoefficents = new double[length];
        int[] newExponents = new int[length];

        for(int i = 0, j = 0; i < degree1 + degree2; i++){
            if(j == length){
                break;
            }
            if(tempCoefficients[i] != 0){
                newCoefficents[j] = tempCoefficients[i];
                newExponents[j] = tempExponents[i];
                j++;
            }
        }

        return new Polynomial(newCoefficents, newExponents);
    }

    public double evaluate(double point){
    	if (this.coefficients == null && this.exponents == null) {
    		return 0.0;
    	}
        double result = 0;
        for(int i = 0; i < coefficients.length; i++){
            result += Math.pow(point, exponents[i]) * coefficients[i];
        }
        return result;
    }

    public boolean hasRoot(double point){
        return (evaluate(point) == 0.0);
    }

    public Polynomial multiply(Polynomial newPolynomial){
    	if (newPolynomial == null) {
    		return null;
    	}
    	
    	int degree1 = this.coefficients.length;
        int degree2 = newPolynomial.coefficients.length;

        double tempC[] = new double[degree1 * degree2];
        int tempE[] = new int[degree1 * degree2];

        int index = 0;
        for (int i = 0; i < degree1; i++) {
            for (int j = 0; j < degree2; j++) {  // Corrected the condition here
                tempC[index] = this.coefficients[i] * newPolynomial.coefficients[j];
                tempE[index] = this.exponents[i] + newPolynomial.exponents[j];
                index++;
            }
        }

        int uniqueExponentsCount = 0;
        for (int i = 0; i < tempE.length; i++) {
            boolean isUnique = true;
            for (int j = 0; j < i; j++) {
                if (tempE[i] == tempE[j]) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                uniqueExponentsCount++;
            }
        }

        double[] combinedC = new double[uniqueExponentsCount];
        int[] combinedE = new int[uniqueExponentsCount];
        int combinedIndex = 0;

        for (int i = 0; i < tempE.length; i++) {
            boolean found = false;
            for (int j = 0; j < combinedIndex; j++) {
                if (combinedE[j] == tempE[i]) {
                    combinedC[j] += tempC[i];
                    found = true;
                    break;
                }
            }
            if (!found) {
                combinedE[combinedIndex] = tempE[i];
                combinedC[combinedIndex] = tempC[i];
                combinedIndex++;
            }
        }

        return new Polynomial(combinedC, combinedE);
    }

    public String polynomialToString(){
        String outputString = "";
        for(int i = 0; i < coefficients.length; i++){
            if(i == 0){
                ;
            }
            else if(coefficients[i] > 0){
                outputString += "+";
            }
            outputString += String.valueOf(coefficients[i]);
            if(exponents[i] != 0){
                outputString += "x";
                outputString += String.valueOf(exponents[i]);
            }
        }
        return outputString;
    }

    public void saveToFile(String filename){
        String outputString = this.polynomialToString();
        try{
            FileWriter writer = new FileWriter(filename); 
            writer.write(outputString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}