package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.TypeFlight;
import a.gleb.bus_station.repositories.TypeFlightsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TypeFlightService {

    private final TypeFlightsRepo typeFlightsRepo;

    @Autowired
    public TypeFlightService(TypeFlightsRepo typeFlightsRepo) {
        this.typeFlightsRepo = typeFlightsRepo;
    }

    public Iterable<TypeFlight> allTypesOfFlights(){
        Iterable<TypeFlight> typesOfFlights = typeFlightsRepo.findAll();
        if (typesOfFlights.iterator().next() == null){
            throw new NoSuchElementException();
        }else {
            return typesOfFlights;
        }
    }

    public TypeFlight selectedTypeOfFlight(String type){
        TypeFlight typeFlight = typeFlightsRepo.findByTypeOfFlight(type);
        if (typeFlight == null){
            throw new NoSuchElementException();
        }else{
            return typeFlight;
        }
    }
}
