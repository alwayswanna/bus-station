package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.PassengerPassport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerPassportRepo extends CrudRepository<PassengerPassport, Integer> {

    Iterable<PassengerPassport> findAllById(int i);

    PassengerPassport findByPassengerDocNum(String docNum);

    Iterable<PassengerPassport> findAllByPassengerSurname(String surname);

    Iterable<PassengerPassport> findAllByPassengerDocNum(String docNum);

    PassengerPassport findAllById(Integer id);
}
