package tech.picnic.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {

    private String id;
    private String name;
    private TemperatureZone temperature_zone;

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemperatureZone getTemperature_zone() {
        return temperature_zone;
    }

    public void setTemperature_zone(TemperatureZone temperature_zone) {
        this.temperature_zone = temperature_zone;
    }

    public enum TemperatureZone {
        @JsonProperty("ambient")
        AMBIENT,
        @JsonProperty("chilled")
        CHILLED;
    }

}
