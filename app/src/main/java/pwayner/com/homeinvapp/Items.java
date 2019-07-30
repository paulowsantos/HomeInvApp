package pwayner.com.homeinvapp;

public class Items {
    String name, category;
    int id, qtt, qtt_min;

    public Items(){

    }

    public Items(String name, String category, int id, int qtt, int qtt_min){
        this.name = name;
        this.category = category;
        this.id = id;
        this.qtt = qtt;
        this.qtt_min = qtt_min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

    public int getQtt_min() {
        return qtt_min;
    }

    public void setQtt_min(int qtt_min) {
        this.qtt_min = qtt_min;
    }
}
