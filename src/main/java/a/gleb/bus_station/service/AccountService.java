package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.User;
import a.gleb.bus_station.exceptions.DuplicateUserNameException;
import a.gleb.bus_station.repositories.AdministratorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final AdministratorRepo administratorRepo;

    @Autowired
    public AccountService(AdministratorRepo administratorRepo) {
        this.administratorRepo = administratorRepo;
    }

    public Iterable<User> getAllUsers(){
        Iterable<User> userList = administratorRepo.findAll();
        if (userList.iterator().next() == null){
            throw new NoSuchElementException();
        }else{
            return userList;
        }
    }

    public User getSelectedUserById(Integer id){
        User selectedUser = administratorRepo.findUserById(id);
        if (selectedUser == null){
            throw new NoSuchElementException();
        }else {
            return selectedUser;
        }
    }

    public User editSelectedUser(User user){
        User editUser = administratorRepo.findUserById(user.getId());
        if (editUser == null){
            throw new NoSuchElementException();
        }else{
            editUser.setUserName(user.getUserName());
            editUser.setPassword(user.getPassword());
            editUser.setName(user.getName());
            editUser.setUserSurname(user.getUserSurname());
            editUser.setUserPhone(user.getUserPhone());
            editUser.setActive(user.isActive());
            editUser.setRoles(user.getRoles());
            administratorRepo.save(editUser);
            return editUser;
        }
    }

    public Iterable<User> createNewUser(User user){
        User checkUser = administratorRepo.findByUserName(user.getUserName());
        if (checkUser == null){
            administratorRepo.save(user);
            return administratorRepo.findAll();
        }else {
            throw new DuplicateUserNameException("DuplicateUserNameException: user with [username]: " + user.getUserName() + " already exist");
        }
    }

    public Iterable<User> deleteSelectedUser(Integer id){
        User deleteUser = administratorRepo.findUserById(id);
        if (deleteUser == null){
            throw new NoSuchElementException();
        }else {
            administratorRepo.delete(deleteUser);
            return administratorRepo.findAll();
        }
    }


}
