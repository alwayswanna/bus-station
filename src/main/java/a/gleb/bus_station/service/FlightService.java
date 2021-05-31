package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.repositories.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FlightService {

    private final FlightRepo flightRepo;

    @Autowired
    public FlightService(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
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

}
