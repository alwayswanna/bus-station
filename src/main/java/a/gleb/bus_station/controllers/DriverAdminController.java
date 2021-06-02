package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusDriver;
import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.repositories.DriversRepo;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class DriverAdminController {

    private final DriversRepo driversRepo;

    public DriverAdminController(DriversRepo driversRepo) {
        this.driversRepo = driversRepo;
    }

    @RequestMapping(value = "/administrator/drivers", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPageDrivers(Map<String, Object> model) {
        Iterable<BusDriver> drivers = driversRepo.findAll();
        model.put("drivers", drivers);
        return "administratorDrivers";
    }

    /*@RequestMapping(value = "/administrator/drivers/add_driver", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String adminAddDriverGet(Map<String, Object> model) {
        return "administrationAddDriver";
    }

    @RequestMapping(value = "/administrator/drivers/add_driver", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
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
            BusDriver driver = new BusDriver(driverName, driverSurname, driverPhone, null);
            driversRepo.save(driver);
            return "redirect:/administrations/administrator/drivers";
        }
    }*/

    @RequestMapping(value = "/administrator/drivers/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDriverEditGet(@PathVariable(value = "id") Integer id,
                                             Map<String, Object> model) {
        Optional<BusDriver> driver = driversRepo.findById(id);
        ArrayList<BusDriver> driverModel = new ArrayList<>();
        driver.ifPresent(driverModel::add);
        Iterable<BusFlights> flights = driverModel.get(0).getBusFlights();
        model.put("flights", flights);
        model.put("driver", driverModel);
        return "administrationBusOrDriverEdit";
    }

    @RequestMapping(value = "/administrator/drivers/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
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
            BusDriver driver = driversRepo.findById(driverId);
            driver.setDriverName(driverName);
            driver.setDriverSurname(driverSurname);
            driver.setDriverPhone(driverPhone);
            driversRepo.save(driver);
            return "redirect:/administrations/administrator/drivers";
        }
    }

    @RequestMapping(value = "/administrator/drivers/{id}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDriverDelete(@PathVariable(value = "id") Integer id,
                                            Map<String, Object> model) {
        int driverId = id;
        BusDriver driver = driversRepo.findById(driverId);
        Iterable<BusFlights> flights = driver.getBusFlights();
        for (BusFlights flight : flights) {
            flight.setBusDriver(driversRepo.findById(1));
        }
        driversRepo.delete(driver);
        return "redirect:/administrations/administrator/drivers";
    }

    @RequestMapping(value = "/administrator/driver/{id}/get_flights", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDriverGetFlightsGet(@PathVariable(value = "id") Integer id,
                                                   Map<String, Object> model) {
        int driverId = id;
        BusDriver busDriver = driversRepo.findById(driverId);
        Iterable<BusFlights> flights = busDriver.getBusFlights();
        model.put("driver", busDriver);
        model.put("flights", flights);
        return "administratorDriverFlights";

    }

}
