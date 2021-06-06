package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.BusDriver;
import a.gleb.bus_station.exceptions.DuplicateOfDriverLicenseException;
import a.gleb.bus_station.repositories.DriversRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DriverService {

    private final DriversRepo driversRepo;

    @Autowired
    public DriverService(DriversRepo driversRepo) {
        this.driversRepo = driversRepo;
    }

    public BusDriver addNewDriver(BusDriver driver){
        BusDriver checkDriver = driversRepo.findAllByDriverLicense(driver.getDriverLicense());
        if (checkDriver == null){
            driversRepo.save(driver);
            return driver;
        }else{
            throw new DuplicateOfDriverLicenseException("Driver with [License] = " + driver.getDriverLicense() + " already exist");
        }
    }

    public BusDriver getSelectedDriver(Integer id){
        int driverId = id;
        BusDriver driver = driversRepo.findById(driverId);
        if (driver == null){
            throw new NoSuchElementException("NoSuchElementException: can`t  find driver with [ID]: " + driverId);
        }else{
            return driver;
        }
    }

    public Iterable<BusDriver> getAllDrivers(){
        Iterable<BusDriver> listDrivers = driversRepo.findAll();
        if (listDrivers.iterator().next() == null){
            throw new NoSuchElementException();
        }else{
            return listDrivers;
        }
    }


    public BusDriver editSelectedDriver(BusDriver driver) {
        int driverId = driver.getId();
        BusDriver driverForEdit = driversRepo.findById(driverId);
        if (driverForEdit == null){
            throw new NoSuchElementException();
        }else{
            driverForEdit.setDriverName(driver.getDriverName());
            driverForEdit.setDriverSurname(driver.getDriverSurname());
            driverForEdit.setDriverPhone(driver.getDriverPhone());
            driverForEdit.setDriverLicense(driver.getDriverLicense());
            driversRepo.save(driverForEdit);
            return driverForEdit;
        }
    }

    public Iterable<BusDriver> deleteSelectedDriver(Integer id) {
        int driverId = id;
        BusDriver driverForDelete = driversRepo.findById(driverId);
        if (driverForDelete == null){
            throw new NoSuchElementException("NoSuchElementException: no driver with [ID]: " + driverId);
        }else{
            driversRepo.delete(driverForDelete);
            return getAllDrivers();
        }
    }

    public BusDriver driverBySurname(String surname){
        BusDriver driver = driversRepo.findByDriverSurname(surname);
        if (driver == null){
            throw new NoSuchElementException();
        }else{
            return driver;
        }
    }
}
