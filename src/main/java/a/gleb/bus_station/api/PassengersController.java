package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.service.PassengerPassportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PassengersController {

    private final PassengerPassportService passengerPassportService;

    public PassengersController(PassengerPassportService passengerPassportService) {
        this.passengerPassportService = passengerPassportService;
    }

    @RequestMapping(value = "/passengers_info", method = RequestMethod.GET)
    public Iterable<PassengerPassport> allPassengers(){
        return passengerPassportService.getAllPassengersInfo();
    }

    @RequestMapping(value = "/flight/{id}/passenger", method = RequestMethod.POST)
    public PassengerPassport buyTicketForPassenger(@PathVariable Integer id, @RequestBody PassengerPassport passengerPassport){
        return passengerPassportService.buyTicketForPassenger(id, passengerPassport);
    }
}
