package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.PassengerPassport;
import a.gleb.bus_station.dto.Ticket;
import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.repositories.FlightRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class SystemMethods {

    public static boolean checkIdForFlight(@PathVariable("id") Integer id, Map<String, Object> model, FlightRepo flightRepo) {
        if (!flightRepo.existsById(id)){
            return true;
        }

        return false;
    }

    public static boolean checkSpaceForTicket(TypeBus typeBus, List<Ticket> tickets){
        if (typeBus.getNumberOfSeats() - tickets.size() > 0){
            return true;
        }
        return false;
    }

    public static boolean checkPassengerInformation(PassengerPassport passengerPassport){
        if (passengerPassport.getPassengerName().equals("") | passengerPassport.getPassengerSurname().equals("") | passengerPassport.getPassengerBirthday().equals("") | passengerPassport.getPassengerPhone().equals("")
                | passengerPassport.getPassengerDocNum().equals("") | passengerPassport.getPassengerRegistration().equals("")){
            return false;
        }else{
            return true;
        }
    }

    public static String generateUniqNumTicket(BusFlights busFlight){
        String uuid = UUID.randomUUID().toString();
        return busFlight.getNumberFlightUnique() + "_" + uuid.substring(0, 4);
    }
}
