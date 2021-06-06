package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import a.gleb.bus_station.repositories.PassengersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PassengerService {

    private final PassengersRepo passengersRepo;
    private final FlightRepo flightRepo;
    private final PassengerPassportRepo passengerPassportRepo;

    @Autowired
    public PassengerService(PassengersRepo passengersRepo, FlightRepo flightRepo, PassengerPassportRepo passengerPassportRepo) {
        this.passengersRepo = passengersRepo;
        this.flightRepo = flightRepo;
        this.passengerPassportRepo = passengerPassportRepo;
    }

    public void savePassenger(Passengers passenger){
        passengersRepo.save(passenger);
    }

    public Iterable<Passengers> getAllPassengers(){
        Iterable<Passengers> passengers = passengersRepo.findAll();
        if (passengers.iterator().next() == null){
            throw new NoSuchElementException();
        }else{
            return passengers;
        }
    }

    public void deleteSelectedPassenger(Integer id){
        Passengers selectedPassenger = passengersRepo.findAllById(id);
        if (selectedPassenger == null){
            throw new NoSuchElementException();
        }else {
            passengersRepo.delete(selectedPassenger);
        }
    }

    public List<Object> getEditPassengerData(Integer id){
        List<Object> response = new ArrayList<>();
        PassengerPassport passport = passengerPassportRepo.findAllById(id);
        if (passport == null){
            throw new NoSuchElementException();
        }else {
            Passengers passenger = passport.getPassengers();
            Iterable<BusFlights> listOfFlights = flightRepo.findAll();
            BusFlights currentFlight = passenger.getTicket().getBusFlights();
            response.add(passport);
            response.add(passenger);
            response.add(listOfFlights);
            response.add(currentFlight);
            return response;
        }
    }
}
