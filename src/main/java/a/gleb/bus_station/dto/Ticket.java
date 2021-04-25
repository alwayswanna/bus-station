package a.gleb.bus_station.dto;


import javax.persistence.*;

@Entity
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
    @JoinColumn(name = "passenger_id", referencedColumnName = "id_passengers")
    private Passengers passengers;

    @ManyToOne
    @JoinColumn(name = "num_flight", referencedColumnName = "number_flight")
    private BusFlights busFlights;

    public Ticket(String ticketPlace, String ticketPassenger) {
        this.ticketPlace = ticketPlace;
        this.ticketPassenger = ticketPassenger;
    }

    public Ticket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicketPlace() {
        return ticketPlace;
    }

    public void setTicketPlace(String ticketPlace) {
        this.ticketPlace = ticketPlace;
    }

    public String getTicketPassenger() {
        return ticketPassenger;
    }

    public void setTicketPassenger(String ticketPassenger) {
        this.ticketPassenger = ticketPassenger;
    }

    public Passengers getPassengers() {
        return passengers;
    }

    public void setPassengers(Passengers passengers) {
        this.passengers = passengers;
    }

    public BusFlights getBusFlights() {
        return busFlights;
    }

    public void setBusFlights(BusFlights busFlights) {
        this.busFlights = busFlights;
    }
}
