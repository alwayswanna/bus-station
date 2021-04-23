package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.TypeBus;
import org.springframework.data.repository.CrudRepository;

public interface TypeBusRepo extends CrudRepository<TypeBus, Integer> {

    Iterable<TypeBus> findAllById(int i);
    TypeBus findByBusModel(String busModel);
}
