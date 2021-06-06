package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public Iterable<BusFlights> allFlights(){
        return flightService.allFlights();
    }

    @RequestMapping(value = "/flight/{id}", method = RequestMethod.GET)
    public BusFlights selectedFlight(@PathVariable Integer id){
        return flightService.returnFlightById(id);
    }

    @RequestMapping(value = "/flight", method = RequestMethod.POST)
    public BusFlights addNewBusFlight(@RequestBody BusFlights flight){
        return flightService.addNewFlight(flight);
    }

    @RequestMapping(value = "/flight", method = RequestMethod.PUT)
    public BusFlights updateSelectedFlight(@RequestBody BusFlights flight){
        return flightService.editSelectedFlight(flight);
    }

    @RequestMapping(value = "/flight/{id}", method = RequestMethod.DELETE)
    public Iterable<BusFlights> deleteSelectedFlight(@PathVariable Integer id){
        return flightService.deleteSelectedFlight(id);
    }


}
