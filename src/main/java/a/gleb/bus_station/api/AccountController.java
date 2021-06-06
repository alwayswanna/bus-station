package a.gleb.bus_station.api;

import a.gleb.bus_station.dto.User;
import a.gleb.bus_station.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<User> getAllUsers(){
        return accountService.getAllUsers();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Integer id){
        return accountService.getSelectedUserById(id);
    }

    @RequestMapping(value = "/user",  method = RequestMethod.PUT)
    public User updateSelectedUser(@RequestBody User user){
        return accountService.editSelectedUser(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Iterable<User> createNewUser(@RequestBody User user){
        return accountService.createNewUser(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Iterable<User> deleteUser(@PathVariable Integer id){
        return accountService.deleteSelectedUser(id);
    }

}
