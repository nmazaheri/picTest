package tech.picnic.assignment.model;

import java.util.Objects;

public class Picker {

    private String id;
    private String name;
    private String active_since;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive_since() {
        return active_since;
    }

    public void setActive_since(String active_since) {
        this.active_since = active_since;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picker picker = (Picker) o;
        return id.equals(picker.id) &&
                name.equals(picker.name) &&
                active_since.equals(picker.active_since);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active_since);
    }
}
