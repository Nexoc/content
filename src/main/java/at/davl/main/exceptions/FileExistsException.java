package at.davl.main.exceptions;



public class FileExistsException extends RuntimeException {

    public FileExistsException(String message) {
        super(message);
    }

}
