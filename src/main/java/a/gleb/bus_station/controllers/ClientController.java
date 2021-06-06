package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.service.FlightService;
import a.gleb.bus_station.service.PassengerPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class ClientController {

    private final FlightService flightService;
    private final PassengerPassportService passengerPassportService;

    @Autowired
    public ClientController(FlightService flightService, PassengerPassportService passengerPassportService) {
        this.flightService = flightService;
        this.passengerPassportService = passengerPassportService;
    }

    @RequestMapping(value = "/flight/{id}/buy_ticket", method = RequestMethod.GET)
    public String buyTicketGet(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        BusFlights selectedFlight = flightService.returnFlightById(id);
        if (selectedFlight == null){
            return "redirect:/";
        }else{
            model.put("flight", selectedFlight);
            return "buyTicket";
        }
    }

    @RequestMapping(value = "/flight/{id}/buy_ticket", method = RequestMethod.POST)
    public String buyTicketPost(@PathVariable(value = "id") Integer id,
                                @RequestParam String passengerName,
                                @RequestParam String passengerSurname,
                                @RequestParam String passengerPhone,
                                @RequestParam String passengerDocNum,
                                @RequestParam String passengerRegistration,
                                @RequestParam String passengerBirthday,
                                Map<String, Object> model,
                                RedirectAttributes redirectAttributes) {
        String redirect = "/flight/" + id + "/buy_ticket";
        PassengerPassport addPassenger = passengerPassportService.buyTicketForPassenger(id, new PassengerPassport(passengerName, passengerSurname, passengerPhone, passengerDocNum, passengerRegistration, passengerBirthday, null));
        if (addPassenger == null){
            String callBackError = "К сожалению данный рейс не сущетсвует или все билеты уже закончились";
            redirectAttributes.addFlashAttribute("error", callBackError);
            return "redirect:" + redirect;
        }else{
            String numTicket = addPassenger.getPassengers().getNumTicket();
            String successCallBack = "Билет успешно приобретен." +
                    "\n Сверьте данные, в случае неточности обратитесь в службу техничесой поддержки автовокзала:" +
                    "\n Фамилия пассажира - " + passengerSurname +
                    "\n Имя пассажира - " + passengerName +
                    "\n Номер пасспорта пассажира - " + passengerDocNum +
                    "\n Номер билета пассажира - " + numTicket +
                    " Обновите страницу если хотите приобрести еще!";
            redirectAttributes.addFlashAttribute("message", successCallBack);
            return "redirect:" + redirect;
        }
    }

    @RequestMapping(value = "/check_ticket", method = RequestMethod.GET)
    public String checkTicketGet(Map<String, Object> model) {
        return "checkTicket";
    }

    @RequestMapping(value = "/check_ticket", method = RequestMethod.POST)
    public String checkTicketPost(@RequestParam String passengerDocNum,
                                  @RequestParam String numTicket,
                                  Map<String, Object> model,
                                  RedirectAttributes redirectAttributes) {
        List<Object> models = passengerPassportService.checkTicket(passengerDocNum, numTicket);
        if (models.get(0).equals("Empty data from user")){
            redirectAttributes.addFlashAttribute("error", "You are input empty data");
            return "redirect:/check_ticket";
        }else if(models.get(0).equals("Incorrect data of document number") | models.get(0).equals("Incorrect ticket data")){
            redirectAttributes.addFlashAttribute("error", "You are input incorrect data for ticket or passenger document number");
            return "redirect:/check_ticket";
        }else{
            Passengers passenger = (Passengers) models.get(0);
            PassengerPassport passport = (PassengerPassport) models.get(1);
            Ticket ticket = (Ticket) models.get(2);
            BusFlights flight = (BusFlights) models.get(3);
            model.put("passport", passport);
            model.put("ticket", ticket);
            model.put("passenger", passenger);
            model.put("flight", flight);
            return "checkTicket";
        }
    }
}
