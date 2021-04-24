package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.Passengers;
import org.springframework.data.repository.CrudRepository;

public interface PassengersRepo extends CrudRepository<Passengers, Integer> {

    Iterable<Passengers> findAllById(int i);
    Passengers findByNumTicket(String uniqueIdTicket);
}
