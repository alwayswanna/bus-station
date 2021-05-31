package a.gleb.bus_station.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
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
    @JsonBackReference
    private Passengers passengers;

    public PassengerPassport(String passengerName, String passengerSurname, String passengerPhone, String passengerDocNum, String passengerRegistration, String passengerBirthday) {
        this.passengerName = passengerName;
        this.passengerSurname = passengerSurname;
        this.passengerPhone = passengerPhone;
        this.passengerDocNum = passengerDocNum;
        this.passengerRegistration = passengerRegistration;
        this.passengerBirthday = passengerBirthday;
    }

    public PassengerPassport() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerSurname() {
        return passengerSurname;
    }

    public void setPassengerSurname(String passengerSurname) {
        this.passengerSurname = passengerSurname;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerDocNum() {
        return passengerDocNum;
    }

    public void setPassengerDocNum(String passengerDocNum) {
        this.passengerDocNum = passengerDocNum;
    }

    public String getPassengerRegistration() {
        return passengerRegistration;
    }

    public void setPassengerRegistration(String passengerRegistration) {
        this.passengerRegistration = passengerRegistration;
    }

    public String getPassengerBirthday() {
        return passengerBirthday;
    }

    public void setPassengerBirthday(String passengerBirthday) {
        this.passengerBirthday = passengerBirthday;
    }

    public Passengers getPassengers() {
        return passengers;
    }

    public void setPassengers(Passengers passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "PassengerPassport{" +
                "id=" + id +
                ", passengerName='" + passengerName + '\'' +
                ", passengerSurname='" + passengerSurname + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", passengerDocNum='" + passengerDocNum + '\'' +
                ", passengerRegistration='" + passengerRegistration + '\'' +
                ", passengerBirthday='" + passengerBirthday + '\'' +
                '}';
    }
}
