package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Passengers;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SystemMethods {

    public static boolean checkPassengerInformation(PassengerPassport passengerPassport){
        return !(passengerPassport.getPassengerName().equals("") | passengerPassport.getPassengerSurname().equals("") | passengerPassport.getPassengerBirthday().equals("") | passengerPassport.getPassengerPhone().equals("")
                | passengerPassport.getPassengerDocNum().equals("") | passengerPassport.getPassengerRegistration().equals(""));
    }

    public static String generateUniqNumTicket(BusFlights busFlight){
        String uuid = UUID.randomUUID().toString();
        return busFlight.getNumberFlightUnique() + "_" + uuid.substring(0, 4);
    }

    @NotNull
    public static List<Object> getObjects(Iterable<PassengerPassport> passengerWithDocNum) {
        List<Object> callBack = new ArrayList<>();
        callBack.add(passengerWithDocNum.iterator().next());
        Passengers passenger = passengerWithDocNum.iterator().next().getPassengers();
        callBack.add(passenger);
        callBack.add(passenger.getTicket());
        return callBack;
    }
}
