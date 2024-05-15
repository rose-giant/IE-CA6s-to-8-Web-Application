package Mizdooni.Model.User;

import Mizdooni.Model.Address;

import Mizdooni.Model.Reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import java.util.ArrayList;


@Entity
@Table(name = "user")
@Getter
public class User {
    @Id
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
//    public User(String username, String password, String role){};
}
