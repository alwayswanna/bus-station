package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusDriver;
import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.service.DriverService;
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
@RequestMapping(value = "/administrations")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class DriverAdminController {

    private final DriverService driverService;

    @Autowired
    public DriverAdminController(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping(value = "/administrator/drivers", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPageDrivers(Map<String, Object> model) {
        Iterable<BusDriver> busDrivers = driverService.getAllDrivers();
        model.put("drivers", busDrivers);
        return "administratorDrivers";
    }

    @RequestMapping(value = "/administrator/drivers/add_driver", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String adminAddDriverGet(Map<String, Object> model) {
        return "administrationAddDriver";
    }

    @RequestMapping(value = "/administrator/drivers/add_driver", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String adminAddDriverPost(@RequestParam String driverName,
                                     @RequestParam String driverSurname,
                                     @RequestParam String driverPhone,
                                     @RequestParam String driverLicence,
                                     Map<String, Object> model,
                                     RedirectAttributes redirectAttributes) {
        if (driverName.equals("") | driverSurname.equals("") | driverPhone.equals("") | driverLicence.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/drivers/add_driver";
        } else {
            driverService.addNewDriver(new BusDriver(driverName, driverSurname, driverPhone, driverLicence, null));
            return "redirect:/administrations/administrator/drivers";
        }
    }

    @RequestMapping(value = "/administrator/drivers/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDriverEditGet(@PathVariable(value = "id") Integer id,
                                             Map<String, Object> model) {
        BusDriver driver = driverService.getSelectedDriver(id);
        Iterable<BusFlights> flights = driver.getBusFlights();
        model.put("flights", flights);
        model.put("driver", driver);
        return "administrationBusOrDriverEdit";
    }

    @RequestMapping(value = "/administrator/drivers/{id}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDriverEditPost(@PathVariable(value = "id") Integer id,
                                              @RequestParam String driverName,
                                              @RequestParam String driverSurname,
                                              @RequestParam String driverPhone,
                                              @RequestParam String driverLicense,
                                              Map<String, Object> model,
                                              RedirectAttributes redirectAttributes) {
        if (driverName.equals("") | driverSurname.equals("") | driverPhone.equals("") | driverLicense.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/drivers/" + id + "/edit";
        } else {
            driverService.editSelectedDriver(new BusDriver(driverName, driverSurname, driverPhone, driverLicense, driverService.getSelectedDriver(id).getBusFlights()));
            return "redirect:/administrations/administrator/drivers";
        }
    }

    @RequestMapping(value = "/administrator/drivers/{id}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDriverDelete(@PathVariable(value = "id") Integer id,
                                            Map<String, Object> model) {
        var res = driverService.deleteSelectedDriver(id);
        return "redirect:/administrations/administrator/drivers";
    }

    @RequestMapping(value = "/administrator/driver/{id}/get_flights", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorDriverGetFlightsGet(@PathVariable(value = "id") Integer id,
                                                   Map<String, Object> model) {
        BusDriver driver = driverService.getSelectedDriver(id);
        Iterable<BusFlights> flights = driver.getBusFlights();
        model.put("driver", driver);
        model.put("flights", flights);
        return "administratorDriverFlights";
    }
}
