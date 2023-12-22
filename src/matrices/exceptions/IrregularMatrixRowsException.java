package matrices.exceptions;

/**
 * Thrown when an operation fails due to irregular row sizes in a matrix.
 * This exception indicates an error when an operation cannot proceed due to varying row lengths in the matrix.
 * For example, attempting to iterate over a column in a matrix with irregular row lengths may trigger this exception.
 */
public class IrregularMatrixRowsException extends IndexOutOfBoundsException {

	private static final long serialVersionUID = -1954598774769264598L;

	public IrregularMatrixRowsException() {
		super();
	}
	
	public IrregularMatrixRowsException(String s) {
		super(s);
	}
	
	public IrregularMatrixRowsException(int n) {
		super(n);
	}
}
