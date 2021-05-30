package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private String typeOfFlight;

    @OneToMany(mappedBy = "typeFlight")
    @JsonManagedReference
    private List<BusFlights> busFlights;

    public TypeFlight(String typeOfFlight) {
        this.typeOfFlight = typeOfFlight;
    }

    public TypeFlight() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeOfFlight() {
        return typeOfFlight;
    }

    public void setTypeOfFlight(String type) {
        this.typeOfFlight = type;
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
                ", type='" + typeOfFlight + '\'' +
                '}';
    }
}
