package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.TypeFlight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFlightsRepo extends CrudRepository<TypeFlight, Integer> {

    TypeFlight findAllById(Integer id);

    Iterable<TypeFlight> findAllById(int i);

    TypeFlight findByTypeOfFlight(String type);

    TypeFlight findById(int id);
}
