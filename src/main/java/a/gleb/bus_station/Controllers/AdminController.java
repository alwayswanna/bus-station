package a.gleb.bus_station.Controllers;

import a.gleb.bus_station.Entity.*;
import a.gleb.bus_station.Repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/administrations")
public class AdminController {


    private final FlightRepo flightRepo;
    private final DriversRepo driversRepo;
    private final TypeBusRepo typeBusRepo;
    private final TypeFlightsRepo typeFlightsRepo;
    private final PassengerPassportRepo passengerPassportRepo;
    private final PassengersRepo passengersRepo;

    public AdminController(FlightRepo flightRepo, DriversRepo driversRepo,
                           TypeBusRepo typeBusRepo, TypeFlightsRepo typeFlightsRepo,
                           PassengerPassportRepo passengerPassportRepo, PassengersRepo passengersRepo) {
        this.passengerPassportRepo = passengerPassportRepo;
        this.passengersRepo = passengersRepo;
        this.typeBusRepo = typeBusRepo;
        this.flightRepo = flightRepo;
        this.driversRepo = driversRepo;
        this.typeFlightsRepo = typeFlightsRepo;
    }

    @RequestMapping(value = "/administrator")
    public String administratorPage(Map<String, Object> model){
        return "administrator";
    }


    @RequestMapping(value = "/add_passengerFull", method = RequestMethod.GET)
    public String adminAddPassengerFullInfoGet(Map<String, Object> model){
        return "addFullPassenger";
    }

    @RequestMapping(value = "/add_passengerFull", method = RequestMethod.POST)
    public String adminAddPassengerFullInfoPost(@RequestParam String numTicket,
                                                @RequestParam String passengerName,
                                                @RequestParam String passengerSurname,
                                                @RequestParam String passengerPhone,
                                                @RequestParam String passengerDocNum,
                                                @RequestParam String passengerRegistration,
                                                @RequestParam String passengerBirthday){
        Passengers passenger = new Passengers(numTicket);
        PassengerPassport passengerPassport = new PassengerPassport(passengerName, passengerSurname,
                                        passengerPhone, passengerDocNum, passengerRegistration, passengerBirthday);
        passenger.setPassengerInfo(passengerPassport);
        passengersRepo.save(passenger);
    return "redirect:/administrations/administrator";
    }

    @RequestMapping(value = "/add_typeFlight", method = RequestMethod.GET)
    public String adminAddTypeFlightGet(Map<String, Object> model){
        return "addTypeFlight";
    }

    @RequestMapping(value = "/add_typeFlight", method = RequestMethod.POST)
    public String adminAddTypeFlightPost(@RequestParam String type
                                        ,Map<String, Object> model){
        TypeFlight typeFlight = new TypeFlight(type);
        typeFlightsRepo.save(typeFlight);
    return "redirect:/administrations/administrator";
    }

    @RequestMapping(value = "/add_typeBus", method = RequestMethod.GET)
    public String adminAddTypeBusGet(Map<String, Object> model){
        return "addTypeBus";
    }

    @RequestMapping(value = "/add_typeBus", method = RequestMethod.POST)
    public String adminAddTypeBusPost(@RequestParam String type,
                                  @RequestParam Integer numberOfSeats,
                                  @RequestParam String busModel,
                                  Map<String, Object> model){
        TypeBus bus = new TypeBus(type, numberOfSeats, busModel);
        typeBusRepo.save(bus);
    return "redirect:/administrations/administrator";
    }


    @RequestMapping(value = "/add_driver", method = RequestMethod.GET)
    public String adminAddDriverGet(Map<String, Object> model){
        return "addDriver";
    }

    @RequestMapping(value = "/add_driver", method = RequestMethod.POST)
    public String adminAddDriverPost(@RequestParam String driverName,
                                     @RequestParam String driverSurname,
                                     @RequestParam String driverPhone,
                                     Map<String, Object> model){
        Drivers driver = new Drivers(driverName, driverSurname, driverPhone);
        driversRepo.save(driver);
    return "redirect:/administrations/administrator";
    }


    @RequestMapping(value = "/add_flight", method = RequestMethod.GET)
    public String adminAddFlightGet(Map<String, Object> model){
        return "addFlight";
    }

    @RequestMapping(value = "/add_flight", method = RequestMethod.POST)
    public String adminAddFlightPost(@RequestParam String routeType,
                                 @RequestParam String fromCity,
                                 @RequestParam String toCity,
                                 @RequestParam String timeDeparture,
                                 @RequestParam String timeArrival,
                                 Map<String, Object> model
                                 ){
        BusFlights busFlight = new BusFlights(routeType, fromCity, toCity,
                timeDeparture, timeArrival);
        flightRepo.save(busFlight);
    return "redirect:/administrations/administrator";
    }
}
