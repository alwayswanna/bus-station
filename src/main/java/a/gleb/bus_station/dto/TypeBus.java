package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "typeBus")
    @JsonBackReference
    private List<BusFlights> busFlights;

    public TypeBus(String type, int numberOfSeats, String busModel) {
        this.type = type;
        this.numberOfSeats = numberOfSeats;
        this.busModel = busModel;
    }

    public TypeBus() {
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

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getBusModel() {
        return busModel;
    }

    public void setBusModel(String busModel) {
        this.busModel = busModel;
    }

    public List<BusFlights> getBusFlights() {
        return busFlights;
    }

    public void setBusFlights(List<BusFlights> busFlights) {
        this.busFlights = busFlights;
    }

    @Override
    public String toString() {
        return "TypeBus{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", busModel='" + busModel + '\'' +
                '}';
    }

    public void addFlightToBusList(BusFlights busFlight){
        if (busFlight == null){
            busFlights = new ArrayList<>();
        }else{
            busFlights.add(busFlight);
        }

    }
}
