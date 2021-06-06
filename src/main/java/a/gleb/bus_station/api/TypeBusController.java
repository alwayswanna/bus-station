package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TypeBusController {

    private final BusService busService;

    @Autowired
    public TypeBusController(BusService busService) {
        this.busService = busService;
    }

    @RequestMapping(value = "/type_buses", method = RequestMethod.GET)
    public Iterable<TypeBus> allBuses(){
        return busService.allBuses();
    }

    @RequestMapping(value = "/type_bus/{id}", method = RequestMethod.GET)
    public TypeBus returnSelectedBus(@PathVariable Integer id){
        return busService.returnBusById(id);
    }

    @RequestMapping(value = "/type_bus/{id}", method = RequestMethod.DELETE)
    public Iterable<TypeBus> deleteSelectedBus(@PathVariable Integer id){
        return busService.deleteSelectedBus(id);
    }

    @RequestMapping(value = "/type_bus", method = RequestMethod.POST)
    public TypeBus addNewTypeBus(@RequestBody TypeBus bus){
        return busService.addNewTypeBus(bus);
    }

    @RequestMapping(value = "/type_bus", method = RequestMethod.PUT)
    public TypeBus editSelectedBus(@RequestBody TypeBus bus){
        return busService.editSelectedBus(bus);
    }


}
