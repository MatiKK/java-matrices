package matrices.exceptions;

/**
* Thrown when an operation expects a vector of a specific length 
* and receives a different length vector.
*/
public class IncompatibleVectorSizeException extends RuntimeException {

	private static final long serialVersionUID = -5781655896665255002L;
	
	public IncompatibleVectorSizeException() {
		super();
	}
	
	public IncompatibleVectorSizeException(String s) {
		super(s);
	}
	public IncompatibleVectorSizeException(int m, int n) {
		super("Expected vector size of " + m + ", but received a vector of size " + n + ".");
	}

}
