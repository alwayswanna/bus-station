package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/administrations")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class BusAdminController {

    private final BusService busService;

    @Autowired
    public BusAdminController(BusService busService) {
        this.busService = busService;
    }

    @RequestMapping(value = "/administrator/buses", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorBusesGet(Map<String, Object> model) {
        Iterable<TypeBus> buses = busService.allBuses();
        model.put("buses", buses);
        return "administrationBuses";
    }

    @RequestMapping(value = "/administrator/buses/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorBusesEditGet(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        TypeBus bus = busService.editSelectedBus(busService.returnBusById(id));
        Iterable<BusFlights> flights = bus.getBusFlights();
        model.put("bus", bus);
        model.put("flights", flights);
        return "administrationBusOrDriverEdit";
    }

    @RequestMapping(value = "/administrator/buses/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorBusesEditPost(@PathVariable(value = "id") Integer id,
                                             @RequestParam String busModel,
                                             @RequestParam String numberOfSeats,
                                             @RequestParam String type,
                                             Map<String, Object> model,
                                             RedirectAttributes redirectAttributes) {
        String redirectLink = "/administrations/administrator/buses/" + id + "/edit";
        if (busModel.equals("") | numberOfSeats.equals("") | type.equals("")) {
            String errorMsg = "Вы заполнили не все поля. Обновите страницу и повторите вновь.";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:" + redirectLink;
        } else {
            TypeBus bus = new TypeBus(type, Integer.parseInt(numberOfSeats), busModel, busService.returnBusById(id).getBusFlights());
            bus.setId(id);
            busService.editSelectedBus(bus);
            return "redirect:/administrations/administrator/buses/";
        }
    }

    @RequestMapping(value = "/administrator/buses/{id}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorRemoveBus(@PathVariable(value = "id") Integer id,
                                         Map<String, Object> model) {
        busService.deleteSelectedBus(id);
        return "redirect:/administrations/administrator/buses";

    }

    @RequestMapping(value = "/administrator/add_bus", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorAddBusGet(Map<String, Object> model) {
        return "administrationAddBus";
    }

    @RequestMapping(value = "/administrator/add_bus", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorAddBusPost(@RequestParam String busModel,
                                          @RequestParam String type,
                                          @RequestParam String numberOfSeats,
                                          Map<String, Object> model,
                                          RedirectAttributes redirectAttributes) {
        if (busModel.equals("") | type.equals("") | numberOfSeats.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/add_bus";
        } else {
            int seats;
            try{
                seats = Integer.parseInt(numberOfSeats);
            }catch (NumberFormatException e){
                String errorMsg = "Вы нерпавильно ввели количество мест, поле требует цифрогового значения!";
                redirectAttributes.addFlashAttribute("error", errorMsg);
                return "redirect:/administrations/administrator/add_bus";
            }
            TypeBus bus = new TypeBus(type, seats, busModel, null);
            busService.addNewTypeBus(bus);
            return "redirect:/administrations/administrator/buses";
        }
    }

}
