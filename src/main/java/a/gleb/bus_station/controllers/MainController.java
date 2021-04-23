package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.TypeBusRepo;
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
    private final TypeBusRepo typeBusRepo;

    public MainController(FlightRepo flightRepo, TypeBusRepo typeBusRepo) {
        this.flightRepo = flightRepo;
        this.typeBusRepo = typeBusRepo;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(Map<String, Object> model){
        Iterable<BusFlights> busFlights = flightRepo.findAll();
        model.put("flights",busFlights);
        return "index";
    }

    @RequestMapping(value = "/flight/{id}")
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



}
