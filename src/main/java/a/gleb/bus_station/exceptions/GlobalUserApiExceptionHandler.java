package a.gleb.bus_station.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalUserApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserFlightsIncorrectData> handleException(NoSuchElementException exceptionFlights){
        UserFlightsIncorrectData data = new UserFlightsIncorrectData();
        data.setInfo("NoSuchElementException: there are no elements with selected [id] - s");
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(DuplicateOfDriverLicenseException licenseException){
        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setInfo(licenseException.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(DuplicateFlightException duplicateFlightException){
        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setInfo(duplicateFlightException.getMessage());
        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);

    }
}
