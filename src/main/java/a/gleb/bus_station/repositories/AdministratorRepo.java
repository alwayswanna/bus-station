package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepo extends JpaRepository<User, Integer> {

    User findByUserName(String login);
}
