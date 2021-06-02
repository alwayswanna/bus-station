package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "drivers")
public class Drivers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_driver")
    private Integer id;
    @Column(name = "driver_name")
    private String driverName;
    @Column(name = "driver_surname")
    private String driverSurname;
    @Column(name = "driver_phone")
    private String driverPhone;

    @OneToMany(mappedBy = "drivers", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private Collection<BusFlights> busFlights;

    public Drivers(String driverName, String driverSurname, String driverPhone, Collection<BusFlights> busFlights) {
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.driverPhone = driverPhone;
        this.busFlights = busFlights;
    }

    public Drivers() {
    }

}
