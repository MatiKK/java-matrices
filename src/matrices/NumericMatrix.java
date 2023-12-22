package matrices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import matrices.exceptions.*;

/**
 * Represents a numeric matrix structure, exclusively accommodating Number
 * objects. It's designed for matrix operations such as addition, subtraction,
 * multiplication, determinant calculation, and similar operations.
 * <p>
 * It exclusively accepts rows consisting of lists of Number elements.
 */
public class NumericMatrix extends MatrixFixedDimension<Number> {

	private static final long serialVersionUID = 1443307467190145070L;

	/**
	 * Creates an empty numeric matrix. Its dimension will be determined by the
	 * first row that is added.
	 */
	public NumericMatrix() {
		super();
	}

	/**
	 * Creates a numeric matrix with an already provided first row. The dimension of
	 * the row will determine the dimension of this matrix.
	 * 
	 * @param first row of this matrix
	 */
	public NumericMatrix(List<Number> row) {
		super(row);
	}

	/**
	 * Creates a numeric matrix with the provided dimension as its limit.
	 * 
	 * @param dimension dimension of this matrix
	 * @throws IllegalArgumentException if a dimension {@code <0} is given
	 */
	public NumericMatrix(int dimension) {
		super(dimension);
	}

	/**
	 * Creates a numeric dimension matrix based on the provided matrix.
	 * 
	 * @param m The matrix used as a basis for creating the limited dimension
	 *          matrix.
	 * @throws IncompatibleRowSizeException if the rows of the provided matrix do
	 *                                      not have the same dimension.
	 */
	public NumericMatrix(Matrix<Number> m) {
		super(m);
	}

	@Override
	public Object clone() {
		NumericMatrix clone = new NumericMatrix();
		for (List<Number> row : this)
			clone.add(row);
		return clone;
	}
	
	/**
	 * Generates an identity matrix of dimension 'n'
	 *
	 * @param n the dimension of the identity matrix to be generated
	 * @return an identity matrix of size 'n x n'
	 * @throws IllegalArgumentException if n is less than 1
	 */
	public static NumericMatrix identity(int n) {
		NumericMatrix identity = new NumericMatrix(n);
		for (int i = 0; i < n; i++) {
			NumericVector identityRow = new NumericVector(n);
			identityRow.set(i, 1);
			identity.add(identityRow);
		}
		return identity;
	}

	/**
	 * Checks if the specified row can be added in this matrix. A row can be added
	 * in this matrix if the row does not include null objects and its dimension
	 * matches the dimension of this matrix.
	 * 
	 * @param row to check dimension
	 * @return {@code true} if the row can be added
	 */
	public boolean canBeAdded(List<Number> row) {
		if (row.contains(null))
			return false;

		return super.canBeAdded(row);
	}

	/**
	 * @throws IllegalArgumentException     if the row includes a null object
	 * @throws IncompatibleRowSizeException if the dimension of the specified row
	 *                                      cannot be added to this matrix (check
	 *                                      {@link #canBeAdded(List)})
	 */
	public boolean add(List<Number> row) {
		if (row.contains(null))
			throw new IllegalArgumentException("Null object not accepted in NumericMatrix");

		if (!canBeAdded(row))
			throw new IncompatibleVectorSizeException(getDimensionLimit(), row.size());

		if (row instanceof NumericVector)
			return super.add(row);

		return super.add(new NumericVector(row));
	}
	
	

	/**
	 * Calculates and returns the determinant of this numeric matrix.
	 *
	 * @return the determinant of the matrix
	 * @throws NonSquareMatrixException if the matrix is not square. check
	 *                                  {@link #isSquare()}
	 */
	public double determinant() {
		if (!isSquare())
			throw new NonSquareMatrixException(size(), getDimensionLimit());

		NumericMatrix clone = (NumericMatrix) this.clone();

		// NumericMatrix clone = this;

		int swappedRowsCount = clone.rowEchelonForm();

		BigDecimal determinant = new BigDecimal(Math.pow(-1, swappedRowsCount));

		for (int i = 0; i < getDimensionLimit(); i++) {
			BigDecimal diagonalNumber = new BigDecimal(clone.getElement(i, i).toString());
			determinant = determinant.multiply(diagonalNumber);
		}

		return determinant.doubleValue();
	}

