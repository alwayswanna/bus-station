package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.repositories.PassengerPassportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightAdminService {

    private final PassengerPassportRepo passengerPassportRepo;

    @Autowired
    public FlightAdminService(PassengerPassportRepo passengerPassportRepo) {
        this.passengerPassportRepo = passengerPassportRepo;
    }

    public Iterable<PassengerPassport> getAllPassengersInformation(){
        return passengerPassportRepo.findAll();
    }
}
