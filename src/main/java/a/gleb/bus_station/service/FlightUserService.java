package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.repositories.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FlightUserService {

    private final FlightRepo flightRepo;

    @Autowired
    public FlightUserService(FlightRepo flightRepo) {
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

    public Ticket buyTicketOnSelectedFlight(BusFlights fl, PassengerPassport ps){


        return null;
    }

}
