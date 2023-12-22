package matrices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import matrices.exceptions.*;

/**
 * Represents a matrix data structure implemented as a list of lists, providing
 * the fundamental operations and behaviors of a two-dimensional matrix.
 *
 * @param <E> the type of elements stored in the matrix.
 */
public class Matrix<E> extends ArrayList<List<E>> {

	private static final long serialVersionUID = -8767750057390170945L;
	
	/**
	 * Number of rows in the matrix
	 */
	private int numberOfRows = 0;
	/**
	 * Number of columns in the matrix. Not consistent because there is no
	 * restriction on the length of the row that can be added
	 */
	private int numberOfColumns = 0;

	/**
	 * Number of elements in the matrix. Nulls are counted.
	 */
	private int numberOfElements = 0;

	/**
	 * Creates an empty Matrix.
	 */
	public Matrix() {
		super();
	}

	/**
	 * Constructs a matrix using the provided list as its first row.
	 *
	 * @param c The list representing the first row of the matrix.
	 */
	public Matrix(List<E> c) {
		super();
		add(c);
	}

	/**
	 * Returns a shallow copy of this matrix. For numeric matrices, this operation
	 * ensures that the elements are copied to prevent unintended mutations.
	 *
	 * @return A clone of this {@code Matrix}.
	 */
	public Object clone() {
		Matrix<E> m = new Matrix<>();
		for (List<E> row : this) {
			m.add(new ArrayList<>(row));
		}
		return m;
	}

	@Override
	public String toString() {
		if (this.isEmpty())
			return "[]";
		StringBuilder s = new StringBuilder('[');
		for (List<E> row : this) {
			s.append(row.toString()).append('\n');
		}
		s.deleteCharAt(s.length() - 1);
		return s.toString();

	}
	
	/**
	 * Transposes the current matrix.
	 *
	 * <p>This operation converts rows into columns and vice versa, resulting in a new matrix where
	 * the rows of the original matrix become the columns in the transposed matrix and vice versa.
	 *
	 * <p><strong>Note:</strong> If the matrix contains irregular row lengths, an {@code
	 * IrregularMatrixRowsException} may be thrown, indicating that the matrix rows are not of equal
	 * lengths and cannot be transposed.
	 *
	 * @return a new matrix representing the transpose of the current matrix
	 * @throws IrregularMatrixRowsException if the matrix contains rows of different lengths,
	 *     preventing transposition due to irregularity in row sizes
	 */
	public Matrix<E> transpose(){
		Matrix<E> transpose = new Matrix<E>();
		
		for (int i = 0; i < numberOfColumns; i++)
			transpose.add(getCol(i));
		
		return transpose;
	}

	/**
	 * Transposes the current matrix, complementing incomplete rows with null values.
	 * see {@link #transpose()}
	 *
	 * @return a new matrix representing the transpose of the current matrix with null-padding for
	 *     incomplete rows.
	 */
	public Matrix<E> transposeCatchNull(){
		Matrix<E> transpose = new Matrix<E>();
		
		for (int i = 0; i < numberOfColumns; i++)
			transpose.add(getColCatchNull(i));
		
		return transpose;
	}
	
	/**
	 * Checks if the matrix is symmetric.
	 *
	 * <p>A matrix is considered symmetric if it is equal to its transpose. In other words, it is
	 * symmetric if, for every element at position (i, j), the element at position (j, i) is equal.
	 * The matrix must be square (number of rows equals number of columns) to be symmetric.
	 *
	 * @return {@code true} if the matrix is symmetric, {@code false} otherwise.
	 */
	public boolean isSymmetric() {
	    for (int i = 0; i < size(); i++)
	    	if (!getRow(i).equals(getCol(i)))
	    		return false;
	    return true;
	}

