package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.service.PassengerPassportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PassengersController {

    private final PassengerPassportService passengerPassportService;

    public PassengersController(PassengerPassportService passengerPassportService) {
        this.passengerPassportService = passengerPassportService;
    }

    @RequestMapping(value = "/passengers", method = RequestMethod.GET)
    public Iterable<PassengerPassport> allPassengers(){
        return passengerPassportService.getAllPassengersInfo();
    }

    @RequestMapping(value = "/flight/{id}/passenger", method = RequestMethod.POST)
    public PassengerPassport buyTicketForPassenger(@PathVariable Integer id, @RequestBody PassengerPassport passengerPassport){
        return passengerPassportService.buyTicketForPassenger(id, passengerPassport);
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.GET)
    public List<Object> getPassengerBySurname(String surname){
        return passengerPassportService.getPassengerBySurname(surname);
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.GET)
    public List<Object> getPassengerByDocNum(String docNum){
        return passengerPassportService.getPassengerByDocNum(docNum);
    }

    @RequestMapping(value = "/passenger/{id}", method = RequestMethod.DELETE)
    public Iterable<PassengerPassport> deleteSelectedPassenger(@PathVariable Integer id){
        return passengerPassportService.deleteSelectedPassenger(id);
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.PUT)
    public PassengerPassport editSelectedPassenger(@RequestBody PassengerPassport passengerPassport){
        return passengerPassportService.editSelectedPassenger(passengerPassport);
    }
}
