package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.service.FlightService;
import a.gleb.bus_station.service.PassengerPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /*@RequestMapping(value = "/check_ticket", method = RequestMethod.POST)
    public String checkTicketPost(@RequestParam String passengerDocNum,
                                  @RequestParam String numTicket,
                                  Map<String, Object> model,
                                  RedirectAttributes redirectAttributes) {
        PassengerPassport passport = passengerPassportRepo.findByPassengerDocNum(passengerDocNum);
        Passengers passenger = passengersRepo.findByNumTicket(numTicket);
        if (passport == null & passenger == null) {
            String errorMsg = "Вы не указали требуемых данных или данные оказались некорректными. Попробуйте повторить попытку вновь.";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/check_ticket";
        } else if (passport == null | passengerDocNum.equals("")) {
            PassengerPassport passengerPassport = passenger.getPassengerInfo();
            Ticket ticket = passenger.getTicket();
            BusFlights flight = ticket.getBusFlights();
            model.put("passport", passengerPassport);
            model.put("ticket", ticket);
            model.put("passenger", passenger);
            model.put("flight", flight);
            return "checkTicket";
        } else if (passenger == null | numTicket.equals("")) {
            Passengers passengerFormDoc = passport.getPassengers();
            Ticket ticket = passengerFormDoc.getTicket();
            BusFlights flight = ticket.getBusFlights();
            model.put("passport", passport);
            model.put("ticket", ticket);
            model.put("passenger", passengerFormDoc);
            model.put("flight", flight);
            return "checkTicket";
        }
        return "checkTicket";
    }*/
}
