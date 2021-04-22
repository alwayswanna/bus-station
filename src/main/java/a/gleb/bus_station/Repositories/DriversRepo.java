package a.gleb.bus_station.Repositories;

import a.gleb.bus_station.Entity.Drivers;
import org.springframework.data.repository.CrudRepository;

public interface DriversRepo extends CrudRepository<Drivers, Integer> {

    Iterable<Drivers> findAllById(int i);
    Drivers findByDriverSurname(String surname);
    Drivers findById(int i);
    Drivers findByDriverNameAndDriverSurname(String driverSurname, String driverName);
}
