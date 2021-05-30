package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.BusFlights;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepo extends CrudRepository<BusFlights, Integer> {

    Iterable<BusFlights> findAllById(int i);

    BusFlights findAllByFromCity(String fromCity);

    Iterable<BusFlights> findAllByRouteType(String routeType);

    BusFlights findByNumberFlightUnique(String numberFlightUnique);

}
