package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.repositories.FlightRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Component
public class SystemMethods {

    public static boolean checkIdForFlight(@PathVariable("id") Integer id, Map<String, Object> model, FlightRepo flightRepo) {
        if (!flightRepo.existsById(id)){
            return true;
        }
        Optional<BusFlights> flight = flightRepo.findById(id);
        ArrayList<BusFlights> busFlight = new ArrayList<>();
        flight.ifPresent(busFlight::add);
        model.put("flight", busFlight);
        return false;
    }
}