	/**
	 * Orders the rows of the matrix based on the number of leading zeros in each
	 * row. Rows with more leading zeros are moved to lower positions.
	 * The returned value indicates the number of row swaps performed during
	 * the row ordering process. Swapping rows in a matrix affects the matrix determinant
	 * by multiplying it by -1 for each row swap performed.
	 * <p>
	 * For example:
	 * <p>
	 * - Rows (3, 4, 5) and (0, 2, 3) remain in their original positions.
	 * <p>
	 * - Rows (0, 0, 4) and (2, 3, 1) swap positions because the former has more
	 * leading zeros.
	 * <p>
	 * Before: (0, 0, 4) is above (2, 3, 1)
	 * <p>
	 * After: (2, 3, 1) is above (0, 0, 4)
	 *
	 * @return the number of row swaps performed during this method
	 */
	public int orderRows() {
		int swappedRowsCount = 0;

		for (int row1 = 0; row1 < size() - 1; row1++) {
			for (int row2 = row1 + 1; row2 < size(); row2++) {
				int indexRow1 = NumericVector.indexFirstNotZeroElement(get(row1));
				int indexRow2 = NumericVector.indexFirstNotZeroElement(get(row2));

				if (indexRow1 > indexRow2 || indexRow1 == -1) {
					swapRows(row1, row2);
					swappedRowsCount++;
				}
			}
		}

		return swappedRowsCount;
	}

	/**
	 * Converts the matrix into row-echelon form by performing row operations.
	 * Rearranges rows and manipulates entries to ensure that the leading entry of
	 * each row is to the right of the leading entry of the row above.
	 * The returned value indicates the number of row swaps performed during
	 * the row ordering process. Swapping rows in a matrix affects the matrix determinant
	 * by multiplying it by -1 for each row swap performed.
	 * 
	 * @return the number of row swaps performed during this method
	 */
	public int rowEchelonForm() {

		int swappedRowsCount = orderRows();

		for (int row1 = 0; row1 < size() - 1; row1++) {

			List<Number> currentRow = get(row1);
			int indexFirstNotZeroCurrentRow = NumericVector.indexFirstNotZeroElement(currentRow);

			for (int row2 = row1 + 1; row2 < size(); row2++) {

				List<Number> nextRow = get(row2);
				int indexFirstNotZeroNextRow = NumericVector.indexFirstNotZeroElement(nextRow);

				if (indexFirstNotZeroCurrentRow == indexFirstNotZeroNextRow && indexFirstNotZeroCurrentRow != -1) {

					BigDecimal currentRowFirstNumber = new BigDecimal(
							currentRow.get(indexFirstNotZeroCurrentRow).toString());

					BigDecimal nextRowFirstNumber = new BigDecimal(nextRow.get(indexFirstNotZeroNextRow).toString());

					BigDecimal alpha = nextRowFirstNumber.divide(currentRowFirstNumber, 20, RoundingMode.HALF_UP);

					NumericVector auxiliarRow = NumericVector.scalarMultiplication(currentRow, alpha);
					auxiliarRow = NumericVector.subtract(nextRow, auxiliarRow);

					setRow(row2, auxiliarRow);

				}
			}
			swappedRowsCount += orderRows();
		}
		return swappedRowsCount;
	}

	/**
	 * Checks if this matrix and the provided matrix can be added or subtracted.
	 * Verifies if both matrices have the same dimension limit and an equal number
	 * of rows for arithmetic operations.
	 *
	 * @param mat the matrix to be validated for addition/subtraction compatibility
	 * @return {@code true} if the matrices can be added or subtracted
	 */
	public boolean validateOperationCompability(NumericMatrix mat) {
		return getDimensionLimit() == mat.getDimensionLimit() && size() == mat.size();
	}

