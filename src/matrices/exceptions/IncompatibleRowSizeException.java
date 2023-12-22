package matrices.exceptions;
/**
 * Thrown when an operation expects a row of a specific size 
 * or when attempting to add a row to a matrix where it cannot be accommodated.
 * Typically used to indicate errors related to row size in matrix operations.
 */
public class IncompatibleRowSizeException extends IncompatibleVectorSizeException {

	private static final long serialVersionUID = 4043873225344230808L;
	
	public IncompatibleRowSizeException() {
		super();
	}
	
	public IncompatibleRowSizeException(String s) {
		super(s);
	}
	public IncompatibleRowSizeException(int m, int n) {
		super("Expected row size of " + m + ", but received a row of size " + n + ".");
	}

}
