package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.exceptions.IncorrectPassengerInformationException;
import a.gleb.bus_station.exceptions.NoFreeSpaceException;
import a.gleb.bus_station.exceptions.NoSuchPassengerSurnameException;
import a.gleb.bus_station.exceptions.NoSuchPassengerWithDocNumberException;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import a.gleb.bus_station.repositories.TicketRepo;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (selectedFlight == null){
            throw new NoSuchElementException();
        }else {
            int busSeats = selectedFlight.getTypeBus().getNumberOfSeats();
            int tickets = selectedFlight.getTickets().size();
            if (tickets >= busSeats) {
                throw new NoFreeSpaceException("NoFreeSpaceException: all tickets are over");
            } else {
                if (!SystemMethods.checkPassengerInformation(passengerPassport)) {
                    throw new IncorrectPassengerInformationException("IncorrectPassengerInformationException: some data of passenger is empty or incorrect");
                } else {
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
    }

    public Iterable<PassengerPassport> getAllPassengersInfo(){
        Iterable<PassengerPassport> allPassengers = passengerPassportRepo.findAll();
        if (allPassengers.iterator().next() == null){
            throw new NoSuchElementException();
        }else{
            return allPassengers;
        }
    }

    public List<Object> getPassengerBySurname(String surname){
        Iterable<PassengerPassport> passengersWithSurname = passengerPassportRepo.findAllByPassengerSurname(surname);
        if (passengersWithSurname.iterator().next() == null){
            throw new NoSuchPassengerSurnameException("NoSuchPassengerSurnameException: there are no passengers with [SURNAME]: " + surname);
        }else{
            return SystemMethods.getObjects(passengersWithSurname);
        }
    }

    public List<Object> getPassengerByDocNum(String passengerDocNum){
        Iterable<PassengerPassport> passengerWithDocNum = passengerPassportRepo.findAllByPassengerDocNum(passengerDocNum);
        if (passengerWithDocNum.iterator().next() == null){
            throw new NoSuchPassengerWithDocNumberException("NoSuchPassengerWithDocNumberException: there are no passenger with [Document Number]: " + passengerDocNum);
        }else{
            return SystemMethods.getObjects(passengerWithDocNum);
        }
    }

    public Iterable<PassengerPassport> deleteSelectedPassenger(Integer id){
        PassengerPassport passenger = passengerPassportRepo.findAllById(id);
        if (passenger == null){
            throw new NoSuchElementException();
        }else{
            passengerPassportRepo.delete(passenger);
            return getAllPassengersInfo();
        }
    }

    public PassengerPassport editSelectedPassenger(PassengerPassport passengerPassport){
        PassengerPassport passenger = passengerPassportRepo.findAllById(passengerPassport.getId());
        if (passenger == null){
            throw new NoSuchElementException();
        }else {
            passenger.setPassengerName(passengerPassport.getPassengerName());
            passenger.setPassengerSurname(passengerPassport.getPassengerSurname());
            passenger.setPassengerBirthday(passengerPassport.getPassengerBirthday());
            passenger.setPassengerPhone(passengerPassport.getPassengerPhone());
            passenger.setPassengerDocNum(passengerPassport.getPassengerDocNum());
            passenger.setPassengerRegistration(passengerPassport.getPassengerRegistration());
            passenger.setPassengers(passengerPassport.getPassengers());
            passengerPassportRepo.save(passenger);
            return passenger;
        }
    }


}
