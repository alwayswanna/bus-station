package a.gleb.bus_station.controllers;

import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.service.SystemMethods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class ClientController {

    private final FlightRepo flightRepo;

    public ClientController(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    @RequestMapping(value = "/flight/{id}/buy_ticket", method = RequestMethod.GET)
    public String buyTicketGet(@PathVariable(value = "id") Integer id, Map<String, Object> model){
        if (SystemMethods.checkIdForFlight(id, model, flightRepo)) return "redirect:/";
        return "buyTicket";
    }



}
