package tech.picnic.assignment.model;

@SuppressWarnings("unused")
public class OutputPick {

    private String articleName;
    private String timestamp;

    public OutputPick(String articleName, String timestamp) {
        this.articleName = articleName;
        this.timestamp = timestamp;
    }

    public String getArticleName() {
        return articleName.toUpperCase();
    }

    public String getTimestamp() {
        return timestamp;
    }

}
