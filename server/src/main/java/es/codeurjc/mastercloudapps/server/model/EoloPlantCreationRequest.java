package es.codeurjc.mastercloudapps.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class EoloPlantCreationRequest implements Serializable {

    private static final long serialVersionUID = 5511349529410950840L;

    private int id;
    private int userId;
    private String city;

    public EoloPlantCreationRequest() {}

    public EoloPlantCreationRequest(
            @JsonProperty("id") int id,
            @JsonProperty("userId") int userId,
            @JsonProperty("city") String city) {
        this.id = id;
        this.userId = userId;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "'userId'=" + userId +
                ", 'city'=" + city + '}';
    }
}
