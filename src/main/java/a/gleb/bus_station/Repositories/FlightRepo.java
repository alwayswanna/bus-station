package a.gleb.bus_station.Repositories;

import a.gleb.bus_station.Entity.BusFlights;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepo extends CrudRepository<BusFlights, Integer> {

    Iterable<BusFlights> findAllById(int i);
    BusFlights findAllByFromCity(String fromCity);
}
