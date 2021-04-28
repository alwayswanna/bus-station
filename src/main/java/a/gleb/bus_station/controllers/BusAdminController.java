package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.repositories.TypeBusRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/administrations")
public class BusAdminController {

    private final TypeBusRepo busRepo;

    public BusAdminController(TypeBusRepo busRepo) {
        this.busRepo = busRepo;
    }

    @RequestMapping(value = "/administrator/buses", method = RequestMethod.GET)
    public String administratorBusesGet(Map<String, Object> model) {
        Iterable<TypeBus> buses = busRepo.findAll();
        model.put("buses", buses);
        return "administrationBuses";
    }

    @RequestMapping(value = "/administrator/buses/{id}/edit", method = RequestMethod.GET)
    public String administratorBusesEditGet(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        int busId = id;
        TypeBus typeBus = busRepo.findById(busId);
        Iterable<BusFlights> flightsOfBus = typeBus.getBusFlights();
        model.put("bus", typeBus);
        model.put("flights", flightsOfBus);
        return "administrationBusEdit";
    }

    @RequestMapping(value = "/administrator/buses/{id}/edit", method = RequestMethod.POST)
    public String administratorBusesEditPost(@PathVariable(value = "id") Integer id,
                                             @RequestParam String busModel,
                                             @RequestParam String numberOfSeats,
                                             @RequestParam String type,
                                             Map<String, Object> model,
                                             RedirectAttributes redirectAttributes) {
        int busId = id;
        String redirectLink = "/administrations/administrator/buses/" + busId + "/edit";
        if (busModel.equals("") | numberOfSeats.equals("") | type.equals("")) {
            String errorMsg = "Вы заполнили не все поля. Обновите страницу и повторите вновь.";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:" + redirectLink;
        } else {
            TypeBus bus = busRepo.findById(busId);
            bus.setBusModel(busModel);
            bus.setNumberOfSeats(Integer.parseInt(numberOfSeats));
            bus.setType(type);
            busRepo.save(bus);
            return "redirect:/administrations/administrator/buses/";
        }
    }

    @RequestMapping(value = "/administrator/buses/{id}/del", method = RequestMethod.GET)
    public String administratorRemoveBus(@PathVariable(value = "id") Integer id,
                                         Map<String, Object> model) {
        int busId = id;
        TypeBus typeBus = busRepo.findById(busId);
        Iterable<BusFlights> flights = typeBus.getBusFlights();
        for (BusFlights flight : flights) {
            flight.setTypeBus(busRepo.findById(1));

        }
        busRepo.delete(typeBus);
        return "redirect:/administrations/administrator/buses";

    }

    @RequestMapping(value = "/administrator/add_bus", method = RequestMethod.GET)
    public String administratorAddBusGet(Map<String, Object> model) {
        return "administrationAddBus";
    }

    @RequestMapping(value = "/administrator/add_bus", method = RequestMethod.POST)
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
            TypeBus bus = new TypeBus(type, seats, busModel);
            busRepo.save(bus);
            return "redirect:/administrations/administrator/buses";
        }
    }

}
