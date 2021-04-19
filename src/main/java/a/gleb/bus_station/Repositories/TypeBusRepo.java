package a.gleb.bus_station.Repositories;

import a.gleb.bus_station.Entity.TypeBus;
import org.springframework.data.repository.CrudRepository;

public interface TypeBusRepo extends CrudRepository<TypeBus, Integer> {

    Iterable<TypeBus> findAllById(int i);
}
