package a.gleb.bus_station.service;

import a.gleb.bus_station.dto.User;
import a.gleb.bus_station.repositories.AdministratorRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final AdministratorRepo administratorRepo;

    public UserService(AdministratorRepo administratorRepo) {
        this.administratorRepo = administratorRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return administratorRepo.findByUserName(username);
    }

    public boolean addUser(User user){
        User userFromDb = administratorRepo.findByUserName(user.getUserName());
        if (userFromDb != null){
            return false;
        }else{
            administratorRepo.save(user);
            return true;
        }
    }
}
