package a.gleb.bus_station.Repositories;

import a.gleb.bus_station.Entity.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepo extends CrudRepository<Ticket, Integer> {

    Iterable<Ticket> findAllById(int i);
}
