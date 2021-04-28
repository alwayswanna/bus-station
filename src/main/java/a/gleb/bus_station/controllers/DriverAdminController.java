package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.Drivers;
import a.gleb.bus_station.repositories.DriversRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/administrations")
public class DriverAdminController {

    private final DriversRepo driversRepo;

    public DriverAdminController(DriversRepo driversRepo) {
        this.driversRepo = driversRepo;
    }

    @RequestMapping(value = "/administrator/drivers", method = RequestMethod.GET)
    public String administratorPageDrivers(Map<String, Object> model) {
        Iterable<Drivers> drivers = driversRepo.findAll();
        model.put("drivers", drivers);
        return "administratorDrivers";
    }

    @RequestMapping(value = "/administrator/drivers/add_driver", method = RequestMethod.GET)
    public String adminAddDriverGet(Map<String, Object> model) {
        return "administrationAddDriver";
    }

    @RequestMapping(value = "/administrator/drivers/add_driver", method = RequestMethod.POST)
    public String adminAddDriverPost(@RequestParam String driverName,
                                     @RequestParam String driverSurname,
                                     @RequestParam String driverPhone,
                                     Map<String, Object> model,
                                     RedirectAttributes redirectAttributes) {
        if (driverName.equals("") | driverSurname.equals("") | driverPhone.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/drivers/add_driver";
        } else {
            Drivers driver = new Drivers(driverName, driverSurname, driverPhone);
            driversRepo.save(driver);
            return "redirect:/administrations/administrator/drivers";
        }
    }

    @RequestMapping(value = "/administrator/drivers/{id}/edit", method = RequestMethod.GET)
    public String administratorDriverEditGet(@PathVariable(value = "id") Integer id,
                                             Map<String, Object> model) {
        Optional<Drivers> driver = driversRepo.findById(id);
        ArrayList<Drivers> driverModel = new ArrayList<>();
        driver.ifPresent(driverModel::add);
        model.put("driver", driverModel);
        return "administratorEditDriver";
    }

    @RequestMapping(value = "/administrator/drivers/{id}/edit", method = RequestMethod.POST)
    public String administratorDriverEditPost(@PathVariable(value = "id") Integer id,
                                              @RequestParam String driverName,
                                              @RequestParam String driverSurname,
                                              @RequestParam String driverPhone,
                                              Map<String, Object> model,
                                              RedirectAttributes redirectAttributes) {
        if (driverName.equals("") | driverSurname.equals("") | driverPhone.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/drivers/" + id + "/edit";
        } else {
            int driverId = id;
            Drivers driver = driversRepo.findById(driverId);
            driver.setDriverName(driverName);
            driver.setDriverSurname(driverSurname);
            driver.setDriverPhone(driverPhone);
            driversRepo.save(driver);
            return "redirect:/administrations/administrator/drivers";
        }
    }

    @RequestMapping(value = "/administrator/drivers/{id}/del", method = RequestMethod.GET)
    public String administratorDriverDelete(@PathVariable(value = "id") Integer id,
                                            Map<String, Object> model) {
        int driverId = id;
        Drivers driver = driversRepo.findById(driverId);
        Iterable<BusFlights> flights = driver.getBusFlights();
        for (BusFlights flight : flights) {
            flight.setDrivers(driversRepo.findById(1));
        }
        driversRepo.delete(driver);
        return "redirect:/administrations/administrator/drivers";
    }

    @RequestMapping(value = "/administrator/driver/{id}/get_flights", method = RequestMethod.GET)
    public String administratorDriverGetFlightsGet(@PathVariable(value = "id") Integer id,
                                                   Map<String, Object> model) {
        int driverId = id;
        Drivers drivers = driversRepo.findById(driverId);
        Iterable<BusFlights> flights = drivers.getBusFlights();
        model.put("driver", drivers);
        model.put("flights", flights);
        return "administratorDriverFlights";

    }

}
