package a.gleb.bus_station.service;

import a.gleb.bus_station.repositories.FlightRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
public class SystemMethods {

    public static boolean checkIdForFlight(@PathVariable("id") Integer id, Map<String, Object> model, FlightRepo flightRepo) {
        if (!flightRepo.existsById(id)){
            return true;
        }

        return false;
    }
}
