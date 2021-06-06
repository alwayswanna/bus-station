package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "passenger_passport")
public class PassengerPassport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passenger_passport")
    private Integer id;
    @Column(name = "passenger_name")
    private String passengerName;
    @Column(name = "passenger_surname")
    private String passengerSurname;
    @Column(name = "passenger_phone")
    private String passengerPhone;
    @Column(name = "passenger_doc_num")
    private String passengerDocNum;
    @Column(name = "passenger_registration")
    private String passengerRegistration;
    @Column(name = "passenger_birthday")
    private String passengerBirthday;

    @OneToOne(mappedBy = "passengerInfo", cascade = CascadeType.ALL)
    @JsonBackReference(value = "passenger_info")
    private Passengers passengers;

    public PassengerPassport(String passengerName, String passengerSurname, String passengerPhone, String passengerDocNum, String passengerRegistration,
                             String passengerBirthday, Passengers passengers) {
        this.passengerName = passengerName;
        this.passengerSurname = passengerSurname;
        this.passengerPhone = passengerPhone;
        this.passengerDocNum = passengerDocNum;
        this.passengerRegistration = passengerRegistration;
        this.passengerBirthday = passengerBirthday;
        this.passengers = passengers;
    }

    public PassengerPassport() {
    }

}
