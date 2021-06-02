package a.gleb.bus_station.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "passengers")
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passengers")
    private Integer id;
    @Column(name = "num_ticket")
    private String numTicket;

    @OneToOne(mappedBy = "passengers", cascade = CascadeType.ALL)
    @JsonBackReference(value = "passenger_list")
    private Ticket ticket;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "passenger_info")
    @JoinColumn(name = "passenger_info", referencedColumnName = "id_passenger_passport")
    private PassengerPassport passengerInfo;

    public Passengers(String numTicket, Ticket ticket, PassengerPassport passengerInfo) {
        this.numTicket = numTicket;
        this.ticket = ticket;
        this.passengerInfo = passengerInfo;
    }

    public Passengers() {
    }
}
