package auxiliary;

public class DSLException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 3322424663356101425L;

    public DSLException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public String toString() {
        return "Error: " + getMessage() + ".";
    }
}
