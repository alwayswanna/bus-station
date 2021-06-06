package a.gleb.bus_station.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_ticket")
    private Integer id;
    @Column(name = "ticket_place")
    private String ticketPlace;
    @Column(name = "ticket_passenger")
    private String ticketPassenger;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "passenger_list")
    @JoinColumn(name = "passenger_id", referencedColumnName = "id_passengers")
    private Passengers passengers;

    @ManyToOne
    @JsonBackReference(value = "ticket_list")
    @JoinColumn(name = "num_flight", referencedColumnName = "number_flight")
    private BusFlights busFlights;

    public Ticket(String ticketPlace, String ticketPassenger, Passengers passengers, BusFlights busFlights) {
        this.ticketPlace = ticketPlace;
        this.ticketPassenger = ticketPassenger;
        this.passengers = passengers;
        this.busFlights = busFlights;
    }

    public Ticket() {
    }
}
