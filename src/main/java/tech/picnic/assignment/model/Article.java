package tech.picnic.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Article {

    private String id;
    private String name;
    private TemperatureZone temperatureZone;

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemperatureZone getTemperatureZone() {
        return temperatureZone;
    }

    public void setTemperatureZone(TemperatureZone temperatureZone) {
        this.temperatureZone = temperatureZone;
    }

    public enum TemperatureZone {
        @JsonProperty("ambient")
        AMBIENT,
        @JsonProperty("chilled")
        CHILLED;
    }

}
