package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.exceptions.DuplicateBusException;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.TypeBusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BusService {

    private final TypeBusRepo busRepo;
    private final FlightRepo flightRepo;

    @Autowired
    public BusService(TypeBusRepo busRepo, FlightRepo flightRepo) {
        this.busRepo = busRepo;
        this.flightRepo = flightRepo;
    }

    public Iterable<TypeBus> allBuses(){
        Iterable<TypeBus> busList = busRepo.findAll();
        if (busList.iterator().next() == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find buses in database");
        }else {
            return busList;
        }
    }

    public TypeBus returnBusById(Integer id){
        TypeBus bus = busRepo.findAllById(id);
        if (bus == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find bus with [ID]: " + id);
        }else {
            return bus;
        }
    }

    public Iterable<TypeBus> deleteSelectedBus(Integer id){
        TypeBus busForDelete = busRepo.findAllById(id);
        if (busForDelete == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find bus with [ID]: " + id + " for delete");
        }else {
            Iterable<BusFlights> listFlights = busForDelete.getBusFlights();
            for (BusFlights flight : listFlights){
                flight.setTypeBus(returnBusById(1));
                flightRepo.save(flight);
            }
            busRepo.delete(busForDelete);
            return allBuses();
        }
    }

    public TypeBus addNewTypeBus(TypeBus typeBus){
        TypeBus bus = busRepo.findAllById(typeBus.getId());
        if (bus == null){
            busRepo.save(typeBus);
            return typeBus;
        }else {
            throw new DuplicateBusException("DuplicateBusException: bus with [ID]: " + typeBus.getId() + " already exist");
        }
    }

    public TypeBus editSelectedBus(TypeBus bus){
        TypeBus checkBus = busRepo.findAllById(bus.getId());
        if (checkBus == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find bus with [ID]: " + bus.getId() + " for edit");
        }else {
            checkBus.setType(bus.getType());
            checkBus.setNumberOfSeats(bus.getNumberOfSeats());
            checkBus.setBusModel(bus.getBusModel());
            checkBus.setBusFlights(bus.getBusFlights());
            busRepo.save(checkBus);
            return checkBus;
        }
    }

    public TypeBus busByNameModel(String modelName){
        TypeBus bus = busRepo.findByBusModel(modelName);
        if (bus == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find bus with [ModelName]: " + modelName);
        }else{
            return bus;
        }
    }


}
