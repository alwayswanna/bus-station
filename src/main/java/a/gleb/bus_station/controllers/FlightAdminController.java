package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.*;
import a.gleb.bus_station.repositories.*;
import a.gleb.bus_station.service.SystemMethods;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/administrations")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class FlightAdminController {

    private final DriversRepo driversRepo;
    private final TypeBusRepo typeBusRepo;
    private final TypeFlightsRepo typeFlightsRepo;
    private final FlightRepo flightRepo;
    private final TicketRepo ticketRepo;
    private final PassengersRepo passengersRepo;

    public FlightAdminController(DriversRepo driversRepo, TypeBusRepo typeBusRepo, TypeFlightsRepo typeFlightsRepo, FlightRepo flightRepo, TicketRepo ticketRepo, PassengersRepo passengersRepo) {
        this.driversRepo = driversRepo;
        this.typeBusRepo = typeBusRepo;
        this.typeFlightsRepo = typeFlightsRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
        this.passengersRepo = passengersRepo;
    }

    @RequestMapping(value = "/administrator/add_flight", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorAddFlightGet(Map<String, Object> model) {
        Iterable<Drivers> drivers = driversRepo.findAll();
        Iterable<TypeBus> typeBuses = typeBusRepo.findAll();
        Iterable<TypeFlight> typeFlights = typeFlightsRepo.findAll();
        model.put("type", typeFlights);
        model.put("drivers", drivers);
        model.put("buses", typeBuses);
        return "addFlight";
    }

    @RequestMapping(value = "/administrator/add_flight", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorAddFlightPost(
            @RequestParam String fromCity,
            @RequestParam String toCity,
            @RequestParam String timeDeparture,
            @RequestParam String timeArrival,
            @RequestParam String dateFlight,
            @RequestParam String driverSurname,
            @RequestParam String busModel,
            @RequestParam String typeFlight,
            Map<String, Object> model,
            RedirectAttributes redirectAttributes) {
        if (fromCity.equals("") | toCity.equals("") | timeDeparture.equals("") | timeArrival.equals("") |
                dateFlight.equals("") | driverSurname.equals("") | busModel.equals("") | typeFlight.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/add_flight";
        } else {
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

            return "redirect:/administrations/administrator/flights";
        }
    }

    @RequestMapping(value = "/administrator/flight/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorEditFlightGet(@PathVariable(value = "id") Integer id,
                                     Map<String, Object> model) {
        if (SystemMethods.checkIdForFlight(id, model, flightRepo)) {
            return "redirect:/administrations/administrator/flights";
        } else {
            Optional<BusFlights> flight = flightRepo.findById(id);
            ArrayList<BusFlights> flightModel = new ArrayList<>();
            flight.ifPresent(flightModel::add);
            Iterable<Drivers> drivers = driversRepo.findAll();
            Iterable<TypeBus> typeBus = typeBusRepo.findAll();
            Iterable<TypeFlight> typeFlight = typeFlightsRepo.findAll();
            model.put("type", typeFlight);
            model.put("buses", typeBus);
            model.put("drivers", drivers);
            model.put("flight", flightModel);
        }
        return "administratorEditFlight";
    }

    @RequestMapping(value = "/administrator/flight/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorEditFlightPost(@PathVariable(value = "id") Integer id,
                                      @RequestParam String fromCity,
                                      @RequestParam String toCity,
                                      @RequestParam String timeDeparture,
                                      @RequestParam String timeArrival,
                                      @RequestParam String dateFlight,
                                      @RequestParam String driverSurname,
                                      @RequestParam String busModel,
                                      @RequestParam String typeOfFlight,
                                      Map<String, Object> model,
                                      RedirectAttributes redirectAttributes) {
        if (fromCity.equals("") | toCity.equals("") | timeDeparture.equals("") | timeArrival.equals("") |
                dateFlight.equals("") | driverSurname.equals("") | busModel.equals("") | typeOfFlight.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/flight/"+ id + "/edit";
        } else {
            Drivers driver = driversRepo.findByDriverSurname(driverSurname);
            TypeFlight typeFlight = typeFlightsRepo.findByTypeOfFlight(typeOfFlight);
            TypeBus typeBus = typeBusRepo.findByBusModel(busModel);
            BusFlights busFlights = flightRepo.findById(id).orElseThrow();
            busFlights.setDrivers(driver);
            busFlights.setTypeBus(typeBus);
            busFlights.setTypeFlight(typeFlight);
            busFlights.setFromCity(fromCity);
            busFlights.setToCity(toCity);
            busFlights.setTimeDeparture(timeDeparture);
            busFlights.setTimeArrival(timeArrival);
            busFlights.setDateFlight(dateFlight);
            flightRepo.save(busFlights);
            return "redirect:/administrations/administrator/flights";
        }
    }

    @RequestMapping(value = "/administrator/flight/{id}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorRemoveFlightPost(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        BusFlights busFlight = flightRepo.findById(id).orElseThrow();
        Iterable<Ticket> ticketsOnFlight = busFlight.getTickets();
        for (Ticket ticket:ticketsOnFlight){
            ticket.setBusFlights(flightRepo.findAllByFromCity("null"));
            Passengers passengers = ticket.getPassengers();
            passengers.setNumTicket("Рейс снят!");
            ticketRepo.save(ticket);
            passengersRepo.save(passengers);
        }
        flightRepo.delete(busFlight);
        return "redirect:/administrations/administrator/flights";
    }

    @RequestMapping(value = "/administrator/flights", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPageFlight(Map<String, Object> model) {
        Iterable<BusFlights> busFlights = flightRepo.findAll();
        model.put("flights", busFlights);
        return "administrationFlights";
    }
}
