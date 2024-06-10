package Mizdooni.Model.Table;

import Mizdooni.Model.Reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Getter
@Data
@Table(name = "table")
public class TableRest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tableNumber")
    public int tableNumber;

    @ManyToOne
    @JoinColumn(name = "table_restaurant", nullable = false)
    public String restaurantName;

    @ManyToOne
    @JoinColumn(name = "table_manager", nullable = false)
    public String managerUsername;

    @Column(name = "seats")
    public int seatsNumber;
    @JsonIgnore
    public ArrayList<LocalDateTime> reservedDateTimes = new ArrayList<>();

    public TableRest(@JsonProperty("managerUsername") String managerUsername,
                     @JsonProperty("tableNumber") int tableNumber,
                     @JsonProperty("restaurantName") String restaurantName,
                     @JsonProperty("seatsNumber") int seatsNumber) {
        this.tableNumber = tableNumber;
        this.restaurantName = restaurantName;
        this.managerUsername = managerUsername;
        this.seatsNumber = seatsNumber;
    }

    public void removeReservation(Reservation reservation) {
        reservedDateTimes.remove(reservation.datetimeFormatted);
    }

    public boolean hasReservationAt(LocalDateTime newDT) {
        for (LocalDateTime reserved : reservedDateTimes) {
            if (reserved == newDT) {
                return true;
            }
        }
        return false;
    }
}