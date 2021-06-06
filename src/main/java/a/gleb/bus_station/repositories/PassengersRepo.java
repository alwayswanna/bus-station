package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.Passengers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengersRepo extends CrudRepository<Passengers, Integer> {

    Iterable<Passengers> findAllById(int i);

    Passengers findByNumTicket(String uniqueIdTicket);

    Passengers findAllById(Integer id);

}
