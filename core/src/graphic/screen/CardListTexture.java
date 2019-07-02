package graphic.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.CardTexture;

import java.util.ArrayList;

public class CardListTexture {

    private ArrayList<CardTexture> cardTextures;
    private ArrayList<Rectangle> rectangles;
    private int xSize;
    private int ySize;
    private int totalPage;
    private int currentPage;
    private float x;
    private float y;

    public CardListTexture(int xSize, int ySize, float x, float y) {
        this.x = x;
        this.y = y;
        this.cardTextures = new ArrayList<CardTexture>();
        this.xSize = xSize;
        this.ySize = ySize;
        createRectangles();
        totalPage = cardTextures.size() / (xSize * ySize);
        currentPage = 0;
    }

    public CardListTexture(ArrayList<CardTexture> cardTextures, int xSize, int ySize, float x, float y) {
        this(xSize, ySize, x, y);
        this.cardTextures.addAll(cardTextures);
        totalPage = cardTextures.size() / (xSize * ySize);

    }

    public void addCardTexture(CardTexture cardTexture) {
        cardTextures.add(cardTexture);
        totalPage = cardTextures.size() / (xSize * ySize);
    }

    public void addCardTexture(String name) {
        addCardTexture(new CardTexture(name, "default information:\nthis is just for test", 0, 0, 0, "Card/Hero/8.atlas"));
        totalPage = cardTextures.size() / (xSize * ySize);
    }

    public void nextPage() {
        if (currentPage < totalPage)
            currentPage++;
    }

    public void previousPage() {
        if (currentPage > 0)
            currentPage--;
    }

    public void draw(SpriteBatch batch) {
        int num = 0;
        for (float j = (ySize - 1) * 330 + y; j >= y; j -= 330) {
            for (int i = 0; i < xSize; ++i) {
                if (cardTextures.size() > num + (xSize * ySize * currentPage))
                    cardTextures.get(num + (xSize * ySize * currentPage)).draw(batch, x + i * 300, j);
                else
                    return;
                num++;
            }
        }
    }

    public String getSelectedCardName(Vector2 pos) {
        for (int i = 0; i < rectangles.size(); ++i) {
            if (rectangles.get(i).contains(pos)) {
                if (cardTextures.size() > i + currentPage * xSize * ySize) {
                    int index = i + xSize * ySize * currentPage;
                    deActiveAllCards();
                    cardTextures.get(index).setActive(true);
                    return cardTextures.get(index).getName();
                }
            }
        }
        deActiveAllCards();
        return "";
    }

    public boolean contains(Vector2 pos) {
        for (Rectangle rectangle: rectangles) {
            if (rectangle.contains(pos))
                return true;
        }
        return false;
    }

    public void resetCurrentPage() {
        this.currentPage = 0;
    }

    private void createRectangles() {
        rectangles = new ArrayList<Rectangle>();
        for (float j = (ySize - 1) * 330 + y; j >= y; j -= 330) {
            for (int i = 0; i < xSize; ++i) {
                rectangles.add(new Rectangle(x + i * 300, j, 250, 328));
            }
        }
    }

    public void deActiveAllCards() {
        for (CardTexture cardTexture: cardTextures)
            cardTexture.setActive(false);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ArrayList<CardTexture> getCardTextures() {
        return cardTextures;
    }
}
