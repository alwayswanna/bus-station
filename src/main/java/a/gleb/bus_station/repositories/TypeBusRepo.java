package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.TypeBus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeBusRepo extends CrudRepository<TypeBus, Integer> {

    Iterable<TypeBus> findAllById(int i);

    TypeBus findByBusModel(String busModel);

    TypeBus findById(int i);

    TypeBus findAllById(Integer id);
}
