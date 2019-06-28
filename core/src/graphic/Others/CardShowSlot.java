package graphic.Others;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import graphic.screen.CardListTexture;
import model.cards.Hero;
import model.cards.Item;
import model.cards.Minion;
import model.cards.Spell;
import model.variables.CardsArray;

import java.awt.*;

public class CardShowSlot {
    private int xSize;
    private int ySize;
    private float x;
    private float y;
    private final int cardWidth = 250;
    private final int cardHeight = 328;
    private CardListTexture heroList;
    private CardListTexture minionList;
    private CardListTexture spellList;
    private CardListTexture itemList;
    private CardListTexture currentList;
    private Button heroButton;
    private Button minionButton;
    private Button spellButton;
    private Button itemButton;

    public CardShowSlot(CardsArray cardsArray, float x, float y, int xSize, int ySize) {
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
        BitmapFont font = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);
        font.setColor(Main.toColor(new Color(0xFFFDFD)));
        float buttonStartX = x + ((xSize * cardWidth + (xSize - 1) * 50) - 4 * 200) / 2f;
        float buttonStartY = y + ySize * (cardHeight + 2) + 20;
        heroButton = new Button("button/shop left.png", "button/shop left active.png", buttonStartX, buttonStartY, "Hero", font);
        minionButton = new Button("button/shop middle.png", "button/shop middle active.png", buttonStartX + 200, buttonStartY, "Minion", font);
        spellButton = new Button("button/shop middle.png", "button/shop middle active.png", buttonStartX + 400, buttonStartY, "Spell", font);
        itemButton = new Button("button/shop right.png", "button/shop right active.png", buttonStartX + 600, buttonStartY, "Item", font);
        currentList = new CardListTexture(xSize, ySize, x, y);
        updateLists(cardsArray);
    }

    public boolean contains(Vector2 pos) {
        if (itemButton.contains(pos)) return true;
        if (heroButton.contains(pos)) return true;
        if (spellButton.contains(pos)) return true;
        if (minionButton.contains(pos)) return true;
        if (currentList.contains(pos)) return true;
        return false;
    }

    public void update(Vector2 pos) {
        if (itemButton.contains(pos)) changeList(itemList);
        else if (heroButton.contains(pos)) changeList(heroList);
        else if (spellButton.contains(pos)) changeList(spellList);
        else if (minionButton.contains(pos)) changeList(minionList);
        else if (!currentList.contains(pos))
            changeList(new CardListTexture(xSize, ySize, x, y));
        if (!currentList.contains(pos)) {
            heroButton.setActive(heroButton.contains(pos));
            minionButton.setActive(minionButton.contains(pos));
            spellButton.setActive(spellButton.contains(pos));
            itemButton.setActive(itemButton.contains(pos));
        }
    }

    public void updateLists(CardsArray newList) {
        heroList = new CardListTexture(xSize, ySize, x, y);
        for (Hero temp:newList.getAllHeroes())
            heroList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));

        minionList = new CardListTexture(xSize, ySize, x, y);
        for (Minion temp: newList.getAllMinions())
            minionList.addCardTexture(new CardTexture( temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));

        spellList = new CardListTexture(xSize, ySize, x, y);
        for (Spell temp: newList.getAllSpells())
            spellList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(),temp.getGifPath()));

        itemList = new CardListTexture(xSize, ySize, x, y);
        for (Item temp: newList.getSellableItems())
            itemList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getGifPath()));

    }

    public void draw(SpriteBatch batch) {
        currentList.draw(batch);
        minionButton.draw(batch);
        heroButton.draw(batch);
        spellButton.draw(batch);
        itemButton.draw(batch);
    }

    public String getSelectedCard(Vector2 pos) {
        return currentList.getSelectedCardName(pos);
    }

    public void nextPage() {
        currentList.nextPage();
    }

    public void previousPage() {
        currentList.previousPage();
    }

    private void changeList(CardListTexture listTexture) {
        currentList.deActiveAllCards();
        currentList = listTexture;
    }



}
