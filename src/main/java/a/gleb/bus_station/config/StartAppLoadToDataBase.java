package a.gleb.bus_station.config;

import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.Drivers;
import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.dto.TypeFlight;
import a.gleb.bus_station.repositories.DriversRepo;
import a.gleb.bus_station.repositories.FlightRepo;
import a.gleb.bus_station.repositories.TypeBusRepo;
import a.gleb.bus_station.repositories.TypeFlightsRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Driver;
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
        Drivers defaultDriver = new Drivers("смена водителя", "Идет", "Не определен");
        Drivers firstDriver = new Drivers("Олег", "Иванов", "+79068568585");
        Drivers secondDriver = new Drivers("Иван", "Меньшов", "+79058569645");
        Drivers thirdDriver = new Drivers("Василий", "Петров", "+79875696365");
        Drivers fourthDriver = new Drivers("Егор", "Котов", "+79856565855");
        Drivers fifthDriver = new Drivers("Семен", "Кузнецов", "+79056363859");
        defaultDriver.setId(1);
        try{
            if (driversRepo.findByDriverSurname("Кузнецов") == null){
                driversRepo.save(defaultDriver);
                driversRepo.save(fifthDriver);
                driversRepo.save(firstDriver);
                driversRepo.save(secondDriver);
                driversRepo.save(thirdDriver);
                driversRepo.save(fourthDriver);
                driversRepo.save(fifthDriver);
            }else{
                System.out.println("Done");
            }
        }catch (Exception e){

        }
        // Adding default buses for TypeBus:
        TypeBus firstTypeBus = new TypeBus("Междугородний", 60, "Mercedes A-314");
        TypeBus secondTypeBus = new TypeBus("Междугородний", 60, "Ford LA-600");
        TypeBus thirdTypeBus = new TypeBus("Пригородный", 20, "Opel S-30");
        TypeBus fourthTypeBus = new TypeBus("Пригородный", 20, "Opel S-40");
        TypeBus fifthTypeBus = new TypeBus("Пригородный", 25, "Mercedes S-400");
        try{
            if (typeBusRepo.findByBusModel("Mercedes A-314") == null){
                typeBusRepo.save(firstTypeBus);
                typeBusRepo.save(secondTypeBus);
                typeBusRepo.save(thirdTypeBus);
                typeBusRepo.save(fourthTypeBus);
                typeBusRepo.save(fifthTypeBus);
            }
        }catch (Exception e){

        }
        // Adding default types of flights for TypeFlight:
        TypeFlight intercityTypeFlight = new TypeFlight("Междугородний");
        TypeFlight suburbanTypeFlight = new TypeFlight("Пригородный");
        try{
            if (typeFlightsRepo.findByTypeOfFlight("Пригородный") == null){
                typeFlightsRepo.save(intercityTypeFlight);
                typeFlightsRepo.save(suburbanTypeFlight);
            }else{
                System.out.println("Done");
            }
        }catch (Exception e){

        }
        // Adding flights for BusFlights:
        BusFlights firstBusFlight = new BusFlights("Прибывающий", "Белорец", "Уфа", "10:00", "12:55", dateOfFlight, "УБ-45");
        firstBusFlight.setDrivers(firstDriver);
        firstBusFlight.setTypeBus(thirdTypeBus);
        firstBusFlight.setTypeFlight(suburbanTypeFlight);
        BusFlights secondBusFlight = new BusFlights("Прибывающий", "Санк-Петербург", "Уфа", "13:30", "19:00", dateOfFlight, "УК-456");
        secondBusFlight.setDrivers(fourthDriver);
        secondBusFlight.setTypeBus(fourthTypeBus);
        secondBusFlight.setTypeFlight(suburbanTypeFlight);
        BusFlights thirdBusFlight = new BusFlights("Прибывающий", "Салават", "Уфа", "15:15", "12:55", dateOfFlight, "УС-96");
        thirdBusFlight.setDrivers(thirdDriver);
        thirdBusFlight.setTypeBus(firstTypeBus);
        thirdBusFlight.setTypeFlight(intercityTypeFlight);
        BusFlights fourthBusFlight = new BusFlights("Прибывающий", "Нефтекамск", "Уфа", "17:00", "20:00", dateOfFlight, "НА-857");
        fourthBusFlight.setDrivers(fourthDriver);
        fourthBusFlight.setTypeBus(fifthTypeBus);
        fourthBusFlight.setTypeFlight(suburbanTypeFlight);
        BusFlights fifthBusFlight = new BusFlights("Отбывающий", "Уфа", "Москва", "19:00", "09:55", dateOfFlight, "УМ-4563");
        fifthBusFlight.setDrivers(fifthDriver);
        fifthBusFlight.setTypeBus(secondTypeBus);
        fifthBusFlight.setTypeFlight(intercityTypeFlight);
        try{
            if (flightRepo.findAllByFromCity("Уфа") == null){
                flightRepo.save(firstBusFlight);
                flightRepo.save(secondBusFlight);
                flightRepo.save(thirdBusFlight);
                flightRepo.save(fourthBusFlight);
                flightRepo.save(fifthBusFlight);
            }else {
                System.out.println("Done");
            }
        }catch (Exception e){

        }
        // Done output, information:
        System.out.println("In data base was added:" +
                "\n Drivers: [Олег, Иван, Василий, Егор, Семен]" +
                "\n TypeBus: [Mercedes A-314, Ford LA-600, Opel S-30, Opel S-40, Mercedes S-400]" +
                "\n TypeFlight: [Пригородный, Междугородний]" +
                "\n FlightsTo: [Белебей, Казань, Самара, Нефтекамск, Москва]" +
                "\n" +
                "\n ********************************* DONE *********************************");



    }
}