	/**
	 * Checks if this matrix and the provided matrix can be multiplied together.
	 * Verifies if the dimension limit of this matrix matches the number of rows in
	 * the provided matrix.
	 *
	 * @param mat the matrix to be validated for multiplication compatibility
	 * @return {@code true} if the matrices can be multiplied
	 */
	public boolean validateMultiplicationCompability(NumericMatrix mat) {
		return getDimensionLimit() == mat.size();
	}

	/**
	 * Performs matrix subtraction between this matrix and the provided matrix.
	 *
	 * @param mat The matrix to be subtracted from the current matrix.
	 * @return A new NumericMatrix resulting from the subtraction operation.
	 */
	public NumericMatrix subtract(NumericMatrix mat) {
		return operation(this, mat, -1);
	}

	/**
	 * Performs matrix addition between this matrix and the provided matrix.
	 *
	 * @param mat The matrix to be added to the current matrix.
	 * @return A new NumericMatrix resulting from the addition operation.
	 */
	public NumericMatrix add(NumericMatrix mat) {
		return operation(this, mat, 1);
	}

	/**
	 * Performs the addition or subtraction operation between two numeric matrices
	 * according to the formula: mat1 + alpha * mat2.
	 *<Strong>
	 * Note that if alpha = 1, an addition is performed, and if alpha = -1, a
	 * subtraction is performed.
	 *</Strong>
	 * @param mat1  the first matrix
	 * @param mat2  the second matrix
	 * @param alpha determines the scalar multiplier for the second matrix
	 * @return A new NumericMatrix resulting from the operation mat1 + alpha * mat2.
	 * @throws IncompatibleVectorSizeException
	 * if the matrices are incompatible for the arithmetic operation.
	 */
	private static NumericMatrix operation(NumericMatrix mat1, NumericMatrix mat2, Number alpha) {

		if (!mat1.validateOperationCompability(mat2))
			throw new IncompatibleVectorSizeException();

		NumericMatrix resultingMatrix = new NumericMatrix(mat1.getDimensionLimit());

		for (int i = 0; i < mat1.size(); i++) {
			List<Number> mat1Row = mat1.get(i);
			List<Number> mat2Row = mat2.get(i);

			resultingMatrix.add(NumericVector.add(mat1Row, NumericVector.scalarMultiplication(mat2Row, alpha)));
		}

		return resultingMatrix;
	}

	/**
	 * Multiplies two NumericMatrix instances.
	 *
	 * @param mat1 the first NumericMatrix to be multiplied
	 * @param mat2 the second NumericMatrix to be multiplied
	 * @return the result of multiplying the two matrices
	 * @throws IncompatibleMatrixDimensionException 
	 * when attempting to multiply incompatible matrices (see {@link #validateMultiplicationCompability(NumericMatrix)})
	 */
	public static NumericMatrix multiply(NumericMatrix mat1, NumericMatrix mat2) {
		if (!mat1.validateMultiplicationCompability(mat2))
			throw new IncompatibleMatrixDimensionException(mat1.getDimensionLimit(), mat2.size());

		NumericMatrix resultingMatrix = new NumericMatrix(mat2.getDimensionLimit());

		for (int i = 0; i < mat1.size(); i++) {

			List<Number> row = mat1.get(i);

			NumericVector newRow = new NumericVector();

			for (int j = 0; j < mat2.getDimensionLimit(); j++) {

				List<Number> col = mat2.getCol(j);

				newRow.add(NumericVector.dotProduct(row, col));
			}
			resultingMatrix.add(newRow);
		}
		return resultingMatrix;
	}
	
	public boolean isIdempotent() {
		return NumericMatrix.multiply(this, this).equals(this);
	}

}
