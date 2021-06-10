package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.*;
import a.gleb.bus_station.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping(value = "/administrations")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class FlightAdminController {

    private final FlightService flightService;
    private final BusService busService;
    private final DriverService driverService;
    private final TypeFlightService typeFlightsService;
    private final TicketService ticketService;
    private final PassengerService passengerService;

    @Autowired
    public FlightAdminController(FlightService flightService, BusService busService, DriverService driverService, TypeFlightService typeFlightsService, TicketService ticketService,
                                 PassengerService passengerService) {
        this.flightService = flightService;
        this.busService = busService;
        this.driverService = driverService;
        this.typeFlightsService = typeFlightsService;
        this.ticketService = ticketService;
        this.passengerService = passengerService;
    }

    @RequestMapping(value = "/administrator/add_flight", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorAddFlightGet(Map<String, Object> model) {
        Iterable<BusDriver> drivers = driverService.getAllDrivers();
        Iterable<TypeBus> buses = busService.allBuses();
        Iterable<TypeFlight> typeFlights = typeFlightsService.allTypesOfFlights();
        model.put("type", typeFlights);
        model.put("drivers", drivers);
        model.put("buses", buses);
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
            BusDriver driver = driverService.driverBySurname(driverSurname);
            TypeFlight typeOfFlight = typeFlightsService.selectedTypeOfFlight(typeFlight);
            TypeBus typeBus = busService.busByNameModel(busModel);
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
            flightService.addNewFlight(new BusFlights(type, fromCity, toCity, timeDeparture, timeArrival, dateFlight, numberFlightUnique, driver,
                    typeBus, typeOfFlight, null));
            return "redirect:/administrations/administrator/flights";
        }
    }

    @RequestMapping(value = "/administrator/flight/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorEditFlightGet(@PathVariable(value = "id") Integer id,
                                     Map<String, Object> model) {
        BusFlights flightForEdit = flightService.returnFlightById(id);
        Iterable<TypeBus> buses = busService.allBuses();
        Iterable<BusDriver> drivers = driverService.getAllDrivers();
        Iterable<TypeFlight> typeFlights = typeFlightsService.allTypesOfFlights();
            model.put("type", typeFlights);
            model.put("buses", buses);
            model.put("drivers", drivers);
            model.put("flight", flightForEdit);
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
            String type;
            if (fromCity.equals("Ufa") | fromCity.equals("Уфа")) {
                type = "Отбывающий";
            } else if (!fromCity.equals("Ufa") | !fromCity.equals("Уфа")) {
                type = "Прибывающий";
            } else {
                type = "Проездной";
            }
            String numberFlightUnique = Character.toString(fromCity.charAt(0)) + Character.toString(toCity.charAt(0))
                    + "-" + Integer.toString((int) Math.random() * (100 - 55) + 55);
            BusDriver driver = driverService.driverBySurname(driverSurname);
            TypeFlight typeFlight = typeFlightsService.selectedTypeOfFlight(typeOfFlight);
            TypeBus typeBus = busService.busByNameModel(busModel);
            BusFlights flight = new BusFlights(type, fromCity, toCity, timeDeparture, timeArrival, dateFlight, numberFlightUnique,
                    driver, typeBus, typeFlight, flightService.returnFlightById(id).getTickets());
            flight.setId(id);
            flightService.editSelectedFlight(flight);
            return "redirect:/administrations/administrator/flights";
        }
    }

   @RequestMapping(value = "/administrator/flight/{id}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorRemoveFlightPost(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        BusFlights busFlight = flightService.returnFlightById(id);
        Iterable<Ticket> ticketsOnFlight = busFlight.getTickets();
        for (Ticket ticket:ticketsOnFlight){
            ticket.setBusFlights(flightService.findFlightByFromCity("null"));
            Passengers passengers = ticket.getPassengers();
            passengers.setNumTicket("Рейс снят!");
            ticketService.saveTicket(ticket);
            passengerService.savePassenger(passengers);
        }
        flightService.deleteSelectedFlight(id);
        return "redirect:/administrations/administrator/flights";
    }

    @RequestMapping(value = "/administrator/flights", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPageFlight(Map<String, Object> model) {
        Iterable<BusFlights> busFlights = flightService.allFlights();
        model.put("flights", busFlights);
        return "administrationFlights";
    }
}
