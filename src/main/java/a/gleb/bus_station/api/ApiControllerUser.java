package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class ApiControllerUser {

    private final FlightService flightService;

    @Autowired
    public ApiControllerUser(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    public Iterable<BusFlights> allFlightsGet(){
        return flightService.allFlights();
    }

    @GetMapping("/flight/{id}")
    public BusFlights getFlightById(@PathVariable Integer id){
        return flightService.busFlightsById(id);
    }
}
