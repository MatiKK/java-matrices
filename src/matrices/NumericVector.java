package matrices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import matrices.exceptions.IncompatibleVectorSizeException;

/**
 * Represents a vector of numbers, extending ArrayList<Number>. Used for
 * numerical vector operations.
 */
public class NumericVector extends ArrayList<Number> {

	private static final long serialVersionUID = -7408612673576241953L;

	/**
	 * Constructs an empty NumericVector.
	 */
	public NumericVector() {
		super();
	}

	/**
	 * Constructs a NumericVector using the provided numbers.
	 *
	 * @param nums the numbers to be added to the NumericVector
	 * @param <T>  any subtype of Number
	 */
	@SafeVarargs
	public <T extends Number> NumericVector(T... nums) {
		for (Number num : nums)
			add(num);
	}

	/**
	 * Constructs a NumericVector from the provided list of numbers.
	 *
	 * @param list the list of numbers to be added to the NumericVector
	 */
	public NumericVector(List<? extends Number> list) {
		for (Number number : list)
			this.add(number);
	}

	/**
	 * Finds the index of the first non-zero element in the provided list of
	 * numbers.
	 *
	 * @param v the list of numbers to search for the first non-zero element
	 * @return the index of the first non-zero element, or -1 if all elements are
	 *         zero
	 */
	public static int indexFirstNotZeroElement(List<? extends Number> v) {
		for (int i = 0; i < v.size(); i++) {
			if (!(v.get(i).doubleValue() == 0))
				return i;
		}
		return -1;
	}

	/**
	 * Finds the index of the first non-zero element in this NumericVector.
	 *
	 * @return the index of the first non-zero element, or -1 if all elements are
	 *         zero.
	 */
	public int indexFirstNotZeroElement() {
		return indexFirstNotZeroElement(this);
	}

	/**
	 * Checks if two vectors are compatible for vector operations. Two vectors are
	 * considered compatible if they have the same size.
	 *
	 * @param vector1 the first vector to be validated
	 * @param vector2 the second vector to be validated
	 * @return {@code true} if the vectors have the same size
	 */
	public static boolean validateOperationCompability(List<? extends Number> vector1, List<? extends Number> vector2) {
		return vector1.size() == vector2.size();
	}

	/**
	 * Validates the compatibility of this NumericVector with another vector for a
	 * mathematical operation. Two vectors are considered compatible if they have
	 * the same size.
	 *
	 * @param v the other vector to be validated for compatibility with this
	 *          NumericVector.
	 * @return {@code true} if the vectors have the same size
	 */
	public boolean validateOperationCompability(List<? extends Number> v) {
		return validateOperationCompability(this, v);
	}

	/**
	 * Performs scalar multiplication of a vector by a scalar value.
	 *
	 * @param v     the vector to be multiplied
	 * @param alpha the scalar value for multiplication
	 * @return A new NumericVector resulting from the scalar multiplication
	 */
	public static NumericVector scalarMultiplication(List<? extends Number> v, Number alpha) {
		BigDecimal multiplier = new BigDecimal(alpha.toString());
		NumericVector resultingVector = new NumericVector();
		for (Number number : v) {
			BigDecimal currentNumber = new BigDecimal(number.toString());
			currentNumber = currentNumber.multiply(multiplier);
			resultingVector.add(currentNumber);
		}
		return resultingVector;

	}

	/**
	 * Performs scalar multiplication of this NumericVector by a scalar value.
	 *
	 * @param alpha the scalar value for multiplication
	 * @return A new NumericVector resulting from the scalar multiplication
	 */
	public NumericVector scalarMultiplication(Number alpha) {
		return scalarMultiplication(this, alpha);
	}

	/**
	 * Performs subtraction between two vectors.
	 *
	 * @param v1 The first vector
	 * @param v2 The second vector
	 * @return A new NumericVector resulting from v1 - v2
	 * @throws IncompatibleVectorSizeException if the vectors have different sizes
	 */
	public static NumericVector subtract(List<? extends Number> v1, List<? extends Number> v2) {
		return operation(v1, v2, -1);
	}

	/**
	 * Performs subtraction between this vector and another vector.
	 *
	 * @param v the vector to subtract from this NumericVector
	 * @return A new NumericVector resulting from the subtraction operation
	 *         ({@code this} - v)
	 * @throws IncompatibleVectorSizeException if the vectors have different sizes
	 */
	public NumericVector subtract(List<Number> v) {
		return subtract(this, v);
	}

	/**
	 * Performs addition between two vectors.
	 *
	 * @param v1 The first vector
	 * @param v2 The second vector
	 * @return A new NumericVector resulting from v1 + v2
	 * @throws IncompatibleVectorSizeException if the vectors have different sizes
	 */
	public static NumericVector add(List<? extends Number> v1, List<? extends Number> v2) {
		return operation(v1, v2, 1);
	}

	/**
	 * Performs addition between this vector and another vector.
	 *
	 * @param v the vector to add to this NumericVector
	 * @return a new NumericVector resulting from the addition operation
	 *         ({@code this} + v)
	 * @throws IncompatibleVectorSizeException if the vectors have different sizes
	 */
	public NumericVector add(List<Number> v) {
		return add(this, v);
	}

