package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "drivers")
public class BusDriver {

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
    @Column(name = "driver_license")
    private String driverLicense;

    @OneToMany(mappedBy = "busDriver", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private Collection<BusFlights> busFlights;

    public BusDriver(String driverName, String driverSurname, String driverPhone, String driverLicense, Collection<BusFlights> busFlights) {
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.driverPhone = driverPhone;
        this.busFlights = busFlights;
        this.driverLicense = driverLicense;
    }

    public BusDriver() {
    }

}
