package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import graphic.main.Button;
import model.game.Deck;

import java.util.ArrayList;

public class DeckTexture {

    private float x = 0;
    private float y = 0;
    private ArrayList<Button> cards;
    private String name;

    public DeckTexture(Deck deck) {
        this.name = deck.getName();
    }
}
