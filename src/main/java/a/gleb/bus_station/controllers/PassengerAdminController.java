package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import a.gleb.bus_station.repositories.TicketRepo;
import a.gleb.bus_station.service.PassengerPassportService;
import a.gleb.bus_station.service.PassengerService;
import a.gleb.bus_station.service.SystemMethods;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
public class PassengerAdminController {

    private final PassengerPassportRepo passengerPassportRepo;
    private final PassengersRepo passengersRepo;
    private final FlightRepo flightRepo;
    private final TicketRepo ticketRepo;

    private final PassengerService passengerService;
    private final PassengerPassportService passengerPassportService;

    public PassengerAdminController(PassengerPassportRepo passengerPassportRepo, PassengersRepo passengersRepo,
                                    FlightRepo flightRepo, TicketRepo ticketRepo, PassengerPassportService passengerPassportService, PassengerService passengerService) {
        this.passengerPassportRepo = passengerPassportRepo;
        this.passengersRepo = passengersRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
        this.passengerService = passengerService;
        this.passengerPassportService = passengerPassportService;
    }

    @RequestMapping(value = "/administrator/passengers", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPassengersGet(Map<String, Object> model) {
        Iterable<PassengerPassport> passengers = passengerPassportService.getAllPassengersInfo();
        Iterable<Passengers> passengersNumTicket = passengerService.getAllPassengers();
        model.put("passenger_ticket", passengersNumTicket);
        model.put("passengers", passengers);
        return "administratorPassengers";
    }

    @RequestMapping(value = "/administrator/passenger/{id}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPassengerDelete(@PathVariable(value = "id") Integer id,
                                               Map<String, Object> model) {
        passengerService.deleteSelectedPassenger(id);
        return "redirect:/administrations/administrator/passengers";
    }

   /* @RequestMapping(value = "/administrator/passenger/{id}/edit_data", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorPassengerEditGet(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
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
    }*/

   /* @RequestMapping(value = "/administrator/passenger/{id}/edit_data", method = RequestMethod.POST)
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
            BusFlights busFlights = flightRepo.findByNumberFlightUnique(numberFlightUnique);

            if (SystemMethods.checkSpaceForTicket(busFlights.getTypeBus(), busFlights.getTickets())) {

                int passengerId = id;
                String uuid = UUID.randomUUID().toString();
                PassengerPassport passengerPassport = passengerPassportRepo.findById(passengerId);
                Passengers passenger = passengerPassport.getPassengers();
                Ticket ticket = passenger.getTicket();
                String numTicket_new = numberFlightUnique + "_" + uuid.substring(0, 4);

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
            } else {
                String errorMsg = "К сожалению все места на данный рейс заняты. Приносим свои извинения";
                redirectAttributes.addFlashAttribute("error", errorMsg);
                return "redirect:" + redirectLink;
            }
        }
    }*/

    @RequestMapping(value = "/administrator/buy_ticket", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMINISTRATOR')")
    public String administratorBuyTicketGet(Map<String, Object> model) {
        Iterable<BusFlights> flights = flightRepo.findAll();
        model.put("flights", flights);
        return "administrationBuyTicket";
    }

    /*@RequestMapping(value = "/administrator/buy_ticket", method = RequestMethod.POST)
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
            BusFlights flight = flightRepo.findByNumberFlightUnique(numberFlightUnique);

            if (SystemMethods.checkSpaceForTicket(flight.getTypeBus(), flight.getTickets())){

                PassengerPassport passengerPassport = new PassengerPassport(passengerName, passengerSurname,
                        passengerPhone, passengerDocNum, passengerRegistration, passengerBirthday);
                Passengers passenger = new Passengers();
                passenger.setPassengerInfo(passengerPassport);
                passenger.setNumTicket(
                        flight.getNumberFlightUnique() + "_" + uuid.substring(0, 4)
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
                        "\n Номер билета пассажира - " + flight.getNumberFlightUnique() + "_" + uuid.substring(0, 4) +
                        " Обновите страницу если хотите приобрести еще!";
                redirectAttributes.addFlashAttribute("message", callBack);
                return "redirect:" + redirectLink;
            }else{
                String errorMsg = "К сожалению все места на данный рейс заняты. Приносим свои извинения.";
                redirectAttributes.addFlashAttribute("error", errorMsg);
                return "redirect:" + redirectLink;
            }
        }
    }*/
}
