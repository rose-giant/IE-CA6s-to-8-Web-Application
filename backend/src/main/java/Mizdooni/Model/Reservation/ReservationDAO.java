package Mizdooni.Model.Reservation;

import Mizdooni.Model.Constants;
import Mizdooni.Model.Reservation.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ReservationDAO {
    public ArrayList<Reservation> getFromAPI() throws Exception{
//        FileInputStream input = new FileInputStream("C:\\Users\\Fateme\\IdeaProjects\\Gorgeous-IE\\CA4\\React-Web-Application-Back-end-API\\backend\\src\\main\\resources\\Reservations.json");
//        String ReservationsJsonString = input.toString();
        String ReservationsJsonString = "[{\"username\": \"Mostafa_Ebrahimi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 1, \"datetime\": \"1986-04-08 12:30\"}, {\"username\": \"Arshia_Abolghasemi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 1, \"datetime\": \"1986-04-09 12:30\"},{\"username\": \"MohammadSadegh_Aboofazeli\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 1, \"datetime\": \"2000-04-08 12:30\"},{\"username\": \"Mostafa_Ebrahimi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 4, \"datetime\": \"1986-04-08 12:30\"}, {\"username\": \"Arshia_Abolghasemi\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 3, \"datetime\": \"1986-04-09 12:30\"},{\"username\": \"MohammadSadegh_Aboofazeli\", \"restaurantName\": \"Sullivan's Steakhouse\", \"tableNumber\": 3, \"datetime\": \"2000-04-08 12:30\"}]";
        ObjectMapper om = new ObjectMapper();
        return om.readValue(ReservationsJsonString, new TypeReference<ArrayList<Reservation>>(){});
    }
}
