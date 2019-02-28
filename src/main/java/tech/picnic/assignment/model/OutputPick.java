package tech.picnic.assignment.model;

import java.util.Comparator;

public class OutputPick {

    private String article_name;
    private String timestamp;

    public OutputPick(String article_name, String timestamp) {
        this.article_name = article_name;
        this.timestamp = timestamp;
    }

    public String getArticle_name() {
        return article_name.toUpperCase();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static class TimestampComparator implements Comparator<OutputPick> {
        @Override
        public int compare(OutputPick o1, OutputPick o2) {
            return o1.timestamp.compareTo(o2.timestamp);
        }
    }
}
