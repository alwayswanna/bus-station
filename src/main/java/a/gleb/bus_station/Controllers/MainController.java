package a.gleb.bus_station.Controllers;

import a.gleb.bus_station.Entity.BusFlights;
import a.gleb.bus_station.Entity.TypeBus;
import a.gleb.bus_station.Repositories.FlightRepo;
import a.gleb.bus_station.Repositories.TypeBusRepo;
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
        if (!flightRepo.existsById(id)){
            return "redirect:/";
        }
        Optional<BusFlights> flight = flightRepo.findById(id);
        ArrayList<BusFlights> busFlight = new ArrayList<>();
        flight.ifPresent(busFlight::add);
        model.put("flight", busFlight);
        return "aboutFlight";
    }


}
