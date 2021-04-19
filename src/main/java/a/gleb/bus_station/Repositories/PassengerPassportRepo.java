package a.gleb.bus_station.Repositories;

import a.gleb.bus_station.Entity.PassengerPassport;
import org.springframework.data.repository.CrudRepository;

public interface PassengerPassportRepo extends CrudRepository<PassengerPassport, Integer> {

    Iterable<PassengerPassport> findAllById(int i);
}