	/**
	 * Performs the addition or subtraction operation between two vectors according
	 * to the formula: v1 + alpha * v2. <Strong> Note that if alpha = 1, an addition
	 * is performed, and if alpha = -1, a subtraction is performed. </Strong>
	 * 
	 * @param v1    the first vector
	 * @param v2    the second vector
	 * @param alpha determines the scalar multiplier for the second vector
	 * @return A new NumericVector resulting from the operation v1 + alpha * v2
	 * @throws IncompatibleVectorSizeException if the vectors have different sizes
	 */
	private static NumericVector operation(List<? extends Number> v1, List<? extends Number> v2, Number alpha) {

		if (!validateOperationCompability(v1, v2))
			throw new IncompatibleVectorSizeException(v1.size(), v2.size());

		NumericVector resultingVector = new NumericVector();

		BigDecimal multiplier = new BigDecimal(alpha.toString());

		for (int i = 0; i < v1.size(); i++) {

			BigDecimal firstNumber = new BigDecimal(v1.get(i).toString());
			BigDecimal secondNumber = new BigDecimal(v2.get(i).toString());

			firstNumber = firstNumber.add(secondNumber.multiply(multiplier));

			// chequear umbral
			BigDecimal umbral = new BigDecimal("1e-15");
			if (firstNumber.abs().subtract(umbral).compareTo(BigDecimal.ZERO) <= 0)
				firstNumber = BigDecimal.ZERO;

			resultingVector.add(firstNumber);

		}
		return resultingVector;
	}

	/**
	 * Computes the dot product of two vectors.
	 * The dot product is only defined for vectors of the same length.
	 *
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return the dot product of the two vectors
	 * @throws IncompatibleVectorSizeException if the vectors have different sizes
	 */
	public static double dotProduct(List<? extends Number> v1, List<? extends Number> v2) {
		if (!validateOperationCompability(v1, v2))
			throw new IncompatibleVectorSizeException(v1.size(), v2.size());

		BigDecimal result = new BigDecimal("0");
		for (int i = 0; i < v1.size(); i++) {
			BigDecimal numberVector1 = new BigDecimal(v1.get(i).toString());
			BigDecimal numberVector2 = new BigDecimal(v2.get(i).toString());

			BigDecimal producto = numberVector1.multiply(numberVector2);
			result = result.add(producto);

		}
		return result.doubleValue();
	}

	/**
	 * Computes the dot product of this vector with another vector.
	 * The dot product is only defined for vectors of the same length.
	 *
	 * @param v the other vector to compute the dot product
	 * @return the dot product of the two vectors
	 * @throws IncompatibleVectorSizeException if the vectors have different sizes
	 */
	public double dotProduct(List<? extends Number> v) {
		return dotProduct(this, v);
	}

	/**
	 * Checks if two numeric vectors are perpendicular (orthogonal).
	 * Two vectors are perpendicular if their dot product is zero.
	 *
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return {@code true} if thier dot product is zero
	 */
	public static boolean isPerpendicular(List<? extends Number> v1, List<? extends Number> v2) {
		return validateOperationCompability(v1,v2) && dotProduct(v1, v2) == 0;
	}

	/**
	 * Checks if this vector is perpendicular (orthogonal) to another vector.
	 * Two vectors are perpendicular if their dot product is zero.
	 *
	 * @param v the other vector to check for perpendicularity with this vector
	 * @return {@code true} if their dot product is zero
	 */
	public boolean isPerpendicular(List<? extends Number> v) {
		return isPerpendicular(this, v);
	}

	/**
	 * Computes the cross product (vector product) of two 3-dimensional numeric vectors.
	 * The cross product is defined only for vectors in three-dimensional space.
	 *
	 * @param v1 the first 3-dimensional vector.
	 * @param v2 the second 3-dimensional vector.
	 * @return a new NumericVector representing the cross product of the two vectors
	 * @throws UnsupportedOperationException if the vectors are not three-dimensional
	 */
	public static NumericVector crossProduct(List<? extends Number> v1, List<? extends Number> v2) {

		if (!validateOperationCompability(v1, v2) || v1.size() != 3)
			throw new UnsupportedOperationException("Two vectors of size 3 are expected on cross product.");

		NumericVector perpendicularVector = new NumericVector();

		BigDecimal number1 = new BigDecimal(v1.get(1).toString());
		BigDecimal number2 = new BigDecimal(v2.get(2).toString());
		BigDecimal aux = number1.multiply(number2);
		number1 = new BigDecimal(v1.get(2).toString());
		number2 = new BigDecimal(v2.get(1).toString());
		aux = aux.subtract(number1.multiply(number2));
		perpendicularVector.add(aux);

		number1 = new BigDecimal(v1.get(0).toString());
		number2 = new BigDecimal(v2.get(2).toString());
		aux = number1.multiply(number2);
		number1 = new BigDecimal(v1.get(2).toString());
		number2 = new BigDecimal(v2.get(0).toString());
		aux = aux.subtract(number1.multiply(number2));
		aux = aux.multiply(new BigDecimal("-1"));
		perpendicularVector.add(aux);

		number1 = new BigDecimal(v1.get(0).toString());
		number2 = new BigDecimal(v2.get(1).toString());
		aux = number1.multiply(number2);
		number1 = new BigDecimal(v1.get(1).toString());
		number2 = new BigDecimal(v2.get(0).toString());
		aux = aux.subtract(number1.multiply(number2));
		perpendicularVector.add(aux);

		return perpendicularVector;

	}

}
