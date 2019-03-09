package tech.picnic.assignment.model;

import java.util.Objects;

@SuppressWarnings("unused")
public class Picker {

    private String id;
    private String name;
    private String activeSince;

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

    public String getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(String activeSince) {
        this.activeSince = activeSince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picker picker = (Picker) o;
        return id.equals(picker.id) &&
                name.equals(picker.name) &&
                activeSince.equals(picker.activeSince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, activeSince);
    }
}
