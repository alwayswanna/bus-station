package a.gleb.bus_station.config;

import a.gleb.bus_station.dto.BusDriver;
import a.gleb.bus_station.dto.BusFlights;
import a.gleb.bus_station.dto.TypeBus;
import a.gleb.bus_station.dto.TypeFlight;
import a.gleb.bus_station.service.BusService;
import a.gleb.bus_station.service.DriverService;
import a.gleb.bus_station.service.FlightService;
import a.gleb.bus_station.service.TypeFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StarUpLoadToDataBase implements CommandLineRunner {

    private final FlightService flightService;
    private final DriverService driverService;
    private final TypeFlightService typeFlightService;
    private final BusService busService;

    @Autowired
    public StarUpLoadToDataBase(FlightService flightService, DriverService driverService, TypeFlightService typeFlightService, BusService busService) {
        this.flightService = flightService;
        this.driverService = driverService;
        this.typeFlightService = typeFlightService;
        this.busService = busService;
    }

    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String dateOfFlight = simpleDateFormat.format(date);



    @Override
    public void run(String... args) throws Exception {

           /* User defaultAdministrator = new User("Administrator", passwordEncoder.encode("administrator"), "Евгений", "Шаповников",
                    "+79006506060", "e.shapovnikov@busstation.ru", true, Collections.singleton(Role.ADMINISTRATOR));
            if (administratorRepo.findByUserName("Administrator") == null){
                administratorRepo.save(defaultAdministrator);
                System.out.println("Пользователь [Administrator] успешно создан. Administrator:administrator");
            }else {
                System.out.println("Пользователь [Administrator] уже создан. Administrator:administrator");
            }*/

            // Adding default values for Drivers:
            BusDriver defaultDriver = new BusDriver("смена водителя", "Идет", "Не определен", "null", null);
            BusDriver firstDriver = new BusDriver("Олег", "Иванов", "+79068568585", "UJ-708", null);
            BusDriver secondDriver = new BusDriver("Иван", "Меньшов", "+79058569645", "UJ-908", null);
            BusDriver thirdDriver = new BusDriver("Василий", "Петров", "+79875696365", "UJ-8080", null);
            BusDriver fourthDriver = new BusDriver("Егор", "Котов", "+79856565855", "UJ-809", null);
             BusDriver fifthDriver = new BusDriver("Семен", "Кузнецов", "+79056363859", "UJ-808", null);
            defaultDriver.setId(1);
            try{
                if (driverService.driverBySurname("Кузнецов") == null){
                    driverService.addNewDriver(defaultDriver);
                    driverService.addNewDriver(fifthDriver);
                    driverService.addNewDriver(firstDriver);
                    driverService.addNewDriver(secondDriver);
                    driverService.addNewDriver(thirdDriver);
                    driverService.addNewDriver(fourthDriver);
                    driverService.addNewDriver(fifthDriver);
                }else{
                    System.out.println("Done");
                }
            }catch (Exception ignored){}
            // Adding default buses for TypeBus:
            TypeBus defaultBus = new TypeBus("Не определен", 0,"Не определен", null);
            TypeBus firstTypeBus = new TypeBus("Междугородний", 60, "Mercedes A-314", null);
            TypeBus secondTypeBus = new TypeBus("Междугородний", 60, "Ford LA-600", null);
            TypeBus thirdTypeBus = new TypeBus("Пригородный", 20, "Opel S-30", null);
            TypeBus fourthTypeBus = new TypeBus("Пригородный", 20, "Opel S-40", null);
            TypeBus fifthTypeBus = new TypeBus("Пригородный", 25, "Mercedes S-400", null);
            defaultBus.setId(1);
            try{
                if (busService.busByNameModel("Mercedes A-314") == null){
                    busService.addNewTypeBus(defaultBus);
                    busService.addNewTypeBus(firstTypeBus);
                    busService.addNewTypeBus(secondTypeBus);
                    busService.addNewTypeBus(thirdTypeBus);
                    busService.addNewTypeBus(fourthTypeBus);
                    busService.addNewTypeBus(fifthTypeBus);
                }
            }catch (Exception ignored){}
            // Adding default types of flights for TypeFlight:
            TypeFlight intercityTypeFlight = new TypeFlight("Междугородний", null);
            TypeFlight suburbanTypeFlight = new TypeFlight("Пригородный", null);
            try{
                if (typeFlightService.selectedTypeOfFlight("Пригородный") == null){
                    typeFlightService.addNewTypeOfFlight(intercityTypeFlight);
                    typeFlightService.addNewTypeOfFlight(suburbanTypeFlight);
                }else{
                    System.out.println("Done");
                }
            }catch (Exception ignored){}
            // Adding flights for BusFlights:
            BusFlights defaultFlight = new BusFlights("null", "null", "null", "null", "null", "null", "null", defaultDriver, defaultBus, suburbanTypeFlight, null);
            BusFlights firstBusFlight = new BusFlights("Прибывающий", "Белорец", "Уфа", "10:00", "12:55", dateOfFlight, "УБ-45", firstDriver, secondTypeBus, suburbanTypeFlight, null);
            try{
                if (flightService.returnFlightByUniqueNumber("УБ-45") == null){
                    flightService.addNewFlight(defaultFlight);
                    flightService.addNewFlight(firstBusFlight);
                }else {
                    System.out.println("Done");
                }
            }catch (Exception ignored){}
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