	/**
	 * Adds a new row to the matrix.
	 *
	 * @param row The row to be added to the matrix.
	 */
	public boolean add(List<E> row) {
		List<E> newRow = new ArrayList<>(row);
		int newRowSize = newRow.size();
		numberOfRows = numberOfRows + 1;
		numberOfColumns = Math.max(numberOfColumns, newRowSize);
		numberOfElements = numberOfElements + newRowSize;
		return super.add(newRow);
	}

	/**
	 * Adds a new row to the matrix.
	 *
	 * @param row The row to be added to the matrix.
	 */
	public void addRow(List<E> row) {
		add(row);
	}

	/**
	 * Checks whether the matrix is empty. A matrix is deemed empty when it contains
	 * no elements, considering the total count of elements across all rows.
	 *
	 * @return {@code true} if the matrix is empty.
	 */
	@Override
	public boolean isEmpty() {
		return numberOfElements() == 0;
	}

	/**
	 * Removes the row at the specified index from the matrix.
	 *
	 * @param indexRow the index of the row to be removed
	 * @return the removed row
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public List<E> remove(int indexRow) {
		List<E> row = super.remove(indexRow);
		numberOfElements -= row.size();
		return row;
	}

	/**
	 * Removes the row at the specified index from the matrix.
	 *
	 * @param indexRow the index of the row to be removed
	 * @return the removed row
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public List<E> removeRow(int indexRow) {
		return remove(indexRow);
	}

	/**
	 * Replaces the row at the specified index with the provided new row.
	 *
	 * @param indexRow the index of the row to be replaced
	 * @param newRow   the new row to replace the existing row
	 * @return the row that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public List<E> setRow(int indexRow, List<E> newRow) {
		return set(indexRow, newRow);
	}
	
	/**
	 * Replaces the row at the specified index in the matrix with the provided new row.
	 *
	 * @param indexRow the index of the row to be replaced.
	 * @param newRow the new row to replace the existing row
	 * @return the row that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public List<E> set(int indexRow, List<E> newRow){
		List<E> removedRow = super.set(indexRow, newRow);
		numberOfElements = numberOfElements - removedRow.size() + newRow.size();
		return removedRow;
	}

	/**
	 * Returns the row at the specified index from the matrix.
	 *
	 * @param indexRow the index of the row to be retrieved
	 * @return the row at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public List<E> getRow(int indexRow) {
		return get(indexRow);
	}

	/**
	 * Returns the element at the specified row and column indices.
	 *
	 * @param indexRow the index of the row containing the element
	 * @param indexCol the index of the column containing the element
	 * @return the element at the specified row and column.
	 * @throws IndexOutOfBoundsException if the row or column indices are out of
	 *                                   range
	 */
	public E getElement(int indexRow, int indexCol) {
		return get(indexRow).get(indexCol);
	}

	/**
	 * Replaces the element at the specified row and column indices with the
	 * provided new element.
	 *
	 * @param indexRow   the index of the row containing the element to be replaced
	 * @param indexCol   the index of the column containing the element to be
	 *                   replaced
	 * @param newElement the new element to replace the existing element
	 * @return the element that was replaced
	 * @throws IndexOutOfBoundsException if the row or column indices are out of
	 *                                   range
	 */
	public E setElement(int indexRow, int indexCol, E newElement) {
		List<E> row = get(indexRow);
		return row.set(indexCol, newElement);
	}
	
	/**
	 * Removes and returns the element at the specified row and column indices in the matrix.
	 *
	 * @param indexRow the index of the row containing the element to be removed
	 * @param indexCol the index of the column containing the element to be removed
	 * @return the element removed from the specified position in the matrix
	 * @throws IndexOutOfBoundsException if the indices are out of range
	 */
	public E removeElement(int indexRow, int indexCol) {
		E removedElement = get(indexRow).remove(indexCol);
		numberOfElements--;
		return removedElement;
	}

