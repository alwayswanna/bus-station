package a.gleb.bus_station.exceptions;

public class DuplicateOfDriverLicenseException extends RuntimeException{

    public DuplicateOfDriverLicenseException(String message){
        super(message);
    }
}
