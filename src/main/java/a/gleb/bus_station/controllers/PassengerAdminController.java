package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.service.FlightService;
import a.gleb.bus_station.service.PassengerPassportService;
import a.gleb.bus_station.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequestMapping(value = "/administrations")
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class PassengerAdminController {


    private final PassengerService passengerService;
    private final PassengerPassportService passengerPassportService;
    private final FlightService flightService;

    @Autowired
    public PassengerAdminController(PassengerService passengerService, PassengerPassportService passengerPassportService, FlightService flightService) {
        this.passengerService = passengerService;
        this.passengerPassportService = passengerPassportService;
        this.flightService = flightService;
    }

    @RequestMapping(value = "/administrator/passengers", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPassengersGet(Map<String, Object> model) {
        try{
            Iterable<PassengerPassport> passengers = passengerPassportService.getAllPassengersInfo();
            Iterable<Passengers> passengersNumTicket = passengerService.getAllPassengers();
            model.put("passenger_ticket", passengersNumTicket);
            model.put("passengers", passengers);
        }catch (NoSuchElementException noSuchElementException){
            model.put("passengers", null);
            model.put("passenger_ticket", null);
        }
        return "administratorPassengers";
    }

    @RequestMapping(value = "/administrator/passenger/{id}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPassengerDelete(@PathVariable(value = "id") Integer id,
                                               Map<String, Object> model) {
        passengerService.deleteSelectedPassenger(id);
        return "redirect:/administrations/administrator/passengers";
    }

   @RequestMapping(value = "/administrator/passenger/{id}/edit_data", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPassengerEditGet(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        List<Object> responseList = passengerService.getEditPassengerData(id);
        PassengerPassport passengerPassport = (PassengerPassport) responseList.get(0);
        Passengers passenger = (Passengers) responseList.get(1);
        Iterable<BusFlights> flight = (Iterable<BusFlights>) responseList.get(2);
        BusFlights currentBusFlight = (BusFlights) responseList.get(3);
        model.put("currentBusFlight", currentBusFlight);
        model.put("flight", flight);
        model.put("passenger_info", passenger);
        model.put("passenger", passengerPassport);
        return "administrationEditPassenger";
    }

    // TODO : there are bad logic!
   @RequestMapping(value = "/administrator/passenger/{id}/edit_data", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPassengerEditPost(@PathVariable(value = "id") Integer id,
                                                 @RequestParam String passengerSurname,
                                                 @RequestParam String passengerName,
                                                 @RequestParam String passengerPhone,
                                                 @RequestParam String passengerDocNum,
                                                 @RequestParam String passengerBirthday,
                                                 @RequestParam String passengerRegistration,
                                                 @RequestParam String numberFlightUnique,
                                                 Map<String, Object> model,
                                                 RedirectAttributes redirectAttributes) {
        String redirectLink = "/administrations/administrator/passenger/" + id + "/edit_data";
        if (passengerName.equals("") | passengerSurname.equals("") | passengerBirthday.equals("") |
                passengerPhone.equals("") | passengerDocNum.equals("") | numberFlightUnique.equals("")) {
            String errorStr = "Вы заполнили не все поля! Обновите страницу";
            redirectAttributes.addFlashAttribute("error", errorStr);
            return "redirect:" + redirectLink;
        } else {
            PassengerPassport data = passengerPassportService.getPassengerById(id);
            PassengerPassport dataPassenger = new PassengerPassport(passengerName, passengerSurname, passengerPhone, passengerDocNum, passengerRegistration, passengerBirthday, data.getPassengers());
            dataPassenger.setId(id);
            PassengerPassport result = passengerPassportService.editSelectedPassenger(dataPassenger);

                return "redirect:/administrations/administrator/passengers";
            }
    }

    @RequestMapping(value = "/administrator/buy_ticket", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorBuyTicketGet(Map<String, Object> model) {
        Iterable<BusFlights> flights = flightService.allFlights();
        model.put("flights", flights);
        return "administrationBuyTicket";
    }

    @RequestMapping(value = "/administrator/buy_ticket", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorBuyTicketPost(@RequestParam String passengerName,
                                             @RequestParam String passengerSurname,
                                             @RequestParam String passengerPhone,
                                             @RequestParam String passengerDocNum,
                                             @RequestParam String passengerRegistration,
                                             @RequestParam String passengerBirthday,
                                             @RequestParam String numberFlightUnique,
                                             Map<String, Object> model,
                                             RedirectAttributes redirectAttributes) {
        String uuid = UUID.randomUUID().toString();
        String redirectLink = "/administrations/administrator/buy_ticket";
        if (passengerName.equals("") | passengerSurname.equals("") | passengerBirthday.equals("") |
                passengerPhone.equals("") | passengerDocNum.equals("") | passengerRegistration.equals("") | numberFlightUnique.equals("")) {
            String errorStr = "Вы заполнили не все поля! Обновите страницу";
            redirectAttributes.addFlashAttribute("error", errorStr);
            return "redirect:" + redirectLink;
        } else {
            BusFlights flight = flightService.returnFlightByUniqueNumber(numberFlightUnique);
            PassengerPassport result = passengerPassportService.buyTicketForPassenger(flight.getId(), new PassengerPassport(passengerName, passengerSurname, passengerPhone,
                    passengerDocNum, passengerRegistration, passengerBirthday, null));
                String callBack = "Билет успешно приобретен." +
                        "\n Сверьте данные, в случае неточности обратитесь в службу техничесой поддержки автовокзала:" +
                        "\n Фамилия пассажира - " + passengerSurname +
                        "\n Имя пассажира - " + passengerName +
                        "\n Номер пасспорта пассажира - " + passengerDocNum +
                        "\n Номер билета пассажира - " + flight.getNumberFlightUnique() + "_" + uuid.substring(0, 4) +
                        " Обновите страницу если хотите приобрести еще!";
                redirectAttributes.addFlashAttribute("message", callBack);
                return "redirect:" + redirectLink;
            }
        }

}
