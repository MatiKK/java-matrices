package matrices;

import java.util.List;
import matrices.exceptions.*;

/**
 * Represents a matrix structure where rows have a fixed length, extending the
 * functionality of a standard matrix by restricting the dimensions of its rows.
 * 
 * @param <E> the type of elements in the matrix.
 */
public class MatrixFixedDimension<E> extends Matrix<E> {

	private static final long serialVersionUID = -7091355132964592260L;
	
	/**
	 * Specifies the fixed length for rows in this matrix.
	 * This variable determines the allowed size for all rows within the matrix.
	 */
	private int dimensionLimit;

	/**
	 * Creates an empty fixed dimension matrix.
	 * The dimension of the matrix will be determined by the first row added to it.
	 */
	public MatrixFixedDimension() {
		super();
	}

	/**
	 * Constructs a matrix using the provided list as its first row. The dimension
	 * of the list will determine the dimension of this matrix.
	 *
	 * @param row the first row of this matrix, determining its dimension
	 */
	public MatrixFixedDimension(List<E> row) {
		super();
		this.add(row);
		this.dimensionLimit = row.size();
	}

	/**
	 * Constructs a fixed dimension matrix with the provided dimension as its
	 * limit.
	 *
	 * @param dimension the dimension of this matrix
	 * @throws IllegalArgumentException if a dimension less than 1 is given
	 */
	public MatrixFixedDimension(int dimension) {
		if (dimension < 1)
			throw new IllegalArgumentException("Dimension must be greater than 0.");
		dimensionLimit = dimension;
	}

	/**
	 * Constructs a fixed dimension matrix based on the provided matrix.
	 *
	 * @param m the matrix used as a basis for creating the limited dimension matrix
	 * @throws IncompatibleRowSizeException if the rows of the provided matrix do
	 *                                      not have the same dimension
	 */
	public MatrixFixedDimension(Matrix<E> m) {
		for (List<E> row : m)
			add(row);
	}

	public Object clone() {
		MatrixFixedDimension<E> clone = new MatrixFixedDimension<>(getDimensionLimit());
		for (List<E> row : this)
			clone.add(row);
		return clone;

	}

	/**
	 * Returns the fixed size that all rows in the matrix have.
	 * 
	 * @return the fixed size of rows in the matrix
	 */
	public int getDimensionLimit() {
		return numberOfColumns();
	}

	/**
	 * Checks if the specified row can be added to this matrix. A row can be added
	 * to this matrix if its length (dimension) matches the dimension of this
	 * matrix.
	 *
	 * @param row the row to check for dimension compatibility
	 * @return {@code true} if the row can be added
	 */
	public boolean canBeAdded(List<E> row) {
		return getDimensionLimit() == 0 || (row.size() > 0 && getDimensionLimit() == row.size());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IncompatibleRowSizeException if the dimension of the specified row
	 *                                      cannot be added to this matrix (check
	 *                                      {@link #canBeAdded(List)}).
	 */
	@Override
	public boolean add(List<E> row) {
		if (!canBeAdded(row))
			throw new IncompatibleRowSizeException(getDimensionLimit(), row.size());

		if (isEmpty())
			dimensionLimit = row.size();

		return super.add(row);
	}

	/**
	 * Checks whether this matrix is a square matrix.
	 * A square matrix has an equal number of rows and columns,
	 * excluding matrices with dimensions {@code 0x0}.
	 *
	 * @return {@code true} if this matrix is square
	 */
	public boolean isSquare() {
		return !isEmpty() && getDimensionLimit() == size();
	}

	@Override
	public List<E> removeCol(int indexCol) {
		List<E> col = super.removeCol(indexCol);
		dimensionLimit--;
		return col;
	}
	
	/**This method is not supported on fixed dimension matrices.
	 * @throws UnsupportedOperationException when attempting to remove an element from a fixed dimension matrix.
	 */
	public E removeElement(int indexRow, int indexCol) {
		throw new UnsupportedOperationException("not possible operation on fixed dimension matrices");
	}
	
	/**
	 * {@InheritDoc}
	 * @throws IncompatibleRowSizeException
	 * if the size of the given row are incompatible with this matrix
	 */
	public List<E> set(int indexRow, List<E> newRow){
		if (!canBeAdded(newRow))
			throw new IncompatibleRowSizeException(getDimensionLimit(), newRow.size());
		return super.set(indexRow, newRow);
	}
	
}
