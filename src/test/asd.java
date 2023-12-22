package test;
import java.util.ArrayList;
import java.util.Arrays;

import matrices.*;


public class asd {
	public static void main(String[] args) {
		
		
		NumericMatrix a = new NumericMatrix();
		
		a.add(new NumericVector(1,0,0));
		a.add(new NumericVector(0,1,0));
		a.add(new NumericVector(0,0,1));
		
		
		NumericMatrix b = new NumericMatrix();
		
		b.add(Arrays.asList(1,0,0.0));
		//b.add(new NumericVector(1,0,0.0));
		b.add(new NumericVector(0,1,0));
		b.add(new NumericVector(0,0,1.0));
		
		System.out.println(a.equals(b));
		
		System.out.println(NumericMatrix.multiply(a, a).equals(b));
		

		
		
	}

}
