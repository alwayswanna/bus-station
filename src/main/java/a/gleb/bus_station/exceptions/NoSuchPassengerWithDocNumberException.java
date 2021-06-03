package a.gleb.bus_station.exceptions;

public class NoSuchPassengerWithDocNumberException extends RuntimeException{

    public NoSuchPassengerWithDocNumberException(String message){
        super(message);
    }
}
