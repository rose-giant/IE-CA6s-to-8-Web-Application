package Mizdooni.Model.Reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Getter
@Table(name = "reservation")
public class Reservation {

    @ManyToOne
    @JoinColumn(name = "reservation_username", nullable = false)
    public String username;

    @ManyToOne
    @JoinColumn(name = "reservation_restaurant", nullable = false)
    public String restaurantName;

    public int tableNumber;

    @Column(name = "datetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public String datetime;

    @JsonIgnore
    public LocalDateTime datetimeFormatted;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long reservationNumber;

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
