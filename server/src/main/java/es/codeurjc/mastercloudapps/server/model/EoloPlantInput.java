package es.codeurjc.mastercloudapps.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class EoloPlantInput implements Serializable {

    private static final long serialVersionUID = 5511349529410950840L;

    private int userId;
    private String city;

    public EoloPlantInput() {}

    public EoloPlantInput(@JsonProperty("userId") int id,
                     @JsonProperty("city") String city) {
        this.userId = id;
        this.city = city;
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
