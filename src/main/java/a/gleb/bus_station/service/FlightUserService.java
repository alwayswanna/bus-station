package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import a.gleb.bus_station.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class FlightUserService {

    private final FlightRepo flightRepo;
    private final PassengerPassportRepo passengerPassportRepo;
    private final PassengersRepo passengersRepo;
    private final TicketRepo ticketRepo;

    @Autowired
    public FlightUserService(FlightRepo flightRepo, TicketRepo ticketRepo, PassengerPassportRepo passengerPassportRepo, PassengersRepo passengersRepo) {
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
        this.passengerPassportRepo = passengerPassportRepo;
        this.passengersRepo = passengersRepo;
    }

    public Iterable<BusFlights> allFlights(){
        return flightRepo.findAll();
    }

    public BusFlights busFlightsById(Integer id){
        if (flightRepo.findAllById(id) == null){
            throw new NoSuchElementException();
        }else{
            return flightRepo.findAllById(id).iterator().next();
        }
    }

    public Ticket buyTicketOnSelectedFlight(BusFlights fl, PassengerPassport ps){
        Ticket ticket = new Ticket();
        Iterable<BusFlights> selectedFlightsIt = flightRepo.findAllById(fl.getId());
        if (selectedFlightsIt == null){
            throw new NoSuchElementException();
        }else{
            if (ps.getPassengerName().equals("") | ps.getPassengerSurname().equals("") | ps.getPassengerBirthday().equals("")
            | ps.getPassengerPhone().equals("") | ps.getPassengerDocNum().equals("") | ps.getPassengerRegistration().equals("")){
                return null;
            }else{
                if (SystemMethods.checkSpaceForTicket(fl.getTypeBus(), fl.getTickets())) {
                    String uuid = UUID.randomUUID().toString();
                    String numTicket = fl.getNumberFlightUnique() + "_" + uuid.substring(0, 4);
                    BusFlights selectedFlight = selectedFlightsIt.iterator().next();
                    List<Ticket> ticketOnFlight = selectedFlight.getTickets();
                    Passengers passenger = new Passengers();
                    passenger.setPassengerInfo(ps);
                    passenger.setNumTicket(numTicket);
                    ticket.setPassengers(passenger);
                    ticket.setTicketPlace("Сидячее");
                    ticket.setBusFlights(fl);
                    ticketOnFlight.add(ticket);
                    passengerPassportRepo.save(ps);
                    passengersRepo.save(passenger);
                    ticketRepo.save(ticket);
                    return ticket;
                }
            }
        }
     return ticket;
    }

}
