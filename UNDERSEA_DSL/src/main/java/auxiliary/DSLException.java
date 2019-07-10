package auxiliary;

public class DSLException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3322424663356101425L;

    public DSLException(String message) {
        super(message);
    }

    public String toString() {
        return "Error: " + getMessage() + ".";
    }
}
