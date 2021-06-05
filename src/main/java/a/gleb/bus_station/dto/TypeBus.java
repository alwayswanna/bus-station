package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "type_bus")
public class TypeBus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_bus")
    private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @Column(name = "bus_model")
    private String busModel;

    @OneToMany(mappedBy = "typeBus", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference(value = "bus_info")
    private Collection<BusFlights> busFlights;

    public TypeBus(String type, int numberOfSeats, String busModel, Collection<BusFlights> busFlights) {
        this.type = type;
        this.numberOfSeats = numberOfSeats;
        this.busModel = busModel;
        this.busFlights = busFlights;
    }

    public TypeBus() {
    }
}
