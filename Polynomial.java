import java.lang.Math;;

class Polynomial{
    double coefficients[];
    public Polynomial(){
        double new_arr[] = {0};
        coefficients = new_arr;
    }
    public Polynomial(double coefs[]){
        coefficients = coefs;
    }

    public Polynomial add(Polynomial poly1){
        int degree1 = poly1.coefficients.length;
        int degree2 = coefficients.length;
        double [] coefs = new double[(degree1 < degree2) ? degree2 : degree1];
        int i = 0;
        for(; i < degree1 && i < degree2; i++){
            coefs[i] = coefficients[i] + poly1.coefficients[i];
        }
        if(i < degree1){
            for(; i < degree1; i++){
                coefs[i] = poly1.coefficients[i];
            }
        }
        if(i < degree2){
            for(; i < degree2; i++){
                coefs[i] = coefficients[i];
            }
        }
        return new Polynomial(coefs);
    }

    public double evaluate(double point){
        double result = 0;
        for(int i = 0; i < coefficients.length; i++){
            result += Math.pow(point, i) * coefficients[i];
        }
        return result;
    }

    public boolean hasRoot(double point){
        return (evaluate(point) == 0);
    }
}
