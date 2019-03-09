package tech.picnic.assignment.model;

@SuppressWarnings("unused")
public class Pick {

    private String timestamp;
    private String id;
    private Picker picker;
    private Article article;
    private Integer quantity;

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Picker getPicker() {
        return picker;
    }

    public void setPicker(Picker picker) {
        this.picker = picker;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
