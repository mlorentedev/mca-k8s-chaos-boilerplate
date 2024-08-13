package es.codeurjc.mastercloudapps.server.model;

import java.io.Serializable;

import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EoloPlant implements Serializable {

    private static final long serialVersionUID = 5511349529410950840L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@org.eclipse.microprofile.graphql.Id
    private long id;
    private long userId;
    private String city;
    private int progress = 0;
    private String planning;

    public EoloPlant() {}

    public EoloPlant(long id, String city) {
        this.userId = id;
        this.city = city;
        this.planning = city;
    }
    
    public EoloPlant(long id, long userId, String city, String planning, int progress) {
    	this(id, city);
        this.userId = userId;
    	this.planning = planning;
    	this.progress = progress;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getCity() {
        return city;
    }

    public int getProgress() {
        return progress;
    }

    public void advanceProgress() {
        this.progress += 25;
        this.progress = Math.min(this.progress, 100);
    }

    public boolean isCompleted() {
        return this.progress == 100;
    }

    public void addPlanning(String str) {
        this.planning += '-' + str;
        advanceProgress();
    }

    public String getPlanning() {
        return isCompleted()? planning : null;
    }

    public void processPlanning() {
        planning = planning.matches("^[A-Ma-m].*") ? 
            planning.toLowerCase() : 
            planning.toUpperCase();
        advanceProgress();
    }

    @Override
    public String toString() {
        return "Eoloplant{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", progress=" + progress +
                ", completed='" + isCompleted() + '\'' +
                ", planning='" + getPlanning() + '\'' +
                '}';
    }

    public void copy(EoloPlant other) {
        this.planning = other.planning;
        this.progress = other.progress;
    }
}
