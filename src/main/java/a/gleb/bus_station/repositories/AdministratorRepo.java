package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepo extends JpaRepository<User, Integer> {

    User findByUserName(String login);

    User findUserById(Integer id);
}
