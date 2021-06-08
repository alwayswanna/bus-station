package a.gleb.bus_station.exceptions;

public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException(String message){
        super(message);
    }
}
