package a.gleb.bus_station.repositories;

import a.gleb.bus_station.dto.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends CrudRepository<Ticket, Integer> {

    Iterable<Ticket> findAllById(int i);
}
