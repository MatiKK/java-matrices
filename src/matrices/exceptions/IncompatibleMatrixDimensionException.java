package matrices.exceptions;

/**
 * Thrown when matrix dimensions are incompatible for a specific operation.
 * This exception is typically thrown when attempting operations on matrices with incompatible sizes.
 */
public class IncompatibleMatrixDimensionException extends IncompatibleRowSizeException {

	private static final long serialVersionUID = -8906968377423945105L;

	public IncompatibleMatrixDimensionException() {
		super();
	}

	public IncompatibleMatrixDimensionException(String s) {
		super(s);
	}

	public IncompatibleMatrixDimensionException(int m, int n) {
		super("Expected a matrix with " + m + " rows, but received one with " + n + " rows.");
	}

}
