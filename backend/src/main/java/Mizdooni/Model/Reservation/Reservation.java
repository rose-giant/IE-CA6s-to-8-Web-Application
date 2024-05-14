package Mizdooni.Model.Reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Reservation {

    public String username;
    public String restaurantName;
    public int tableNumber;
    public String datetime;
    @JsonIgnore
    public LocalDateTime datetimeFormatted;
    @JsonIgnore
    public int reservationNumber;


    public Reservation(
            @JsonProperty("username") String username,
            @JsonProperty("restaurantName") String restaurantName,
            @JsonProperty("tableNumber")int tableNumber,
            @JsonProperty("datetime")String datetime) {
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.datetime = datetime;
    }
}
