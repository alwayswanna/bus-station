package a.gleb.bus_station.exceptions;

public class DuplicateUserNameException extends RuntimeException{

    public DuplicateUserNameException(String message){
        super(message);
    }
}
