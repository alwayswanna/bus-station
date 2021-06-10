package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.NoSuchElementException;

@Controller
public class MainController {

    private final FlightService flightService;

    @Autowired
    public MainController(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Map<String, Object> model) {
        try {
            Iterable<BusFlights> busFlights = flightService.allFlights();
            model.put("flights", busFlights);
        } catch (NoSuchElementException noSuchElementException) {
            model.put("flights", null);
        }
        return "index";
    }

    @RequestMapping(value = "/flight/{id}", method = RequestMethod.GET)
    public String aboutFlight(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        try {
            BusFlights flight = flightService.returnFlightById(id);
            model.put("flight", flight);
        } catch (NoSuchElementException noSuchElementException) {
            model.put("flight", null);
        }
        return "aboutFlight";
    }

    @RequestMapping(value = "/flights/arrival", method = RequestMethod.GET)
    public String getAllArrivalFlights(Map<String, Object> model) {
        try {
            Iterable<BusFlights> flights = flightService.flightsByType("Прибывающий");
            model.put("flights", flights);
        } catch (NoSuchElementException noSuchElementException) {
            model.put("flights", null);
        }
        return "clientAnyFlights";
    }

    @RequestMapping(value = "/flights/departure", method = RequestMethod.GET)
    public String getAllDepartureFlights(Map<String, Object> model) {
        try {
            Iterable<BusFlights> flights = flightService.flightsByType("Отбывающий");
            model.put("flights", flights);
        } catch (NoSuchElementException noSuchElementException) {
            model.put("flights", null);
        }
        return "clientAnyFlights";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost() {
        return "redirect:/administration/administrator/flights";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return "login";
    }


}
