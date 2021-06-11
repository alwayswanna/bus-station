package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.User;
import a.gleb.bus_station.exceptions.DuplicateUserException;
import a.gleb.bus_station.repositories.AdministratorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdministratorService {

    private final AdministratorRepo administratorRepo;

    @Autowired
    public AdministratorService(AdministratorRepo administratorRepo) {
        this.administratorRepo = administratorRepo;
    }

    public User getUserById(Integer id){
        User user = administratorRepo.findUserById(id);
        if (user == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find USER with [ID]: " + id);
        }else {
            return user;
        }
    }

    public User getUserByLogin(String login){
        User user = administratorRepo.findByUserName(login);
        if (user == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find USER with [Login]: " + login);
        }else{
            return user;
        }
    }

    public Iterable<User> addNewUser(User user){
        User addUser = administratorRepo.findUserById(user.getId());
        if (addUser != null){
            throw new DuplicateUserException("DuplicateUserException: USER with [ID]: " + user.getId() + " already exist");
        }else{
            administratorRepo.save(user);
            return returnAllUsers();
        }
    }

    public Iterable<User> returnAllUsers(){
        Iterable<User> allUsers = administratorRepo.findAll();
        if (allUsers.iterator().next() == null){
            throw new NoSuchElementException("NoSuchElementException: can`t find USERs in database");
        }else{
            return allUsers;
        }
    }
}
