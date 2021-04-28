package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.service.SystemMethods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {

    private final FlightRepo flightRepo;

    public MainController(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Map<String, Object> model){
        Iterable<BusFlights> busFlights = flightRepo.findAll();
        model.put("flights",busFlights);
        return "index";
    }

    @RequestMapping(value = "/flight/{id}", method = RequestMethod.GET)
    public String aboutFlight(@PathVariable(value = "id") Integer id, Map<String, Object> model){
        if (SystemMethods.checkIdForFlight(id, model, flightRepo)) {
            return "redirect:/";
        }else {
            Optional<BusFlights> flight = flightRepo.findById(id);
            ArrayList<BusFlights> busFlight = new ArrayList<>();
            flight.ifPresent(busFlight::add);
            model.put("flight", busFlight);
        }
        return "aboutFlight";
    }

    @RequestMapping(value = "/flights/arrival", method = RequestMethod.GET)
    public String getAllArrivalFlights(Map<String, Object> model){
        Iterable<BusFlights> flights = flightRepo.findAllByRouteType("Прибывающий");
        model.put("flights", flights);
        return "clientAnyFlights";
    }

    @RequestMapping(value = "/flights/departure", method = RequestMethod.GET)
    public String getAllDepartureFlights(Map<String, Object> model){
        Iterable<BusFlights> flights = flightRepo.findAllByRouteType("Отбывающий");
        model.put("flights", flights);
        return "clientAnyFlights";
    }



}
