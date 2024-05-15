package Mizdooni.Model.Restaurant;

import Mizdooni.Model.Address;
import Mizdooni.Model.Constants.*;
import Mizdooni.Model.ResponseHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import static Mizdooni.Model.Constants.RESTAURANTS_TABLE_NAME;

@Entity
@Getter
@Data
@Table(name = RESTAURANTS_TABLE_NAME)
public class Restaurant {
    @Id
    @Column(name = "name")
    public String name;
    @Column(name = "managerUsername")
    public String managerUsername;
    @Column(name = "type")
    public String type;
    @Column(name = "startTime")
    public String startTime;
    @Column(name = "endTime")
    public String endTime;
    @Column(name = "description")
    public String description;
    @Column(name = "image")
    public String image;
    @Column(name = "address")
    public Address address = new Address();

    @JsonIgnore
    public double overall;
    @JsonIgnore
    private String id;

    public Restaurant(
            @JsonProperty("address") Address address,
            @JsonProperty("description") String description,
            @JsonProperty("endTime") String endTime,
            @JsonProperty("image") String image,
            @JsonProperty("managerUsername") String managerUsername,
            @JsonProperty("name")String name,
            @JsonProperty("startTime") String startTime,
            @JsonProperty("type") String type
        ) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.address = address;
        this.image = image;
        this.managerUsername = managerUsername;
    }
    public Restaurant(){};

}
