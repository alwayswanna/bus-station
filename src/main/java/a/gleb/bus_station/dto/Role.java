package a.gleb.bus_station.dto;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    OPERATOR, ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
