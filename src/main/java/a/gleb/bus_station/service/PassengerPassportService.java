package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.exceptions.IncorrectPassengerInformationException;
import a.gleb.bus_station.exceptions.NoFreeSpaceException;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import a.gleb.bus_station.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class PassengerPassportService {

    private final PassengerPassportRepo passengerPassportRepo;
    private final TicketRepo ticketRepo;
    private final FlightRepo flightRepo;
    private final PassengersRepo passengersRepo;

    public PassengerPassportService(PassengerPassportRepo passengerPassportRepo, TicketRepo ticketRepo, FlightRepo flightRepo,
                                    PassengersRepo passengersRepo) {
        this.passengerPassportRepo = passengerPassportRepo;
        this.ticketRepo = ticketRepo;
        this.flightRepo = flightRepo;
        this.passengersRepo = passengersRepo;
    }

    public PassengerPassport buyTicketForPassenger(Integer id, PassengerPassport passengerPassport){
        BusFlights selectedFlight = flightRepo.findAllById(id);
        int busSeats = selectedFlight.getTypeBus().getNumberOfSeats();
        int tickets = selectedFlight.getTickets().size();
        if (tickets >= busSeats){
            throw new NoFreeSpaceException("NoFreeSpaceException: all tickets are over");
        }else{
            if (!SystemMethods.checkPassengerInformation(passengerPassport)){
                throw new IncorrectPassengerInformationException("IncorrectPassengerInformationException: some data of passenger is empty or incorrect");
            }else{
                Ticket ticket = new Ticket();
                Passengers passenger = new Passengers();
                passenger.setNumTicket(SystemMethods.generateUniqNumTicket(selectedFlight));
                ticket.setPassengers(passenger);
                ticket.setTicketPassenger(passengerPassport.getPassengerSurname());
                ticket.setTicketPlace("Sitting place");
                ticket.setBusFlights(selectedFlight);
                passenger.setPassengerInfo(passengerPassport);
                ticketRepo.save(ticket);
                passengersRepo.save(passenger);
                passengerPassportRepo.save(passengerPassport);
                return passengerPassport;
            }
        }
    }

    public Iterable<PassengerPassport> getAllPassengersInfo(){
        Iterable<PassengerPassport> allPassengers = passengerPassportRepo.findAll();
        if (allPassengers.iterator().next() == null){
            throw new NoSuchElementException();
        }else{
            return allPassengers;
        }
    }
}
