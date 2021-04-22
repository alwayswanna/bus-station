package a.gleb.bus_station.dto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "drivers")
    private List<BusFlights> busFlights;

    public Drivers(String driverName, String driverSurname, String driverPhone) {
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.driverPhone = driverPhone;
    }

    public Drivers() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public List<BusFlights> getBusFlights() {
        return busFlights;
    }

    public void setBusFlights(List<BusFlights> busFlights) {
        this.busFlights = busFlights;
    }

    public void addRouteToList(BusFlights busFlight){
        if (busFlights == null){
            busFlights = new ArrayList<>();
        }else{
            busFlights.add(busFlight);
        }

    }
}
