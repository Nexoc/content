package at.davl.movie.exceptions;



public class FileExistsException extends RuntimeException {

    public FileExistsException(String message) {
        super(message);
    }

}
