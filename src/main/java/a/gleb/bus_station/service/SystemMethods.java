package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.*;
import a.gleb.bus_station.repositories.FlightRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public static String  checkDocAndTicketNumber(String passenger, String ticket){
        if (passenger == null | passenger.equals("")){
            return "TICKET";
        }else if(ticket == null | ticket.equals("")){
            return "PASSENGER";
        }else{
            return "ERROR";
        }
    }
}
