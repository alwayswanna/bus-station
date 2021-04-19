package a.gleb.bus_station.Controllers;

import a.gleb.bus_station.Entity.BusFlights;
import a.gleb.bus_station.Repositories.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/administrations")
public class AdminController {

    @Autowired
    FlightRepo flightRepo;

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
    return "redirect:/";
    }
}
