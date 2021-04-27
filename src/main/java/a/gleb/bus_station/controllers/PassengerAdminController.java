package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping(value = "/administrations")
public class PassengerAdminController {

    private final PassengerPassportRepo passengerPassportRepo;
    private final PassengersRepo passengersRepo;
    private final FlightRepo flightRepo;

    public PassengerAdminController(PassengerPassportRepo passengerPassportRepo, PassengersRepo passengersRepo, FlightRepo flightRepo) {
        this.passengerPassportRepo = passengerPassportRepo;
        this.passengersRepo = passengersRepo;
        this.flightRepo = flightRepo;
    }

    @RequestMapping(value = "/administrator/passengers", method = RequestMethod.GET)
    public String administratorPassengersGet(Map<String, Object> model){
        Iterable<PassengerPassport> passengers = passengerPassportRepo.findAll();
        Iterable<Passengers> passengersNumTicket = passengersRepo.findAll();
        model.put("passenger_ticket", passengersNumTicket);
        model.put("passengers", passengers);
        return "administratorPassengers";
    }

    @RequestMapping(value = "/administrator/passenger/{id}/del", method = RequestMethod.GET)
    public String administratorPassengerDelete(@PathVariable(value = "id") Integer id,
                                               Map<String, Object> model){
        int passengerId = id;
        PassengerPassport passenger = passengerPassportRepo.findById(passengerId);
        passengerPassportRepo.delete(passenger);
        return "redirect:/administrations/administrator/passengers";
    }

    @RequestMapping(value = "/administrator/{id}/edit_data", method = RequestMethod.GET)
    public String administratorPassengerEditGet(@PathVariable(value = "id")Integer id, Map<String, Object> model){
        int passengerId = id;
        PassengerPassport passengerPassport = passengerPassportRepo.findById(passengerId);
        Passengers passenger = passengerPassport.getPassengers();
        Iterable<BusFlights> flight = flightRepo.findAll();
        model.put("flight", flight);
        model.put("passenger_info", passenger);
        model.put("passenger", passengerPassport);
        return "administrationEditPassenger";
    }

    @RequestMapping(value = "/administrator/passenger/{id}/edit", method = RequestMethod.POST)
    public String administratorPassengerEditPost(){

        return "redirect:/administrations/administrator/passengers";
    }
}
