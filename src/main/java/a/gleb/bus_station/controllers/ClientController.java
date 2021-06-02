package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.*;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import a.gleb.bus_station.repositories.TicketRepo;
import a.gleb.bus_station.service.SystemMethods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class ClientController {

    private final FlightRepo flightRepo;
    private final TicketRepo ticketRepo;
    private final PassengerPassportRepo passengerPassportRepo;
    private final PassengersRepo passengersRepo;

    public ClientController(FlightRepo flightRepo, TicketRepo ticketRepo,
                            PassengerPassportRepo passengerPassportRepo, PassengersRepo passengersRepo) {
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
        this.passengerPassportRepo = passengerPassportRepo;
        this.passengersRepo = passengersRepo;
    }

    @RequestMapping(value = "/flight/{id}/buy_ticket", method = RequestMethod.GET)
    public String buyTicketGet(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        if (SystemMethods.checkIdForFlight(id, model, flightRepo)) {
            return "redirect:/";
        } else {
            Optional<BusFlights> busFlights = flightRepo.findById(id);
            ArrayList<BusFlights> busFlightsModel = new ArrayList<>();
            busFlights.ifPresent(busFlightsModel::add);
            model.put("flight", busFlightsModel);
        }
        return "buyTicket";
    }

    /*@RequestMapping(value = "/flight/{id}/buy_ticket", method = RequestMethod.POST)
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
        if (passengerName.equals("") | passengerSurname.equals("") | passengerBirthday.equals("") |
                passengerPhone.equals("") | passengerDocNum.equals("") | passengerRegistration.equals("")) {
            String errorStr = "Вы заполнили не все поля! Обновите страницу";
            redirectAttributes.addFlashAttribute("error", errorStr);
            return "redirect:" + redirect;
        } else {

            Optional<BusFlights> busFlights = flightRepo.findById(id);
            ArrayList<BusFlights> busFlightsArrayList = new ArrayList<>();
            busFlights.ifPresent(busFlightsArrayList::add);
            BusFlights busFlight = busFlightsArrayList.get(0);
            model.put("flight", busFlight);

            if (SystemMethods.checkSpaceForTicket(busFlight.getTypeBus(), busFlight.getTickets())) {

                String uuid = UUID.randomUUID().toString();
                String numTicket = busFlight.getNumberFlightUnique() + "_" + uuid.substring(0, 4);
                PassengerPassport passengerPassport = new PassengerPassport(passengerName, passengerSurname,
                        passengerPhone, passengerDocNum, passengerRegistration, passengerBirthday);
                Passengers passenger = new Passengers();
                passenger.setPassengerInfo(passengerPassport);
                passenger.setNumTicket(numTicket);
                Ticket ticket = new Ticket();
                ticket.setPassengers(passenger);
                ticket.setTicketPassenger(passengerSurname);
                ticket.setTicketPlace("Сидячее");
                ticket.setBusFlights(busFlight);
                passengerPassportRepo.save(passengerPassport);
                passengersRepo.save(passenger);
                ticketRepo.save(ticket);
                String callBack = "Билет успешно приобретен." +
                        "\n Сверьте данные, в случае неточности обратитесь в службу техничесой поддержки автовокзала:" +
                        "\n Фамилия пассажира - " + passengerSurname +
                        "\n Имя пассажира - " + passengerName +
                        "\n Номер пасспорта пассажира - " + passengerDocNum +
                        "\n Номер билета пассажира - " + numTicket +
                        " Обновите страницу если хотите приобрести еще!";
                redirectAttributes.addFlashAttribute("message", callBack);
            } else {
                String errorMsg = "К сожалению все места на данный рейс заняты. Приносим свои извинения";
                redirectAttributes.addFlashAttribute("error", errorMsg);
                return "redirect:" + redirect;
            }
            return "redirect:" + redirect;
        }
    }*/

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
