package model.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.cards.Card;
import model.game.Deck;
import model.variables.CardsArray;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class DeckSavingObject {
    String name;
    ArrayList<String> cards;

    DeckSavingObject(Deck deck) {
        this.name = deck.getName();
        this.cards = new ArrayList<String>();
        for (Card card: deck.getCards().getAllCards()) {
            cards.add(card.getName());
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public static void saveDeck(Deck deck, String path) throws Exception {
        DeckSavingObject savingObjects = new DeckSavingObject(deck);
        path = path + "/" + savingObjects.getName() + ".deck";
        Gson gson = new GsonBuilder().create();
        Writer writer = new FileWriter(path);
        writer.write(gson.toJson(savingObjects));
        writer.close();
    }

    public static Deck readDeck(String deckPath) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        Scanner reader;
        String str = "";
        DeckSavingObject data;

        reader = new Scanner(new File(deckPath));
        str = reader.nextLine();
        data = gson.fromJson(str, DeckSavingObject.class);
        reader.close();
        return getDeckFromObject(data);
    }

    public static Deck getDeckFromObject(DeckSavingObject savingObject) {
        CardsArray cardsArray = new CardsArray(savingObject.cards, Account.getCurrentAccount().getUsername());
        return new Deck(savingObject.getName(), cardsArray);
    }
}
