package a.gleb.bus_station.exceptions;

public class NoFreeSpaceException extends RuntimeException{

    public NoFreeSpaceException(String message){
        super(message);
    }
}
