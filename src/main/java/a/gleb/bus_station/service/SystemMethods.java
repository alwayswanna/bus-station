package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.repositories.FlightRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Component
public class SystemMethods {

    public static boolean checkIdForFlight(@PathVariable("id") Integer id, Map<String, Object> model, FlightRepo flightRepo) {
        if (!flightRepo.existsById(id)){
            return true;
        }

        return false;
    }

    public static boolean checkSpaceForTicket(TypeBus typeBus, List<Ticket> tickets){
        if (typeBus.getNumberOfSeats() - tickets.size() > 0){
            return true;
        }
        return false;
    }
}
