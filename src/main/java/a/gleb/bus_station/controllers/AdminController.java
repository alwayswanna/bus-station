package a.gleb.bus_station.controllers;

import a.gleb.bus_station.dto.Role;
import a.gleb.bus_station.dto.User;
import a.gleb.bus_station.repositories.AdministratorRepo;
import a.gleb.bus_station.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/administrations")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class AdminController {

    private final AdministratorRepo administratorRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AdminController(AdministratorRepo administratorRepo, PasswordEncoder passwordEncoder, UserService userService) {
        this.administratorRepo = administratorRepo;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @RequestMapping(value = "/administrator/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public String administratorUsersGet(Map<String, Object> model) {
        Iterable<User> users = administratorRepo.findAll();
        model.put("user", users);
        return "administratorUsers";
    }

    @RequestMapping(value = "/administrator/add_user", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public String administratorUserAddGet(Map<String, Object> model) {
        return "administratorAddUser";
    }

    @RequestMapping(value = "/administrator/add_user", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public String administratorUserAddPost(@RequestParam String userName,
                                           @RequestParam String password,
                                           @RequestParam String name,
                                           @RequestParam String userSurname,
                                           @RequestParam String userPhone,
                                           @RequestParam String userEmail,
                                           @RequestParam Role role,
                                           Map<String, Object> model,
                                           RedirectAttributes redirectAttributes) {
        if (userName.equals("") | password.equals("") | name.equals("") | userSurname.equals("") | userPhone.equals("") | userEmail.equals("")) {
            String errorMsg = "Вы заполнили не все поля!";
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/administrations/administrator/add_user";
        } else {
            User newUser = new User(userName, passwordEncoder.encode(password), name, userSurname, userPhone, userEmail,
                    true, Collections.singleton(role));
            if (userService.addUser(newUser)) {
                return "redirect:/administrations/administrator/users";
            } else {
                String errorMsg = "Пользователь с данным [Login] уже существует";
                redirectAttributes.addFlashAttribute("error", errorMsg);
                return "redirect:/administrations/administrator/add_user";
            }
        }
    }

    @RequestMapping(value = "/administrator/users/{userName}/del", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public String administratorUserDelete(@PathVariable(value = "userName") String userName, Map<String, Object> model) {
        User user = administratorRepo.findByUserName(userName);
        administratorRepo.delete(user);
        return "redirect:/administrations/administrator/users";
    }

    //TODO: create POST/GET methods for edit user;


}
