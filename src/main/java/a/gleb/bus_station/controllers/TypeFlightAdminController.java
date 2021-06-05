package a.gleb.bus_station.controllers;


import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping(value = "/administrations")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class TypeFlightAdminController {

   private final FlightService flightService;

   @Autowired
    public TypeFlightAdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/administrator/type_flights", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorTypeFlightGet(Map<String, Object> model){
        Iterable<BusFlights> flights =  flightService.allFlights();
        model.put("type_flight", flights);
        return "administrationTypeFlight";
    }

    @RequestMapping(value = "/administrator/type_flight/arrival", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorArrivalFlightGet(Map<String, Object> model){
        Iterable<BusFlights> flightsArrival = flightService.returnFlightsByRouteType("Прибывающий");
        model.put("type_flight", flightsArrival);
        return "administrationTypeFlight";
    }

    @RequestMapping(value = "/administrator/type_flight/departure", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDepartureFlightGet(Map<String, Object> model){
        Iterable<BusFlights> flightsArrival = flightService.flightsByType("Отбывающий");
        model.put("type_flight", flightsArrival);
        return "administrationTypeFlight";
    }
}
