package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.BusDriver;
import a.gleb.bus_station.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping(value = "/driver", method = RequestMethod.POST)
    public BusDriver addNewDriver(@RequestBody BusDriver busDriver){
        return driverService.addNewDriver(busDriver);
    }

    @RequestMapping(value = "/driver/{id}", method = RequestMethod.GET)
    public BusDriver singleDriver(@PathVariable Integer id){
        return driverService.getSelectedDriver(id);
    }

    @RequestMapping(value = "/drivers", method = RequestMethod.GET)
    public Iterable<BusDriver> allDrivers(){
        return driverService.getAllDrivers();
    }

    @RequestMapping(value = "/drivers", method = RequestMethod.PUT)
    public BusDriver editBusDriver(@RequestBody BusDriver driver){
        return driverService.editSelectedDriver(driver.getId(), driver);
    }

    @RequestMapping(value = "/driver/{id}", method = RequestMethod.DELETE)
    public Iterable<BusDriver> deleteSelectedDriver(@PathVariable Integer id){
        return driverService.deleteSelectedDriver(id);
    }

}
