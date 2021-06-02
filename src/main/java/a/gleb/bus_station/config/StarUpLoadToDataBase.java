package a.gleb.bus_station.config;

import a.gleb.bus_station.dto.*;
import a.gleb.bus_station.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Component
public class StarUpLoadToDataBase implements CommandLineRunner {

    private final FlightRepo flightRepo;
    private final DriversRepo driversRepo;
    private final TypeBusRepo typeBusRepo;
    private final TypeFlightsRepo typeFlightsRepo;
    private final AdministratorRepo administratorRepo;
    private final PasswordEncoder passwordEncoder;


    public StarUpLoadToDataBase(FlightRepo flightRepo, DriversRepo driversRepo, TypeBusRepo typeBusRepo, TypeFlightsRepo typeFlightsRepo, AdministratorRepo administratorRepo, PasswordEncoder passwordEncoder) {
        this.flightRepo = flightRepo;
        this.driversRepo = driversRepo;
        this.typeBusRepo = typeBusRepo;
        this.typeFlightsRepo = typeFlightsRepo;
        this.administratorRepo = administratorRepo;
        this.passwordEncoder = passwordEncoder;
    }

    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String dateOfFlight = simpleDateFormat.format(date);



    @Override
    public void run(String... args) throws Exception {

            User defaultAdministrator = new User("Administrator", passwordEncoder.encode("administrator"), "Евгений", "Шаповников",
                    "+79006506060", "e.shapovnikov@busstation.ru", true, Collections.singleton(Role.ADMINISTRATOR));
            if (administratorRepo.findByUserName("Administrator") == null){
                administratorRepo.save(defaultAdministrator);
                System.out.println("Пользователь [Administrator] успешно создан. Administrator:administrator");
            }else {
                System.out.println("Пользователь [Administrator] уже создан. Administrator:administrator");
            }

            // Adding default values for Drivers:
            BusDriver defaultDriver = new BusDriver("смена водителя", "Идет", "Не определен", "null", null);
            BusDriver firstDriver = new BusDriver("Олег", "Иванов", "+79068568585", "UJ-708", null);
            BusDriver secondDriver = new BusDriver("Иван", "Меньшов", "+79058569645", "UJ-908", null);
            BusDriver thirdDriver = new BusDriver("Василий", "Петров", "+79875696365", "UJ-8080", null);
            BusDriver fourthDriver = new BusDriver("Егор", "Котов", "+79856565855", "UJ-809", null);
             BusDriver fifthDriver = new BusDriver("Семен", "Кузнецов", "+79056363859", "UJ-808", null);
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
            TypeBus defaultBus = new TypeBus("Не определен", 0,"Не определен", null);
            TypeBus firstTypeBus = new TypeBus("Междугородний", 60, "Mercedes A-314", null);
            TypeBus secondTypeBus = new TypeBus("Междугородний", 60, "Ford LA-600", null);
            TypeBus thirdTypeBus = new TypeBus("Пригородный", 20, "Opel S-30", null);
            TypeBus fourthTypeBus = new TypeBus("Пригородный", 20, "Opel S-40", null);
            TypeBus fifthTypeBus = new TypeBus("Пригородный", 25, "Mercedes S-400", null);
            defaultBus.setId(1);
            try{
                if (typeBusRepo.findByBusModel("Mercedes A-314") == null){
                    typeBusRepo.save(defaultBus);
                    typeBusRepo.save(firstTypeBus);
                    typeBusRepo.save(secondTypeBus);
                    typeBusRepo.save(thirdTypeBus);
                    typeBusRepo.save(fourthTypeBus);
                    typeBusRepo.save(fifthTypeBus);
                }
            }catch (Exception e){

            }
            // Adding default types of flights for TypeFlight:
            TypeFlight intercityTypeFlight = new TypeFlight("Междугородний", null);
            TypeFlight suburbanTypeFlight = new TypeFlight("Пригородный", null);
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
            BusFlights defaultFlight = new BusFlights("null", "null", "null", "null", "null", "null", "null", defaultDriver, defaultBus, suburbanTypeFlight, null);
            BusFlights firstBusFlight = new BusFlights("Прибывающий", "Белорец", "Уфа", "10:00", "12:55", dateOfFlight, "УБ-45", firstDriver, secondTypeBus, suburbanTypeFlight, null);
            /*firstBusFlight.setDrivers(firstDriver);
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
            fifthBusFlight.setTypeFlight(intercityTypeFlight);*/
            try{
                if (flightRepo.findAllByFromCity("Уфа") == null){
                    flightRepo.save(defaultFlight);
                    flightRepo.save(firstBusFlight);
                    /*flightRepo.save(secondBusFlight);
                    flightRepo.save(thirdBusFlight);
                    flightRepo.save(fourthBusFlight);
                    flightRepo.save(fifthBusFlight);*/
                }else {
                    System.out.println("Done");
                }
            }catch (Exception e){

            }
            // Done output, information:
            System.out.println("In data base was added:" +
                    "\n Drivers: [default, Олег, Иван, Василий, Егор, Семен]" +
                    "\n TypeBus: [default, Mercedes A-314, Ford LA-600, Opel S-30, Opel S-40, Mercedes S-400]" +
                    "\n TypeFlight: [Пригородный, Междугородний]" +
                    "\n FlightsTo: [Белебей, Казань, Самара, Нефтекамск, Москва]" +
                    "\n" +
                    "\n ********************************* DONE *********************************");



        }
}
