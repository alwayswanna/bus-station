package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.*;
import a.gleb.bus_station.repositories.*;
import a.gleb.bus_station.service.SystemMethods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Type;
import java.sql.Driver;
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

    @RequestMapping(value = "/administrator", method = RequestMethod.GET)
    public String administratorPage(Map<String, Object> model) {
        Iterable<BusFlights> busFlights = flightRepo.findAll();
        model.put("flights", busFlights);
        return "administrator";
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


    @RequestMapping(value = "/add_driver", method = RequestMethod.GET)
    public String adminAddDriverGet(Map<String, Object> model) {
        return "addDriver";
    }

    @RequestMapping(value = "/add_driver", method = RequestMethod.POST)
    public String adminAddDriverPost(@RequestParam String driverName,
                                     @RequestParam String driverSurname,
                                     @RequestParam String driverPhone,
                                     Map<String, Object> model) {
        Drivers driver = new Drivers(driverName, driverSurname, driverPhone);
        driversRepo.save(driver);
        return "redirect:/administrations/administrator";
    }


    @RequestMapping(value = "/add_flight", method = RequestMethod.GET)
    public String adminAddFlightGet(Map<String, Object> model) {
        Iterable<Drivers> drivers = driversRepo.findAll();
        Iterable<TypeBus> typeBuses = typeBusRepo.findAll();
        Iterable<TypeFlight> typeFlights = typeFlightsRepo.findAll();
        model.put("type", typeFlights);
        model.put("drivers", drivers);
        model.put("buses", typeBuses);
        return "addFlight";
    }

    @RequestMapping(value = "/add_flight", method = RequestMethod.POST)
    public String adminAddFlightPost(
            @RequestParam String fromCity,
            @RequestParam String toCity,
            @RequestParam String timeDeparture,
            @RequestParam String timeArrival,
            @RequestParam String dateFlight,
            @RequestParam String driverSurname,
            @RequestParam String busModel,
            @RequestParam String typeFlight,
            Map<String, Object> model
    ) {
        //int typeId = idType;
        //int driverId = id;
        Drivers driver = driversRepo.findByDriverSurname(driverSurname);
        TypeFlight typeOfFlight = typeFlightsRepo.findByTypeOfFlight(typeFlight);
        TypeBus typeBus = typeBusRepo.findByBusModel(busModel);
        String type;
        String numberFlightUnique = Character.toString(fromCity.charAt(0)) + Character.toString(toCity.charAt(0))
                + "-" + Integer.toString((int) Math.random() * (100 - 55) + 55);
        System.out.println(numberFlightUnique);
        if (fromCity.equals("Ufa") | fromCity.equals("Уфа")) {
            type = "Отбывающий";
        } else if (!fromCity.equals("Ufa") | !fromCity.equals("Уфа")) {
            type = "Прибывающий";
        } else {
            type = "Проездной";
        }
        BusFlights busFlight = new BusFlights(type, fromCity, toCity,
                timeDeparture, timeArrival, dateFlight, numberFlightUnique);
        busFlight.setDrivers(driver);
        busFlight.setTypeBus(typeBus);
        busFlight.setTypeFlight(typeOfFlight);
        flightRepo.save(busFlight);

        return "redirect:/administrations/administrator";
    }

    @RequestMapping(value = "/flight/{id}/edit", method = RequestMethod.GET)
    public String adminEditFlightGet(@PathVariable(value = "id") Integer id,
                                     Map<String, Object> model) {
        if (SystemMethods.checkIdForFlight(id, model, flightRepo)) {
            return "redirect:/administrations/administrator";
        } else {
            Optional<BusFlights> flight = flightRepo.findById(id);
            ArrayList<BusFlights> flightModel = new ArrayList<>();
            flight.ifPresent(flightModel::add);
            Drivers driver = flightModel.get(0).getDrivers();
            TypeBus typeBus = flightModel.get(0).getTypeBus();
            TypeFlight typeFlight = flightModel.get(0).getTypeFlight();
            model.put("type", typeFlight);
            model.put("buses", typeBus);
            model.put("drivers", driver);
            model.put("flight", flightModel);
        }
        return "administratorEditFlight";
    }

    @RequestMapping(value = "/flight/{id}/edit", method = RequestMethod.POST)
    public String adminEditFlightPost(@PathVariable(value = "id") Integer id,
                                      @RequestParam String fromCity,
                                      @RequestParam String toCity,
                                      @RequestParam String timeDeparture,
                                      @RequestParam String timeArrival,
                                      @RequestParam String dateFlight,
                                      Map<String, Object> model){
        BusFlights busFlights = flightRepo.findById(id).orElseThrow();
        busFlights.setFromCity(fromCity);
        busFlights.setToCity(toCity);
        busFlights.setTimeDeparture(timeDeparture);
        busFlights.setTimeArrival(timeArrival);
        busFlights.setDateFlight(dateFlight);
        flightRepo.save(busFlights);
        return "redirect:/administrations/administrator";
    }

    @RequestMapping(value = "/flight/{id}/del", method = RequestMethod.GET)
    public String adminRemoveFlightPost(@PathVariable(value = "id")Integer id, Map<String, Object> model){
        BusFlights busFlight = flightRepo.findById(id).orElseThrow();
        flightRepo.delete(busFlight);
        return "redirect:/administrations/administrator";
    }


}
