package pwayner.com.homeinvapp;

public class ShoppingList {
    String name;
    double priceC, priceW;

    public ShoppingList(){

    }

    public ShoppingList(String name, double priceC, double priceW){
        this.name = name;
        this.priceC = priceC;
        this.priceW = priceW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceC() {
        return priceC;
    }

    public void setPriceC(double priceC) {
        this.priceC = priceC;
    }

    public double getPriceW() {
        return priceW;
    }

    public void setPriceW(double priceW) {
        this.priceW = priceW;
    }
}
