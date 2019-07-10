package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import connection.Client;
import graphic.Others.CardTexture;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.cards.Card;
import model.other.Account;
import model.other.AuctionCard;

import java.awt.*;
import java.util.ArrayList;

public class AuctionScreen extends Screen {

    private boolean isOffering = false;
    private Texture background;
    private Texture sellerSlot;
    private BitmapFont sellerFont;
    private ArrayList<AuctionCard> cardArrayList;
    private ArrayList<Button> allAuctions;
    private Button increaseOffer;
    private Button backButton;
    private int selectedID = 0;
    private AuctionCard selectedCard;
    private CardTexture auctionCard;
    private Vector2 mousePos;
    private GlyphLayout glyphLayout;
    private Button daricPicture;
    private ShapeRenderer shapeRenderer;


    @Override
    public void create() {
        setCameraAndViewport();
        mousePos = new Vector2();
        background = AssetHandler.getData().get("backGround/auction back.png");
        sellerSlot = AssetHandler.getData().get("button/decks/activeDeck.png");

        sellerFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getRegions(), true);
        sellerFont.setColor(Main.toColor(new Color(0xFFC2FC)));
        backButton = new Button("button/back.png", 0, 850, 50, 50);
        increaseOffer = new Button("button/yellow.png", "button/yellow glow.png", 1350, 650, 100, 50, "+50 D", "fonts/Arial 24.fnt");
        glyphLayout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();

        auctionCard = null;
        selectedCard = new AuctionCard();
        cardArrayList = new ArrayList<AuctionCard>();
        allAuctions = new ArrayList<Button>();
        updateDaricShow();
    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);
        updateDaricShow();
        if (selectedID != 0) {
            Client.sendCommand("get auction " + selectedID);
            selectedCard = Client.getData(AuctionCard.class);
        }
        cardArrayList.clear();
        Client.sendCommand("get auction market");
        cardArrayList.addAll(Client.getArrayList(AuctionCard.class));

        allAuctions.clear();
        float yStart = Main.HEIGHT - (Main.HEIGHT - cardArrayList.size() * 80) / 2f - 80;
        boolean temp = false;
        for (AuctionCard card : cardArrayList) {
            allAuctions.add(new Button("button/auction card.png", 100, yStart, 300, 80, card.getCardName(), "fonts/Arial 20.fnt"));
            if (card.getUsername().equals(Account.getCurrentAccount().getUsername()) || card.getSeller().equals(Account.getCurrentAccount().getUsername())) {
                isOffering = true;
                temp = true;
            }
            yStart -= 80;
        }
        if (! temp)
            isOffering = false;

        if (selectedID != 0 && !isOffering) {
            if (selectedCard.getLastOffer() + 50 < Account.getCurrentAccount().getDaric())
                increaseOffer.setActive(increaseOffer.contains(mousePos));
        }
        else
            increaseOffer.setActive(false);

        backButton.setActive(backButton.contains(mousePos) && !isOffering);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (isOffering) {
                    return false;
                }
                for (Button temp: allAuctions)
                    temp.setActive(temp.contains(mousePos));

                if (containsArrayList(allAuctions)) {
                    for (Button temp: allAuctions) {
                        if (temp.isActive()) {
                            int index = allAuctions.indexOf(temp);
                            selectedCard = cardArrayList.get(index);
                            selectedID = selectedCard.getId();
                            auctionCard = new CardTexture(Card.getCards().findByName(selectedCard.getCardName()));
                        }
                    }
                }

                if (increaseOffer.isActive()) {
                    if (Account.getCurrentAccount().getDaric() > selectedCard.getLastOffer() + 50)
                        Client.sendCommand("new offer " + Account.getCurrentAccount().getUsername() + " " + selectedID + " " + (selectedCard.getLastOffer() + 50));
                }

                if (backButton.isActive())
                    ScreenManager.setScreen(new ShopScreen());

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        if (auctionCard != null) {
            batch.begin();
            batch.draw(sellerSlot,1018, 250, 200, 100);
            glyphLayout.setText(sellerFont, String.valueOf(selectedCard.getLastOffer()));
            sellerFont.draw(batch, String.valueOf(selectedCard.getLastOffer()), 1018 + (200 - glyphLayout.width) / 2, 250 + glyphLayout.height + 30);
            batch.end();
            auctionCard.draw(batch, 1000, 350);
        }
        backButton.draw(batch);
        for (Button button : allAuctions)
            button.draw(batch);
        if (selectedID != 0 && Account.getCurrentAccount().getDaric() > selectedCard.getLastOffer() + 50)
            increaseOffer.draw(batch);
        daricPicture.draw(batch);
        if (selectedCard != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 1);
            shapeRenderer.rect(1000, 70, 400 * selectedCard.getRemainingTime(), 20);
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {

    }

    private boolean containsArrayList(ArrayList<Button> buttons) {
        for (Button button : buttons) {
            if (button.contains(mousePos)) return true;
        }
        return false;
    }

    private void updateDaricShow() {
        BitmapFont font = new BitmapFont(AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getRegions(), true);
        font.setColor(Main.toColor(new Color(0xFF6100)));
        daricPicture = new Button("button/daric slot.png", 1200, 50, String.valueOf(Account.getCurrentAccount().getDaric()), font);
    }

}
