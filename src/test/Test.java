package test;

import matrices.*;
import java.util.Objects;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		Scanner reader = new Scanner(System.in);
		
		System.out.println("Choose a matrix operation to perform:\n1- Calculate determinant \n2- Matrix addition \n3- Matrix subtraction \n4- Matrix multiplication");
		
		int chosenOperation = reader.nextInt();
		NumericMatrix m1, m2, result;
		switch (chosenOperation) {
			case 1:
				m1 = askForMatrix(reader);
				System.out.println(m1);
				double determinant = m1.determinant();
				System.out.println("Determinant: " + determinant);
				break;
			case 2:
				m1 = askForMatrix(reader);
				m2 = askForMatrix(reader);
				result = m1.add(m2);
				System.out.println(result);
				break;
			case 3:
				m1 = askForMatrix(reader);
				m2 = askForMatrix(reader);
				result = m1.subtract(m2);
				System.out.println(result);
				break;
			case 4:
				m1 = askForMatrix(reader);
				m2 = askForMatrix(reader);
				result = NumericMatrix.multiply(m1,m2);
				System.out.println(result);
				break;
		}
		
		reader.close();
		
		
	}
	
	public static NumericMatrix askForMatrix(Scanner sc) {
		
		int rows, columns;
		System.out.print("Input the matrix number of rows");
		rows = sc.nextInt();
		Objects.checkIndex(rows-1, Integer.MAX_VALUE);
		System.out.print("Input the matrix number of columns");
		columns = sc.nextInt();
		Objects.checkIndex(columns-1,Integer.MAX_VALUE);
		
		NumericMatrix mat = new NumericMatrix(columns);

		System.out.println("Write your matrix rows separating each number with a space");
		
		sc.nextLine();
		int addedRows = 0;
		while (addedRows != rows) {

			NumericVector newRow = new NumericVector();
			System.out.print("F"+(addedRows+1) + ": ");
			String[] rowInput = sc.nextLine().split(" ");
			for (String number: rowInput)
				newRow.add(Double.parseDouble(number));

			mat.add(newRow);
			addedRows++;
		}

		return mat;
	}
}
