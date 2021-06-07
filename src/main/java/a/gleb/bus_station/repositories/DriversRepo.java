package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.BusDriver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriversRepo extends CrudRepository<BusDriver, Integer> {

    Iterable<BusDriver> findAllById(int i);
    BusDriver findByDriverSurname(String surname);
    BusDriver findById(int i);
    BusDriver findByDriverNameAndDriverSurname(String driverSurname, String driverName);
    BusDriver findAllByDriverLicense(String driverLicense);
    BusDriver findAllById(Integer id);

}
