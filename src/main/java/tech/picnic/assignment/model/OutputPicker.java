package tech.picnic.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("unused")
public class OutputPicker {

    private String pickerName;
    private String activeSince;
    private String id;
    private Set<OutputPick> picks = new TreeSet<>(getComparator());

    private static Comparator<OutputPick> getComparator() {
        return Comparator.comparing(OutputPick::getTimestamp);
    }

    public OutputPicker(String pickerName, String activeSince, String id) {
        this.pickerName = pickerName;
        this.activeSince = activeSince;
        this.id = id;
    }

    public void addItem(String articleName, String timestamp) {
        OutputPick outputPick = new OutputPick(articleName, timestamp);
        picks.add(outputPick);
    }

    public String getPickerName() {
        return pickerName;
    }

    public String getActiveSince() {
        return activeSince;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public Set<OutputPick> getPicks() {
        return picks;
    }
}
