package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import a.gleb.bus_station.repositories.TicketRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/administrations")
public class PassengerAdminController {

    private final PassengerPassportRepo passengerPassportRepo;
    private final PassengersRepo passengersRepo;
    private final FlightRepo flightRepo;
    private final TicketRepo ticketRepo;

    public PassengerAdminController(PassengerPassportRepo passengerPassportRepo, PassengersRepo passengersRepo,
                                    FlightRepo flightRepo, TicketRepo ticketRepo) {
        this.passengerPassportRepo = passengerPassportRepo;
        this.passengersRepo = passengersRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    @RequestMapping(value = "/administrator/passengers", method = RequestMethod.GET)
    public String administratorPassengersGet(Map<String, Object> model){
        Iterable<PassengerPassport> passengers = passengerPassportRepo.findAll();
        Iterable<Passengers> passengersNumTicket = passengersRepo.findAll();
        model.put("passenger_ticket", passengersNumTicket);
        model.put("passengers", passengers);
        return "administratorPassengers";
    }

    @RequestMapping(value = "/administrator/passenger/{id}/del", method = RequestMethod.GET)
    public String administratorPassengerDelete(@PathVariable(value = "id") Integer id,
                                               Map<String, Object> model){
        int passengerId = id;
        PassengerPassport passenger = passengerPassportRepo.findById(passengerId);
        passengerPassportRepo.delete(passenger);
        return "redirect:/administrations/administrator/passengers";
    }

    @RequestMapping(value = "/administrator/passenger/{id}/edit_data", method = RequestMethod.GET)
    public String administratorPassengerEditGet(@PathVariable(value = "id")Integer id, Map<String, Object> model){
        int passengerId = id;
        PassengerPassport passengerPassport = passengerPassportRepo.findById(passengerId);
        Passengers passenger = passengerPassport.getPassengers();
        Iterable<BusFlights> flight = flightRepo.findAll();
        BusFlights currentBusFlight = passenger.getTicket().getBusFlights();
        model.put("currentBusFlight", currentBusFlight);
        model.put("flight", flight);
        model.put("passenger_info", passenger);
        model.put("passenger", passengerPassport);
        return "administrationEditPassenger";
    }

    @RequestMapping(value = "/administrator/passenger/{id}/edit", method = RequestMethod.POST)
    public String administratorPassengerEditPost(@PathVariable(value = "id") Integer id,
                                                 @RequestParam String passengerSurname,
                                                 @RequestParam String passengerName,
                                                 @RequestParam String passengerPhone,
                                                 @RequestParam String passengerDocNum,
                                                 @RequestParam String passengerBirthday,
                                                 @RequestParam String passengerRegistration,
                                                 @RequestParam String numberFlightUnique,
                                                 Map<String, Object> model){
        int passengerId = id;
        String uuid = UUID.randomUUID().toString();
        PassengerPassport passengerPassport = passengerPassportRepo.findById(passengerId);
        Passengers passenger = passengerPassport.getPassengers();
        Ticket ticket = passenger.getTicket();
        BusFlights busFlights = flightRepo.findByNumberFlightUnique(numberFlightUnique);
        String numTicket_new = numberFlightUnique + "_" + uuid.substring(0,4);

        passenger.setNumTicket(numTicket_new);
        passenger.setPassengerInfo(passengerPassport);
        ticket.setPassengers(passenger);
        ticket.setBusFlights(busFlights);
        ticket.setTicketPassenger(passengerSurname);
        passengerPassport.setPassengerSurname(passengerSurname);
        passengerPassport.setPassengerName(passengerName);
        passengerPassport.setPassengerPhone(passengerPhone);
        passengerPassport.setPassengerDocNum(passengerDocNum);
        passengerPassport.setPassengerBirthday(passengerBirthday);
        passengerPassport.setPassengerRegistration(passengerRegistration);
        passengerPassportRepo.save(passengerPassport);
        return "redirect:/administrations/administrator/passengers";
    }

    @RequestMapping(value = "/administrator/buy_ticket", method = RequestMethod.GET)
    public String administratorBuyTicketGet(Map<String, Object> model){
        Iterable<BusFlights> flights = flightRepo.findAll();
        model.put("flights", flights);
        return "administrationBuyTicket";
    }

    @RequestMapping(value = "/administrator/buy_ticket", method = RequestMethod.POST)
    public String administratorBuyTicketPost(@RequestParam String passengerName,
                                             @RequestParam String passengerSurname,
                                             @RequestParam String passengerPhone,
                                             @RequestParam String passengerDocNum,
                                             @RequestParam String passengerRegistration,
                                             @RequestParam String passengerBirthday,
                                             @RequestParam String numberFlightUnique,
                                             Map<String, Object> model,
                                             RedirectAttributes redirectAttributes){
        String uuid = UUID.randomUUID().toString();
        String redirectLink = "/administrations/administrator/buy_ticket";
        if (passengerName.equals("") | passengerSurname.equals("") | passengerBirthday.equals("") |
                passengerPhone.equals("") | passengerDocNum.equals("") | passengerBirthday.equals("")) {
            String errorStr = "Вы заполнили не все поля! Обновите страницу";
            redirectAttributes.addFlashAttribute("error", errorStr);
            return "redirect:" + redirectLink;
        } else {
            BusFlights flight = flightRepo.findByNumberFlightUnique(numberFlightUnique);
            PassengerPassport passengerPassport = new PassengerPassport(passengerName, passengerSurname,
                    passengerPhone, passengerDocNum, passengerRegistration, passengerBirthday);
            Passengers passenger = new Passengers();
            passenger.setPassengerInfo(passengerPassport);
            passenger.setNumTicket(
                    flight.getNumberFlightUnique() + "_" + uuid.substring(0,4)
            );
            Ticket ticket = new Ticket();
            ticket.setTicketPlace("Сидячее");
            ticket.setTicketPassenger(passengerSurname);
            ticket.setPassengers(passenger);
            ticket.setBusFlights(flight);
            passengerPassportRepo.save(passengerPassport);
            passengersRepo.save(passenger);
            ticketRepo.save(ticket);
            String callBack = "Билет успешно приобретен." +
                    "\n Сверьте данные, в случае неточности обратитесь в службу техничесой поддержки автовокзала:" +
                    "\n Фамилия пассажира - " + passengerSurname +
                    "\n Имя пассажира - " + passengerName +
                    "\n Номер пасспорта пассажира - " + passengerDocNum +
                    "\n Номер билета пассажира - " + flight.getNumberFlightUnique() + "_" + uuid.substring(0,4) +
                    " Обновите страницу если хотите приобрести еще!";
            redirectAttributes.addFlashAttribute("message", callBack);
        }

        return "redirect:" + redirectLink;
    }
}
