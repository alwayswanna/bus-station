package a.gleb.bus_station.Repositories;

import org.springframework.data.repository.CrudRepository;

public interface TypeFlights extends CrudRepository<TypeFlights, Integer> {

    Iterable<TypeFlights> findAllById(int i);
}
