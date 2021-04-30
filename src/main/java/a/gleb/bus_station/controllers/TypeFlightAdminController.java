package a.gleb.bus_station.controllers;


import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.TypeFlightsRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping(value = "/administrations")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class TypeFlightAdminController {

    private final TypeFlightsRepo typeFlightsRepo;
    private final FlightRepo flightRepo;

    public TypeFlightAdminController(TypeFlightsRepo typeFlightsRepo, FlightRepo flightRepo) {
        this.typeFlightsRepo = typeFlightsRepo;
        this.flightRepo = flightRepo;
    }

    @RequestMapping(value = "/administrator/type_flights", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorTypeFlightGet(Map<String, Object> model){
        Iterable<BusFlights> flights =  flightRepo.findAll();
        model.put("type_flight", flights);
        return "administrationTypeFlight";
    }

    @RequestMapping(value = "/administrator/type_flight/arrival", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorArrivalFlightGet(Map<String, Object> model){
        Iterable<BusFlights> flightsArrival = flightRepo.findAllByRouteType("Прибывающий");
        model.put("type_flight", flightsArrival);
        return "administrationTypeFlight";
    }

    @RequestMapping(value = "/administrator/type_flight/departure", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDepartureFlightGet(Map<String, Object> model){
        Iterable<BusFlights> flightsArrival = flightRepo.findAllByRouteType("Отбывающий");
        model.put("type_flight", flightsArrival);
        return "administrationTypeFlight";
    }
}
