package matrices.exceptions;

/**
 * Thrown when an operation expects a square matrix
 * (equal number of rows and columns) but receives a non-square matrix.
*/
public class NonSquareMatrixException extends IncompatibleMatrixDimensionException {

	private static final long serialVersionUID = 6794897455707386373L;

	public NonSquareMatrixException() {
		super();
	}
	
	public NonSquareMatrixException(String s) {
		super(s);
	}
	
	public NonSquareMatrixException(int m, int n) {
		this("Expected a square matrix, but received a matrix of dimension " + m + " x " + n + ".");
	}

}
