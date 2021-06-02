package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "type_flight")
public class TypeFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_flight")
    private Integer id;
    @Column(name = "type")
    private String typeOfFlight;

    @OneToMany(mappedBy = "typeFlight")
    @JsonManagedReference(value = "type_flight")
    private Collection<BusFlights> busFlights;

    public TypeFlight(String typeOfFlight, Collection<BusFlights> busFlights) {
        this.typeOfFlight = typeOfFlight;
        this.busFlights = busFlights;
    }

    public TypeFlight() {
    }
}
