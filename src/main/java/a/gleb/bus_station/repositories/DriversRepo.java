package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.Drivers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriversRepo extends CrudRepository<Drivers, Integer> {

    Iterable<Drivers> findAllById(int i);
    Drivers findByDriverSurname(String surname);
    Drivers findById(int i);
    Drivers findByDriverNameAndDriverSurname(String driverSurname, String driverName);

}
