package tech.picnic.assignment.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OutputPicker {

    private String picker_name;
    private String active_since;
    private String id;
    private List<OutputPick> picks = new ArrayList<>();

    public OutputPicker(String picker_name, String active_since, String id) {
        this.picker_name = picker_name;
        this.active_since = active_since;
        this.id = id;
    }

    public void addItem(String articleName, String timestamp) {
        OutputPick outputPick = new OutputPick(articleName, timestamp);
        picks.add(outputPick);
    }

    public String getPicker_name() {
        return picker_name;
    }

    public String getActive_since() {
        return active_since;
    }

    public List<OutputPick> getPicks() {
        return picks;
    }

    public static class ActiveSinceComparator implements Comparator<OutputPicker> {
        @Override
        public int compare(OutputPicker o1, OutputPicker o2) {
            if (o1.active_since.equals(o2.active_since)) {
                return o1.id.compareTo(o2.id);
            }
            return o1.active_since.compareTo(o2.active_since);
        }
    }
}
