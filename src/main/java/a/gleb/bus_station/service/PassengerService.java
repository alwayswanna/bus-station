package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.repositories.PassengersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
