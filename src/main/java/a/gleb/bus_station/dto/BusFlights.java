package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "flights")
@Data
public class BusFlights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number_flight")
    private Integer id;
    @Column(name = "route_type")
    private String routeType;
    @Column(name = "from_city")
    private String fromCity;
    @Column(name = "to_city")
    private String toCity;
    @Column(name = "time_departure")
    private String timeDeparture;
    @Column(name = "time_arrival")
    private String timeArrival;
    @Column(name = "date")
    private String dateFlight;
    @Column(name = "num_flight")
    private String numberFlightUnique;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "driver_info")
    private BusDriver busDriver;


    @ManyToOne
    @JsonBackReference(value = "bus_info")
    @JoinColumn(name = "bus_info")
    private TypeBus typeBus;


    @ManyToOne
    @JsonBackReference(value = "type_flight")
    @JoinColumn(name = "type_flight")
    private TypeFlight typeFlight;


    @OneToMany(mappedBy = "busFlights", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference(value = "ticket_list")
    private Collection<Ticket> tickets;


    public BusFlights(String routeType, String fromCity, String toCity, String timeDeparture, String timeArrival, String dateFlight,
                      String numberFlightUnique, BusDriver busDriver, TypeBus typeBus, TypeFlight typeFlight, Collection<Ticket> tickets) {
        this.routeType = routeType;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
        this.dateFlight = dateFlight;
        this.numberFlightUnique = numberFlightUnique;
        this.busDriver = busDriver;
        this.typeBus = typeBus;
        this.typeFlight = typeFlight;
        this.tickets = tickets;
    }

    public BusFlights() {
    }



}
