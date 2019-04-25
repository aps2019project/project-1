package model.other;

public class Shop {
    private static final Shop shop = new Shop();
    public static Shop getInstance(){
        return shop;
    }
    private Shop(){
    }
}
