package a.gleb.bus_station.Config;

import a.gleb.bus_station.Entity.BusFlights;
import a.gleb.bus_station.Entity.Drivers;
import a.gleb.bus_station.Entity.TypeBus;
import a.gleb.bus_station.Entity.TypeFlight;
import a.gleb.bus_station.Repositories.DriversRepo;
import a.gleb.bus_station.Repositories.FlightRepo;
import a.gleb.bus_station.Repositories.TypeBusRepo;
import a.gleb.bus_station.Repositories.TypeFlightsRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StartAppLoadToDataBase implements CommandLineRunner {

    private final FlightRepo flightRepo;
    private final DriversRepo driversRepo;
    private final TypeBusRepo typeBusRepo;
    private final TypeFlightsRepo typeFlightsRepo;

    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String dateOfFlight = simpleDateFormat.format(date);

    public StartAppLoadToDataBase(FlightRepo flightRepo, DriversRepo driversRepo,
                                  TypeBusRepo typeBusRepo, TypeFlightsRepo typeFlightsRepo) {
        this.flightRepo = flightRepo;
        this.driversRepo = driversRepo;
        this.typeBusRepo = typeBusRepo;
        this.typeFlightsRepo = typeFlightsRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Adding default values for Drivers:
        Drivers firstDriver = new Drivers("Oleg", "Ivanov", "+79068568585");
        Drivers secondDriver = new Drivers("Ivan", "Menshov", "+79058569645");
        Drivers thirdDriver = new Drivers("Vasili", "Petrov", "+79875696365");
        Drivers fourthDriver = new Drivers("Egor", "Kowtow", "+79856565855");
        Drivers fifthDriver = new Drivers("Semen", "Kuznetsov", "+79056363859");
        driversRepo.save(firstDriver);
        driversRepo.save(secondDriver);
        driversRepo.save(thirdDriver);
        driversRepo.save(fourthDriver);
        driversRepo.save(fifthDriver);
        // Adding default buses for TypeBus:
        TypeBus firstTypeBus = new TypeBus("Intercity", 60, "Mercedes A-314");
        TypeBus secondTypeBus = new TypeBus("Intercity", 60, "Ford LA-600");
        TypeBus thirdTypeBus = new TypeBus("Suburban", 20, "Opel S-30");
        TypeBus fourthTypeBus = new TypeBus("Suburban", 20, "Opel S-40");
        TypeBus fifthTypeBus = new TypeBus("Suburban", 25, "Mercedes S-400");
        typeBusRepo.save(firstTypeBus);
        typeBusRepo.save(secondTypeBus);
        typeBusRepo.save(thirdTypeBus);
        typeBusRepo.save(fourthTypeBus);
        typeBusRepo.save(fifthTypeBus);
        // Adding default types of flights for TypeFlight:
        TypeFlight intercityTypeFlight = new TypeFlight("Intercity");
        TypeFlight suburbanTypeFlight = new TypeFlight("Suburban");
        typeFlightsRepo.save(intercityTypeFlight);
        typeFlightsRepo.save(suburbanTypeFlight);
        // Adding flights for BusFlights:
        BusFlights firstBusFlight = new BusFlights("null", "Ufa", "Belebei", "10:00", "12:55", dateOfFlight, "null");
        firstBusFlight.setDrivers(firstDriver);
        firstBusFlight.setTypeBus(thirdTypeBus);
        firstBusFlight.setTypeFlight(suburbanTypeFlight);
        BusFlights secondBusFlight = new BusFlights("null", "Ufa", "Kazan`", "13:30", "19:00", dateOfFlight, "null");
        secondBusFlight.setDrivers(fourthDriver);
        secondBusFlight.setTypeBus(fourthTypeBus);
        secondBusFlight.setTypeFlight(suburbanTypeFlight);
        BusFlights thirdBusFlight = new BusFlights("null", "Ufa", "Samara", "15:15", "12:55", dateOfFlight, "null");
        thirdBusFlight.setDrivers(thirdDriver);
        thirdBusFlight.setTypeBus(firstTypeBus);
        thirdBusFlight.setTypeFlight(intercityTypeFlight);
        BusFlights fourthBusFlight = new BusFlights("null", "Ufa", "Neftekamsk", "17:00", "20:00", dateOfFlight, "null");
        fourthBusFlight.setDrivers(fourthDriver);
        fourthBusFlight.setTypeBus(fifthTypeBus);
        fourthBusFlight.setTypeFlight(suburbanTypeFlight);
        BusFlights fifthBusFlight = new BusFlights("null", "Ufa", "Moscow", "19:00", "09:55", dateOfFlight, "null");
        fifthBusFlight.setDrivers(fifthDriver);
        fifthBusFlight.setTypeBus(secondTypeBus);
        fifthBusFlight.setTypeFlight(intercityTypeFlight);
        flightRepo.save(firstBusFlight);
        flightRepo.save(secondBusFlight);
        flightRepo.save(thirdBusFlight);
        flightRepo.save(fourthBusFlight);
        flightRepo.save(fifthBusFlight);
        // Done output, information:
        System.out.println("In data base was added:" +
                "\n Drivers: [Oleg, Ivan, Vasili, Egor, Semen]" +
                "\n TypeBus: [Mercedes A-314, Ford LA-600, Opel S-30, Opel S-40, Mercedes S-400]" +
                "\n TypeFlight: [Intercity, Suburban]" +
                "\n FlightsTo: [Belebei, Kazan`, Samara, Neftekamsk, Moscow]" +
                "\n" +
                "\n ********************************* DONE *********************************");



    }
}
