package a.gleb.bus_station.Entity;


import javax.persistence.*;

@Entity
@Table(name = "passengers")
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPassengers")
    private Integer id;
    @Column(name = "passenger_passport")
    private int passengerPassport;
    @Column(name = "num_ticket")
    private String numTicket;

    @OneToOne(mappedBy = "passengers")
    private Ticket ticket;

    @OneToOne
    @JoinColumn(name = "passenger_info")
    private PassengerPassport passengerInfo;

    public Passengers(int passengerPassport, String numTicket) {
        this.passengerPassport = passengerPassport;
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

    public int getPassengerPassport() {
        return passengerPassport;
    }

    public void setPassengerPassport(int passengerPassport) {
        this.passengerPassport = passengerPassport;
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
                ", passengerPassport=" + passengerPassport +
                ", numTicket='" + numTicket + '\'' +
                '}';
    }
}
