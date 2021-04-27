package a.gleb.bus_station.dto;


import javax.persistence.*;

@Entity
@Table(name = "passengers")
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passengers")
    private Integer id;
    @Column(name = "num_ticket")
    private String numTicket;

    @OneToOne(mappedBy = "passengers", cascade = CascadeType.ALL)
    private Ticket ticket;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_info", referencedColumnName = "id_passenger_passport")
    private PassengerPassport passengerInfo;

    public Passengers(String numTicket) {
        this.numTicket = numTicket;
    }

    public Passengers() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumTicket() {
        return numTicket;
    }

    public void setNumTicket(String numTicket) {
        this.numTicket = numTicket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public PassengerPassport getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(PassengerPassport passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    @Override
    public String toString() {
        return "Passengers{" +
                "id=" + id +
                ", numTicket='" + numTicket + '\'' +
                ", ticket=" + ticket +
                '}';
    }
}
