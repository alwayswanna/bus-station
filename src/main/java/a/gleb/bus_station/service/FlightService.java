package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.Passengers;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.exceptions.DuplicateFlightException;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FlightService {

    private final FlightRepo flightRepo;
    private final TicketRepo ticketRepo;

    @Autowired
    public FlightService(FlightRepo flightRepo, TicketRepo ticketRepo) {
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    public Iterable<BusFlights> allFlights(){
        Iterable<BusFlights> allFlights = flightRepo.findAll();
        if (allFlights.iterator().next() == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find flights in database");
        }else{
            return allFlights;
        }
    }

    public BusFlights returnFlightById(Integer id){
        BusFlights selectedFlight = flightRepo.findAllById(id);
        if (selectedFlight == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find flight with [ID]: " + id);
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
            throw new NoSuchElementException("NoSuchElementException: can`t find flight with [ID]: " + busFlight.getId() + " for edit");
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
            Iterable<Ticket> tickets = flight.getTickets();
            for (Ticket ticket : tickets){
                ticket.setBusFlights(returnFlightById(1));
                ticketRepo.save(ticket);
            }
            flightRepo.delete(flight);
            return allFlights();
        }
    }

    public Iterable<BusFlights> flightsByType(String type){
        Iterable<BusFlights> flightsByType = flightRepo.findAllByRouteType(type);
        if (flightsByType.iterator().next() == null){
            throw new NoSuchElementException("NoSuchElementException: can`t flight with [type]: " + type);
        }else{
            return flightsByType;
        }
    }

    public BusFlights findFlightByFromCity(String city){
        BusFlights flights = flightRepo.findAllByFromCity(city);
        if (flights == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find flight with [fromCity]: " + city);
        }else {
            return flights;
        }
    }

    public Iterable<BusFlights> returnFlightsByRouteType(String routeType){
        Iterable<BusFlights> flights = flightRepo.findAllByRouteType(routeType);
        if (flights.iterator().next() == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find flight with [routeType]: " + routeType);
        }else {
            return flights;
        }
    }

    public BusFlights returnFlightByUniqueNumber(String number){
        BusFlights flight = flightRepo.findByNumberFlightUnique(number);
        if (flight == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find flight with [number]: " + number);
        }else {
            return flight;
        }
    }
}
