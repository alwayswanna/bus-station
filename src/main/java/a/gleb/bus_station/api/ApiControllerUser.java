package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.service.FlightUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ApiControllerUser {

    private final FlightUserService flightUserService;

    @Autowired
    public ApiControllerUser(FlightUserService flightUserService) {
        this.flightUserService = flightUserService;
    }

    @GetMapping("/flights")
    public Iterable<BusFlights> allFlightsGet(){
        return flightUserService.allFlights();
    }

    @GetMapping("/flight/{id}")
    public BusFlights getFlightById(@PathVariable Integer id){
        return flightUserService.busFlightsById(id);
    }

    @PostMapping("/ticket")
    public Ticket buyTicketForPassenger(@RequestBody BusFlights bf, @RequestBody PassengerPassport pp){
        return flightUserService.buyTicketOnSelectedFlight(bf, pp);
    }

}
