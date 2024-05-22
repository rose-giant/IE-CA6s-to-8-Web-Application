package Mizdooni.Model.User;

import Mizdooni.Model.Address;

import Mizdooni.Model.Reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Data
@Table(name = "user")
public class User implements CredentialsContainer, UserDetails {
    @Id
    @Column(name = "username")
    public String username;
    @Column(name = "password")
    public  String password;
    @Column(name = "email")
    public String email;
    @Column(name = "address")
    public Address address;

    public String role;

    @JsonIgnore
    public ArrayList<Reservation> reservations = new ArrayList<>();

    public User(@JsonProperty("address") Address address,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password,
                @JsonProperty("role") String role,
                @JsonProperty("username") String username
                ){
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }
    public User(){};

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public Object getAddress() {
        return this.address;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public void eraseCredentials() {

    }
//    public User(String username, String password, String role){};
}
