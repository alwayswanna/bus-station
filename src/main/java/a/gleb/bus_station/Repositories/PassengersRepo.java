package a.gleb.bus_station.Repositories;

import a.gleb.bus_station.Entity.Passengers;
import org.springframework.data.repository.CrudRepository;

public interface PassengersRepo extends CrudRepository<Passengers, Integer> {

    Iterable<Passengers> findAllById(int i);
}
