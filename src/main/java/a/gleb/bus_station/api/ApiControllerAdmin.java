package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.service.FlightAdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class ApiControllerAdmin {

    private final FlightAdminService flightAdminService;

    public ApiControllerAdmin(FlightAdminService flightAdminService) {
        this.flightAdminService = flightAdminService;
    }

    @GetMapping("passengers_information")
    public Iterable<PassengerPassport> allPassengersInformation(){
        return flightAdminService.getAllPassengersInformation();
    }
}
