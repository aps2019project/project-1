package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.CardTexture;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.cards.*;
import model.other.Account;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ShopScreen extends Screen {

    private Vector2 mousePos;
    private Texture backGround;
    private Texture middleGround;
    private Texture forGround;
    private Button heroButton;
    private Button minionButton;
    private Button spellButton;
    private Button itemButton;
    private Button sellButton;
    private Button buyButton;
    private Button backButton;
    private Button daricPicture;
    private Button collectionButton;
    private Button doneButton;
    private final CardListTexture allHeroList = new CardListTexture(3, 2, 70, 140);
    private final CardListTexture allMinionList = new CardListTexture(3, 2, 70, 140);
    private final CardListTexture allSpellList = new CardListTexture(3, 2, 70, 140);
    private final CardListTexture allItemList = new CardListTexture(3, 2, 70, 140);
    private CardListTexture playerHeroList;
    private CardListTexture playerMinionList;
    private CardListTexture playerSpellList;
    private CardListTexture playerItemList;
    private CardListTexture currentList;
    private String selectedCard;
    private ArrayList<MoveAnimation> snowAnimation;


    @Override
    public void create() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAllCards();
            }
        }).start();
        setCameraAndViewport();
        createNewObjects();
        createSnowAnimation();
        createButtons();
        getPlayerCards();
        playBackGroundMusic("music/shop.mp3");
        currentList = allHeroList;
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
                if (keycode == Input.Keys.RIGHT) {
                    currentList.nextPage();
                }
                else if (keycode == Input.Keys.LEFT) {
                    currentList.previousPage();
                } else if (keycode == Input.Keys.PAGE_DOWN) {
                    setMusicVolume(false);
                } else if (keycode == Input.Keys.PAGE_UP) {
                    setMusicVolume(true);
                }
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
                updateButtonsOnClickActivation();
                if (currentList.contains(mousePos)) {
                    selectedCard = currentList.getSelectedCardName(mousePos);
                }
                if (sellAndBuySelectedCard()) return false;
                if (backButton.isActive())
                    ScreenManager.setScreen(new MenuScreen());
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

            private void updateButtonsOnClickActivation() {
                if (heroButton.contains(mousePos) || minionButton.contains(mousePos) || spellButton.contains(mousePos) || itemButton.contains(mousePos)) {
                    heroButton.setActive(heroButton.contains(mousePos));
                    minionButton.setActive(minionButton.contains(mousePos));
                    spellButton.setActive(spellButton.contains(mousePos));
                    itemButton.setActive(itemButton.contains(mousePos));
                    if (buyButton.isActive()) {
                        updateShowList(allHeroList, allMinionList, allItemList, allSpellList);
                    }
                    if (sellButton.isActive()) {
                        updateShowList(playerHeroList, playerMinionList, playerItemList, playerSpellList);
                    }
                    selectedCard = "";
                }
                else if (buyButton.contains(mousePos) || sellButton.contains(mousePos)) {
                    buyButton.setActive(buyButton.contains(mousePos));
                    sellButton.setActive(sellButton.contains(mousePos));
                    refreshAllLists();
                }
            }

            private boolean sellAndBuySelectedCard() {
                if (doneButton.isActive()) {
                    if (selectedCard.equals(""))
                        return true;
                    if (sellButton.isActive()) {
                        doneShopping("sellCard");
                    }
                    if (buyButton.isActive()) {
                        doneShopping("buyCard");
                    }
                }
                return false;
            }

            private void doneShopping(String methodName) {
                try {
                    Account.getCurrentAccount().getClass().getMethod(methodName, String.class).invoke(Account.getCurrentAccount(), selectedCard);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                getPlayerCards();
                refreshAllLists();
                updateDaricShow();
            }

            private void updateShowList(CardListTexture playerHeroList, CardListTexture playerMinionList, CardListTexture playerItemList, CardListTexture playerSpellList) {
                if (heroButton.isActive())
                    changeCurrentList(playerHeroList);
                else if (minionButton.isActive())
                    changeCurrentList(playerMinionList);
                else if (itemButton.isActive())
                    changeCurrentList(playerItemList);
                else
                    changeCurrentList(playerSpellList);
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

    private void getAllCards() {
        synchronized (allHeroList) {
            for (int i = 0; i < Card.getCards().getAllHeroes().size(); ++i) {
                Hero temp = Card.getCards().getAllHeroes().get(i);
                allHeroList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));
            }
        }

        synchronized (allMinionList) {
            for (int i = 0; i < Card.getCards().getAllMinions().size(); ++i) {
                Minion temp = Card.getCards().getAllMinions().get(i);
                allMinionList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));
            }
        }

        synchronized (allSpellList) {
            for (int i = 0; i < Card.getCards().getSellableItems().size(); ++i) {
                Spell temp = Card.getCards().getAllSpells().get(i);
                allSpellList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getGifPath()));
            }
        }

        synchronized (allItemList) {
            for (int i = 0; i < Card.getCards().getAllItems().size(); ++i) {
                Item temp = Card.getCards().getAllItems().get(i);
                allItemList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getGifPath()));
            }
        }
    }

    private void getPlayerCards() {
        playerHeroList = new CardListTexture(3, 2, 70, 140);
        for (Hero temp: Account.getCurrentAccount().getCollection().getAllHeroes()) {
            playerHeroList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));
        }

        playerMinionList = new CardListTexture(3, 2, 70, 140);
        for (Minion temp: Account.getCurrentAccount().getCollection().getAllMinions()) {
            playerMinionList.addCardTexture(new CardTexture( temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));
        }

        playerSpellList = new CardListTexture(3, 2, 70, 140);
        for (Spell temp: Account.getCurrentAccount().getCollection().getAllSpells()) {
            playerSpellList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(),temp.getGifPath()));
        }

        playerItemList = new CardListTexture(3, 2, 70, 140);
        for (Item temp: Account.getCurrentAccount().getCollection().getSellableItems()) {
            playerItemList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getGifPath()));
        }
    }

    private void updateButtonOnTouch() {
        backButton.setActive(backButton.contains(mousePos));
        doneButton.setActive(doneButton.contains(mousePos));
    }

    private void refreshAllLists() {
        heroButton.setActive(true);
        minionButton.setActive(false);
        spellButton.setActive(false);
        itemButton.setActive(false);
        if (buyButton.isActive()) {
            changeCurrentList(allHeroList);
            selectedCard = "";
        }
        if (sellButton.isActive()) {
            changeCurrentList(playerHeroList);
            selectedCard = "";
        }
    }

    private void drawButtons(SpriteBatch batch) {
        heroButton.draw(batch);
        minionButton.draw(batch);
        spellButton.draw(batch);
        itemButton.draw(batch);
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
        BitmapFont font = AssetHandler.getData().get("fonts/Arial 24.fnt");
        font.setColor(Main.toColor(new Color(0xFFFDFD)));
        heroButton = new Button("button/shop left.png", "button/shop left active.png", 100, 830, "Hero", font);
        minionButton = new Button("button/shop middle.png", "button/shop middle active.png", 300, 830, "Minion", font);
        spellButton = new Button("button/shop middle.png", "button/shop middle active.png", 500, 830, "Spell", font);
        itemButton = new Button("button/shop right.png", "button/shop right active.png", 700, 830, "Item", font);
        heroButton.setActive(true);
        font = AssetHandler.getData().get("fonts/Arial 36.fnt");
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

    private void changeCurrentList(CardListTexture temp) {
        currentList.deActiveAllCards();
        currentList = temp;
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
}
