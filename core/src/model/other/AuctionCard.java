package model.other;

public class AuctionCard {
    private int id;
    private String seller;
    private String cardName;
    private String username;
    private int lastOffer;
    private float remainingTime;

    public String getCardName() {
        return cardName;
    }

    public String getUsername() {
        return username;
    }

    public int getLastOffer() {
        return lastOffer;
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public int getId() {
        return id;
    }

    public String getSeller() {
        return seller;
    }
}
