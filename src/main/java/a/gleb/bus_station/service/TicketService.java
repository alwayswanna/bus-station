package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {


    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public void saveTicket(Ticket ticket){
        ticketRepo.save(ticket);
    }
}
