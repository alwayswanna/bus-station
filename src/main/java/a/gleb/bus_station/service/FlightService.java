package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.exceptions.DuplicateFlightException;
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
        Iterable<BusFlights> allFlights = flightRepo.findAll();
        if (allFlights.iterator().next() == null){
            throw new NoSuchElementException();
        }else{
            return allFlights;
        }
    }

    public BusFlights returnFlightById(Integer id){
        BusFlights selectedFlight = flightRepo.findAllById(id);
        if (selectedFlight == null){
            throw new NoSuchElementException();
        }else{
            return selectedFlight;
        }
    }

    public BusFlights addNewFlight(BusFlights busFlights){
        BusFlights checkFlight = flightRepo.findAllById(busFlights.getId());
        if (checkFlight == null){
            flightRepo.save(busFlights);
            return busFlights;
        }else{
            throw new DuplicateFlightException("DuplicateFlightException: flight with [ID]: " + busFlights.getId() + " already exists");
        }
    }

    public BusFlights editSelectedFlight(BusFlights busFlight){
        BusFlights flight = flightRepo.findAllById(busFlight.getId());
        if (flight == null){
            throw new NoSuchElementException();
        }else{
            flight.setRouteType(busFlight.getRouteType());
            flight.setFromCity(busFlight.getFromCity());
            flight.setToCity(busFlight.getToCity());
            flight.setTimeDeparture(busFlight.getTimeDeparture());
            flight.setTimeArrival(busFlight.getTimeArrival());
            flight.setDateFlight(busFlight.getDateFlight());
            flight.setNumberFlightUnique(busFlight.getNumberFlightUnique());
            flight.setBusDriver(busFlight.getBusDriver());
            flight.setTypeBus(busFlight.getTypeBus());
            flight.setTypeFlight(busFlight.getTypeFlight());
            flight.setTickets(busFlight.getTickets());
            flightRepo.save(flight);
            return flight;
        }
    }

    public Iterable<BusFlights> deleteSelectedFlight(Integer id){
        BusFlights flight = flightRepo.findAllById(id);
        if (flight == null){
            throw new NoSuchElementException("NoSuchElementException: no flight with [ID]: " + id);
        }else{
            flightRepo.delete(flight);
            return allFlights();
        }

    }
}