	/**
	 * Creates and returns a sub-matrix derived from the current matrix, excluding
	 * the specified row and column.
	 *
	 * @param indexRow The index of the row to be excluded from the sub-matrix.
	 * @param indexCol The index of the column to be excluded from the sub-matrix.
	 * @return A new Matrix instance representing a sub-matrix without the specified
	 *         row and column.
	 * @throws IndexOutOfBoundsException if the row or column indices are out of
	 *                                   range.
	 */
	public Matrix<E> subMatrix(int indexRow, int indexCol) {
		@SuppressWarnings("unchecked")
		Matrix<E> subMatrix = (Matrix<E>) this.clone();
		subMatrix.removeRow(indexRow);
		subMatrix.removeCol(indexCol);
		return subMatrix;
	}

	/**
	 * Removes the column at the specified index from the matrix.
	 *
	 * @param indexCol the index of the column to be removed.
	 * @return a list representing the removed column.
	 * @throws IndexOutOfBoundsException    if the index is out of range.
	 * @throws IrregularMatrixRowsException if any row does not contain the
	 *                                      specified column index.
	 */
	public List<E> removeCol(int indexCol) {
		Objects.checkIndex(indexCol, numberOfColumns);

		List<E> deletedCol = new ArrayList<>();
		for (List<E> row : this) {
			try {
				deletedCol.add(row.remove(indexCol));
			} catch (IndexOutOfBoundsException e) {
				throw new IrregularMatrixRowsException(
						"The matrix has an uncompleted column due to inconsistent row sizes");
			}
		}
		numberOfElements -= deletedCol.size();
		return deletedCol;
	}

	/**
	 * Returns the column at the specified index from the matrix.
	 *
	 * @param indexCol the index of the column to return
	 * @return a list representing the column at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 * @throws IrregularMatrixRowsException if any row does not contain the
	 *                                      specified column index.
	 */
	public List<E> getCol(int indexCol) {
		List<E> col = getColCatchNull(indexCol);
		int i = col.indexOf(null);
		if (i != -1)
			throw new IrregularMatrixRowsException(
					"The matrix has an uncompleted column (row index " + i + ") due to inconsistent row sizes");
		return col;
	}

	/**
	 * Returns the column at the specified index from the matrix. If any row does
	 * not contain the specified column index, it includes null values in the column
	 * instead of throwing an exception.
	 *
	 * @param indexCol the index of the column to be retrieved.
	 * @return a list representing the column at the specified index, including null
	 *         values if needed
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public List<E> getColCatchNull(int indexCol) {
		Objects.checkIndex(indexCol, get(0).size());

		List<E> col = new ArrayList<E>();

		for (List<E> row : this) {
			try {
				col.add(row.get(indexCol));
			} catch (IndexOutOfBoundsException e) {
				col.add(null);
			}
		}

		return col;
	}

	/**
	 * Swaps the positions of two rows within the matrix.
	 *
	 * @param indexRow1 the index of the first row to be swapped
	 * @param indexRow2 the index of the second row to be swapped
	 * @throws IndexOutOfBoundsException if either indexRow1 or indexRow2 is out of
	 *                                   range
	 */
	public void swapRows(int indexRow1, int indexRow2) {

		List<E> row1 = this.getRow(indexRow1);
		List<E> row2 = this.getRow(indexRow2);

		setRow(indexRow1, row2);
		setRow(indexRow2, row1);
	}

	/**
	 * Returns the number of rows in the matrix.
	 *
	 * @return the number of rows in this matrix
	 */
	public int size() {
		return numberOfRows;
	}

	/**
	 * Returns the total number of elements in the matrix, which is the sum the
	 * count of elements across all rows or columns, including null objects.
	 *
	 * @return the total count of elements in the matrix
	 */
	public int numberOfElements() {
		return numberOfElements;
	}
	
	/**
	 * Returns the number of columns in the matrix.
	 * 
	 * Note: The number of columns may be inconsistent in a  non fixed dimension matrix.
	 * @return the number of columns in the matrix
	 */
	int numberOfColumns() {
		return numberOfColumns;
	}
	
}
