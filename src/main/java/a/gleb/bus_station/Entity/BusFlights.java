package a.gleb.bus_station.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "flights")
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
    @JoinColumn(name = "driver_info")
    private Drivers drivers;

    @ManyToOne
    @JoinColumn(name = "type_bus")
    private TypeBus typeBus;

    @ManyToOne
    @JoinColumn(name = "type_flight")
    private TypeFlight typeFlight;

    @OneToMany(mappedBy = "busFlights", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public BusFlights(String routeType, String fromCity, String toCity,
                      String timeDeparture, String timeArrival, String dateFlight, String numberFlightUnique) {
        this.numberFlightUnique = numberFlightUnique;
        this.dateFlight = dateFlight;
        this.routeType = routeType;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.timeDeparture = timeDeparture;
        this.timeArrival = timeArrival;
    }


    public BusFlights() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(String timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
    }

    public Drivers getDrivers() {
        return drivers;
    }

    public String getDateFlight() {
        return dateFlight;
    }

    public void setDateFlight(String dateFlight) {
        this.dateFlight = dateFlight;
    }

    public void setDrivers(Drivers drivers) {
        this.drivers = drivers;
    }

    public TypeBus getType() {
        return typeBus;
    }

    public void setType(TypeBus type) {
        this.typeBus = type;
    }

    public TypeBus getTypeBus() {
        return typeBus;
    }

    public String getNumberFlightUnique() {
        return numberFlightUnique;
    }

    public void setNumberFlightUnique(String numberFlightUnique) {
        this.numberFlightUnique = numberFlightUnique;
    }

    public void setTypeBus(TypeBus typeBus) {
        this.typeBus = typeBus;
    }

    public TypeFlight getTypeFlight() {
        return typeFlight;
    }

    public void setTypeFlight(TypeFlight typeFlight) {
        this.typeFlight = typeFlight;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "BusFlights{" +
                "id=" + id +
                ", routeType='" + routeType + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", timeDeparture='" + timeDeparture + '\'' +
                ", timeArrival='" + timeArrival + '\'' +
                '}';
    }

}
