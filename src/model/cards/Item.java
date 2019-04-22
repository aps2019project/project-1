package model.cards;

import java.util.ArrayList;

public class Item extends Card {
    private static ArrayList<Item> items = new ArrayList<>();
    private ItemType itemType;

    Item(String name, int price, ItemType itemType){
        super(name, price);
        this.itemType = itemType;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public static void scanItems(ArrayList<String[]> data){
        for(String[] line : data){
            new Item(line[1]
                    , Integer.parseInt(line[2])
                    , ItemType.valueOf(line[3].toUpperCase()));
        }
    }
}
