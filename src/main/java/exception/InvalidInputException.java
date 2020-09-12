package exception;

public class InvalidInputException extends Exception {
    public InvalidInputException(String s,Exception e) {
        super(s,e);
    }



    public InvalidInputException(String message) {
        super(message);
    }
}
