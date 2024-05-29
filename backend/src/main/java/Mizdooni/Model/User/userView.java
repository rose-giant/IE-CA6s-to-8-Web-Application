package Mizdooni.Model.User;

import Mizdooni.Model.Address;
import Mizdooni.Security.Hash;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class userView {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String country;
    @NonNull
    private String city;
    @NonNull
    private String password;
    @NonNull
    private String role;


    public User viewToUser() throws Exception {
        String hashPass = Hash.doHash(password);
        return new User(new Address(city, country),email, hashPass, role, username );
    }
}

