package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.*;
import a.gleb.bus_station.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/administrations")
public class AdminController {


    private final FlightRepo flightRepo;
    private final DriversRepo driversRepo;
    private final TypeBusRepo typeBusRepo;
    private final TypeFlightsRepo typeFlightsRepo;
    private final PassengerPassportRepo passengerPassportRepo;
    private final PassengersRepo passengersRepo;
    private final TicketRepo ticketRepo;

    public AdminController(FlightRepo flightRepo, DriversRepo driversRepo,
                           TypeBusRepo typeBusRepo, TypeFlightsRepo typeFlightsRepo,
                           PassengerPassportRepo passengerPassportRepo, PassengersRepo passengersRepo, TicketRepo ticketRepo) {
        this.passengerPassportRepo = passengerPassportRepo;
        this.ticketRepo = ticketRepo;
        this.passengersRepo = passengersRepo;
        this.typeBusRepo = typeBusRepo;
        this.flightRepo = flightRepo;
        this.driversRepo = driversRepo;
        this.typeFlightsRepo = typeFlightsRepo;
    }

    @RequestMapping(value = "/administrator/drivers", method = RequestMethod.GET)
    public String administratorPageDrivers(Map<String, Object> model) {
        Iterable<Drivers> drivers = driversRepo.findAll();
        model.put("drivers", drivers);
        return "administratorDrivers";
    }

    @RequestMapping(value = "/add_ticket", method = RequestMethod.GET)
    public String adminAddTicketGet(Map<String, Object> model) {
        return "addTicket";
    }

    @RequestMapping(value = "/add_ticket", method = RequestMethod.POST)
    public String adminAddTicketPost(@RequestParam String ticketPlace,
                                     @RequestParam String ticketPassenger,
                                     Map<String, Object> model) {
        Ticket ticket = new Ticket(ticketPlace, ticketPassenger);
        ticketRepo.save(ticket);
        return "redirect:/administrations/administrator";
    }

    @RequestMapping(value = "/add_passengerFull", method = RequestMethod.GET)
    public String adminAddPassengerFullInfoGet(Map<String, Object> model) {
        return "addFullPassenger";
    }

    @RequestMapping(value = "/add_passengerFull", method = RequestMethod.POST)
    public String adminAddPassengerFullInfoPost(@RequestParam String numTicket,
                                                @RequestParam String passengerName,
                                                @RequestParam String passengerSurname,
                                                @RequestParam String passengerPhone,
                                                @RequestParam String passengerDocNum,
                                                @RequestParam String passengerRegistration,
                                                @RequestParam String passengerBirthday) {
        Passengers passenger = new Passengers(numTicket);
        PassengerPassport passengerPassport = new PassengerPassport(passengerName, passengerSurname,
                passengerPhone, passengerDocNum, passengerRegistration, passengerBirthday);
        passenger.setPassengerInfo(passengerPassport);
        passengersRepo.save(passenger);
        return "redirect:/administrations/administrator";
    }

    @RequestMapping(value = "/add_typeFlight", method = RequestMethod.GET)
    public String adminAddTypeFlightGet(Map<String, Object> model) {
        return "addTypeFlight";
    }

    @RequestMapping(value = "/add_typeFlight", method = RequestMethod.POST)
    public String adminAddTypeFlightPost(@RequestParam String type
            , Map<String, Object> model) {
        TypeFlight typeFlight = new TypeFlight(type);
        typeFlightsRepo.save(typeFlight);
        return "redirect:/administrations/administrator";
    }

    @RequestMapping(value = "/add_typeBus", method = RequestMethod.GET)
    public String adminAddTypeBusGet(Map<String, Object> model) {
        return "addTypeBus";
    }

    @RequestMapping(value = "/add_typeBus", method = RequestMethod.POST)
    public String adminAddTypeBusPost(@RequestParam String type,
                                      @RequestParam Integer numberOfSeats,
                                      @RequestParam String busModel,
                                      Map<String, Object> model) {
        TypeBus bus = new TypeBus(type, numberOfSeats, busModel);
        typeBusRepo.save(bus);
        return "redirect:/administrations/administrator";
    }
}
