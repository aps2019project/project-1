package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;
import graphic.main.Button;
import model.game.Deck;

import java.util.ArrayList;

public class DeckTexture {

    private float x;
    private float y;
    private ArrayList<Button> cards;
    private Button deckName;
    private Deck deck;

    public DeckTexture(Deck deck, float x, float y) {
        this.deck = deck;
        this.x = x;
        this.y = y;
        BitmapFont font = new BitmapFont(AssetHandler.getData().get("fonts/Arial 20.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 20.fnt", BitmapFont.class).getRegions(), true);
        this.deckName = new Button("button/deckName.png", x, y - 55, deck.getName(), font);
        y -= 55;
        cards = new ArrayList<Button>();
        for (int i = 0; i < deck.size(); ++i) {
            cards.add(new Button("button/card in deck.png", "button/card in deck1.png", x, y - 40, 200, 40, deck.getCards().getAllCards().get(i).getName(), "fonts/Arial 16.fnt"));
            y -= 40;
        }
    }

    public void setActive(Vector2 pos) {
        for (Button button: cards)
            button.setActive(button.contains(pos));
    }

    public boolean contains(Vector2 pos) {
        for (Button button: cards) {
            if (button.contains(pos))
                return true;
        }
        return false;
    }

    public String getCardName(Vector2 pos) {
        if (!contains(pos))
            return "";
        for (Button button: cards) {
            if (button.contains(pos))
                return deck.getCards().getAllCards().get(cards.indexOf(button)).getName();
        }
        return "";
    }

    public void draw(SpriteBatch batch) {
        deckName.draw(batch);
        for (Button button: cards)
            button.draw(batch);
    }


}
