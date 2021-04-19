package a.gleb.bus_station.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type_flight")
public class TypeFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_flight")
    private Integer id;
    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "typeFlight")
    private List<BusFlights> busFlights;

    public TypeFlight(String type) {
        this.type = type;
    }

    public TypeFlight() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BusFlights> getBusFlights() {
        return busFlights;
    }

    public void setBusFlights(List<BusFlights> busFlights) {
        this.busFlights = busFlights;
    }

    @Override
    public String toString() {
        return "TypeFlight{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
