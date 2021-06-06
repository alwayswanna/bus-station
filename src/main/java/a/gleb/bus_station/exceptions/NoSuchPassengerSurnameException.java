package a.gleb.bus_station.exceptions;

public class NoSuchPassengerSurnameException extends RuntimeException{

    public NoSuchPassengerSurnameException(String message){
        super(message);
    }
}
