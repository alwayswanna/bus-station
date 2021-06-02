package a.gleb.bus_station.exceptions;

public class DuplicateFlightException extends RuntimeException{

    public DuplicateFlightException(String message){
        super(message);
    }
}
