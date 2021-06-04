package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class MainController {

    private final FlightService flightService;

    @Autowired
    public MainController(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Map<String, Object> model){
        Iterable<BusFlights> busFlights = flightService.allFlights();
        model.put("flights",busFlights);
        return "index";
    }

    @RequestMapping(value = "/flight/{id}", method = RequestMethod.GET)
    public String aboutFlight(@PathVariable(value = "id") Integer id, Map<String, Object> model){
        BusFlights flight = flightService.returnFlightById(id);
        model.put("flight", flight);
        return "aboutFlight";
    }

    @RequestMapping(value = "/flights/arrival", method = RequestMethod.GET)
    public String getAllArrivalFlights(Map<String, Object> model){
        Iterable<BusFlights> flights = flightService.flightsByType("Прибывающий");
        model.put("flights", flights);
        return "clientAnyFlights";
    }

    @RequestMapping(value = "/flights/departure", method = RequestMethod.GET)
    public String getAllDepartureFlights(Map<String, Object> model){
        Iterable<BusFlights> flights = flightService.flightsByType("Отбывающий");
        model.put("flights", flights);
        return "clientAnyFlights";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(){
        return "redirect:/administration/administrator/flights";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(){
        return "login";
    }



}
