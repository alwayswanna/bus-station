package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.Drivers;
import a.gleb.bus_station.repositories.DriversRepo;
import a.gleb.bus_station.repositories.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightAdminService {

    private final DriversRepo driverRepository;
    private final FlightRepo flightsRepository;

    @Autowired
    public FlightAdminService(DriversRepo driverRepository, FlightRepo flightsRepository) {
        this.driverRepository = driverRepository;
        this.flightsRepository = flightsRepository;
    }


    public Drivers addNewDriver(Drivers driver){
        driverRepository.save(driver);
        return driver;
    }

    public Iterable<BusFlights> getAllFlights() {
        return flightsRepository.findAll();
    }
}
