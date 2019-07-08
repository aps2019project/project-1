package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import connection.Client;
import graphic.Others.*;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.cards.*;
import model.other.Account;
import model.other.exeptions.shop.*;

import java.awt.*;
import java.util.ArrayList;

public class ShopScreen extends Screen {

    private Vector2 mousePos;
    private Texture backGround;
    private Texture middleGround;
    private Texture forGround;
    private Button sellButton;
    private Button buyButton;
    private Button backButton;
    private Button daricPicture;
    private Button collectionButton;
    private Button doneButton;
    private String selectedCard;
    private CardShowSlot allCard;
    private CardShowSlot playerCard = new CardShowSlot(Account.getCurrentAccount().getCollection(), 70, 140, 3, 2);
    private CardShowSlot currentList;
    private ArrayList<MoveAnimation> snowAnimation;


    @Override
    public void create() {
        setCameraAndViewport();
        createNewObjects();
        createSnowAnimation();
        createButtons();
        playBackGroundMusic("music/shop.mp3");
        updateCards();
        allCard = new CardShowSlot(Card.getCards(), 70, 140, 3, 2);
        currentList = allCard;
    }


    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);
        updateButtonOnTouch();
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.RIGHT)
                    currentList.nextPage();
                else if (keycode == Input.Keys.LEFT)
                    currentList.previousPage();
                else if (keycode == Input.Keys.PAGE_DOWN)
                    setMusicVolume(false);
                else if (keycode == Input.Keys.PAGE_UP)
                    setMusicVolume(true);
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
                currentList.update(mousePos);
                if (currentList.contains(mousePos)) {
                    selectedCard = currentList.getSelectedCard(mousePos);
                }
                if (buyButton.isActive())
                    currentList = allCard;
                if (sellButton.isActive())
                    currentList = playerCard;
                if (sellAndBuySelectedCard()) return false;
                if (backButton.isActive())
                    ScreenManager.setScreen(new MenuScreen());
                if (buyButton.contains(mousePos) || sellButton.contains(mousePos)) {
                    buyButton.setActive(buyButton.contains(mousePos));
                    sellButton.setActive(sellButton.contains(mousePos));
                }
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

            private boolean sellAndBuySelectedCard() {
                if (doneButton.isActive()) {
                    if (selectedCard.equals(""))
                        return true;
                    if (sellButton.isActive()) {
                        doneShopping("sellCard");
                        playerCard.updateLists(Account.getCurrentAccount().getCollection());
                    }
                    if (buyButton.isActive()) {
                        doneShopping("buyCard");
                        playerCard.updateLists(Account.getCurrentAccount().getCollection());
                    }
                }
                return false;
            }

            private void doneShopping(String methodName) {
                try {
                    if (methodName.equals("sellCard"))
                        Account.getCurrentAccount().sellCard(selectedCard);
                    else if (methodName.equals("buyCard"))
                        Account.getCurrentAccount().buyCard(selectedCard);
                    if (methodName.equals("sellCard"))
                        PopUp.getInstance().setText("Card Sold Successfully.");
                    else
                        PopUp.getInstance().setText("Card added to your Collection Successfully.");
                } catch (MoreThanTwoItemException e) {
                    PopUp.getInstance().setText("Can't Have More Than 2 Item At The Time.");
                } catch (CardNotFoundException e) {
                    PopUp.getInstance().setText("Cant Find Selected Card.");
                } catch (CardAlreadyExistsException e) {
                    PopUp.getInstance().setText("You Already Have This Card.");
                } catch (NotEnoughDaricException e) {
                    PopUp.getInstance().setText("You Don't Have Enough Money To Buy This Card.");
                } catch (CantSellCardException e) {
                    PopUp.getInstance().setText("You Can't Sell/Buy This Card");
                } catch (ShopExeption shopExeption) {
                    PopUp.getInstance().setText("Something Went Wrong. Try Contact With Creators.");
                }
                updateDaricShow();
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        drawBackGround(batch);
        drawButtons(batch);
        currentList.draw(batch);
    }

    @Override
    public void dispose() {
        music.stop();
        music.dispose();

    }

    private void createNewObjects() {
        backGround = AssetHandler.getData().get("backGround/shop1.png");
        middleGround = AssetHandler.getData().get("backGround/shop2.png");
        forGround = AssetHandler.getData().get("backGround/shop3.png");
        mousePos = new Vector2();
        selectedCard = "";
    }


    private void updateButtonOnTouch() {
        backButton.setActive(backButton.contains(mousePos));
        doneButton.setActive(doneButton.contains(mousePos));
    }


    private void drawButtons(SpriteBatch batch) {
        sellButton.draw(batch);
        buyButton.draw(batch);
        backButton.draw(batch);
        doneButton.draw(batch);
        daricPicture.draw(batch);
    }

    private void createSnowAnimation() {
        snowAnimation = new ArrayList<MoveAnimation>();
        for (int i = 0; i < 100; ++i) {
            int xStart = (int) (400 + 1200 * Math.random());
            int xEnd = (int) (xStart - 150 + 300 * Math.random());
            int random = (int) (Math.random() * 5);
            if (random > 3)
                snowAnimation.add(new MoveAnimation("animation/snow2.png", xStart, 900, xEnd, 250, MoveType.SIMPLE, true));
            else
                snowAnimation.add(new MoveAnimation("animation/snow.png", xStart, 900, xEnd, 250, MoveType.SIMPLE, true));
            snowAnimation.get(i).setSpeed((float) (0.2f + Math.random()));
        }
    }

    private void createButtons() {
        BitmapFont font = AssetHandler.getData().get("fonts/Arial 36.fnt");
        font.setColor(Main.toColor(new Color(0xFFFDFD)));
        sellButton = new Button("button/shop sb.png", "button/shop sb active.png", 1250, 350, "Sell", font);
        buyButton = new Button("button/shop sb.png", "button/shop sb active.png", 1250, 450, "Buy", font);
        buyButton.setActive(true);
        backButton = new Button("button/back.png", "button/back.png", 0, 850, 50,50);
        font = new BitmapFont(AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getRegions(), true);
        font.setColor(Main.toColor(new Color(0xBBCDFF)));
        doneButton = new Button("button/shop done.png", 50, 50, "Done", font);
        updateDaricShow();


    }

    private void updateDaricShow() {
        BitmapFont font = new BitmapFont(AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getRegions(), true);
        font.setColor(Main.toColor(new Color(0xFF6100)));
        daricPicture = new Button("button/daric slot.png", 1200, 50, String.valueOf(Account.getCurrentAccount().getDaric()), font);
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(middleGround, 0, 0);
        batch.end();
        for (MoveAnimation animation: snowAnimation)
            animation.draw(batch);
        batch.begin();
        batch.draw(forGround, 0, 0);
        batch.end();
    }

    public void updateCards() {
        Card.clearAllCards();
        Client.getCardFiles();
        Card.scanAllCards();
    }
}
