package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.TypeFlight;
import org.springframework.data.repository.CrudRepository;

public interface TypeFlightsRepo extends CrudRepository<TypeFlight, Integer> {

    Iterable<TypeFlight> findAllById(int i);
    TypeFlight findByTypeEquals(String type);
}
