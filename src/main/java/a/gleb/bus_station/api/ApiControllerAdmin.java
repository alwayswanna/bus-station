package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.Drivers;
import a.gleb.bus_station.service.FlightAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class ApiControllerAdmin {

    private final FlightAdminService flightAdminService;

    @Autowired
    public ApiControllerAdmin(FlightAdminService flightAdminService) {
        this.flightAdminService = flightAdminService;
    }

    @PostMapping("/driver")
    public Drivers addNewDriver(@RequestBody Drivers driver){
        return flightAdminService.addNewDriver(driver);
    }

    @GetMapping("/flights")
    public Iterable<BusFlights> getAllFlights(){
        return flightAdminService.getAllFlights();
    }
}
