package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.User;
import a.gleb.bus_station.repositories.AdministratorRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/administrations")
public class AdminController {

    private final AdministratorRepo administratorRepo;

    public AdminController(AdministratorRepo administratorRepo) {
        this.administratorRepo = administratorRepo;
    }

    @RequestMapping(value = "/administrator/users", method = RequestMethod.GET)
    public String administratorUsersGet(Map<String, Object> model){
        Iterable<User> users = administratorRepo.findAll();
        model.put("user", users);
        return "administratorUsers";
    }

    @RequestMapping(value = "/administrator/add_user", method = RequestMethod.GET)
    public String administratorUserAddGet(Map<String, Object> model){
        return "administratorAddUser";
    }

    @RequestMapping(value = "/administrator/add_user", method = RequestMethod.POST)
    public String administratorUserAddPost(@RequestParam String login,
                                           @RequestParam String password,
                                           @RequestParam String userName,
                                           @RequestParam String userSurname,
                                           @RequestParam String userPhone,
                                           @RequestParam String userEmail,
                                           @RequestParam boolean active,
                                           Map<String, Object> model){


        return "redirect:/administrations/administrator/users";
    }


}
