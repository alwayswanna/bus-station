package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.repositories.PassengersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PassengerService {

    private final PassengersRepo passengersRepo;

    @Autowired
    public PassengerService(PassengersRepo passengersRepo) {
        this.passengersRepo = passengersRepo;
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
}
