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
import java.util.ArrayList;

public class TestScreen extends Screen {

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
    private CardListTexture allHeroList;
    private CardListTexture allMinionList;
    private CardListTexture allSpellList;
    private CardListTexture allItemList;
    private CardListTexture playerHeroList;
    private CardListTexture playerMinionList;
    private CardListTexture playerSpellList;
    private CardListTexture playerItemList;
    private CardListTexture currentList;
    private String selectedCard;

    private ArrayList<MoveAnimation> snowAnimation;


    @Override
    public void create() {
        setCameraAndViewport();
        backGround = AssetHandler.getData().get("backGround/shop1.png");
        middleGround = AssetHandler.getData().get("backGround/shop2.png");
        forGround = AssetHandler.getData().get("backGround/shop3.png");
        mousePos = new Vector2();
        playBackGroundMusic("music/shop.mp3");
        selectedCard = "";
        createSnowAnimation();
        createButtons();

        allHeroList = new CardListTexture(3, 2, 70, 140);
        for (int i = 0; i < Card.getCards().getAllHeroes().size(); ++i) {
            Hero temp = Card.getCards().getAllHeroes().get(i);
            allHeroList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getAp(), temp.getHp(), "Card/Hero/" + (i%11+1) +".atlas"));
        }

        allMinionList = new CardListTexture(3, 2, 70, 140);
        for (int i = 0; i < Card.getCards().getAllMinions().size(); ++i) {
            Minion temp = Card.getCards().getAllMinions().get(i);
            allMinionList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getAp(), temp.getHp(), "Card/Hero/" + 3 +".atlas"));
        }

        allSpellList = new CardListTexture(3, 2, 70, 140);
        for (int i = 0; i < Card.getCards().getSellableItems().size(); ++i) {
            Spell temp = Card.getCards().getAllSpells().get(i);
            allSpellList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), 5, 5, "Card/Hero/" + 7+".atlas"));
        }

        allItemList = new CardListTexture(3, 2, 70, 140);
        for (int i = 0; i < Card.getCards().getAllItems().size(); ++i) {
            Item temp = Card.getCards().getAllItems().get(i);
            allItemList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), 10, 10, "Card/Hero/" + (5) +".atlas"));
        }

        getPlayerCards();


        currentList = allHeroList;

        playBackGroundMusic("music/shop.mp3");

    }

    private void getPlayerCards() {
        playerHeroList = new CardListTexture(3, 2, 70, 140);
        for (Hero temp: Account.getCurrentAccount().getCollection().getAllHeroes()) {
            playerHeroList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getAp(), temp.getHp(), "Card/Hero/" + 7 + ".atlas"));
        }

        playerMinionList = new CardListTexture(3, 2, 70, 140);
        for (Minion temp: Account.getCurrentAccount().getCollection().getAllMinions()) {
            playerMinionList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getAp(), temp.getHp(), "Card/Hero/" + 9 + ".atlas"));
        }

        playerSpellList = new CardListTexture(3, 2, 70, 140);
        for (Spell temp: Account.getCurrentAccount().getCollection().getAllSpells()) {
            playerSpellList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), 5, 5, "Card/Hero/" + 2 + ".atlas"));
        }

        playerItemList = new CardListTexture(3, 2, 70, 140);
        for (Item temp: Account.getCurrentAccount().getCollection().getSellableItems()) {
            playerItemList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), 10, 10, "Card/Hero/" + 3 + ".atlas"));
        }
    }

    @Override
    public void update() {

        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        backButton.setActive(backButton.contains(mousePos));
        doneButton.setActive(doneButton.contains(mousePos));

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
                if (heroButton.contains(mousePos) || minionButton.contains(mousePos) || spellButton.contains(mousePos) || itemButton.contains(mousePos)) {
                    heroButton.setActive(heroButton.contains(mousePos));
                    minionButton.setActive(minionButton.contains(mousePos));
                    spellButton.setActive(spellButton.contains(mousePos));
                    itemButton.setActive(itemButton.contains(mousePos));
                    if (buyButton.isActive()) {
                        if (heroButton.isActive())
                            changeCurrentList(allHeroList);
                        else if (minionButton.isActive())
                            changeCurrentList(allMinionList);
                        else if (itemButton.isActive())
                            changeCurrentList(allItemList);
                        else
                            changeCurrentList(allSpellList);
                    }
                    if (sellButton.isActive()) {
                        if (heroButton.isActive())
                            changeCurrentList(playerHeroList);
                        else if (minionButton.isActive())
                            changeCurrentList(playerMinionList);
                        else if (itemButton.isActive())
                            changeCurrentList(playerItemList);
                        else
                            changeCurrentList(playerSpellList);
                    }
                    selectedCard = "";
                }
                else if (buyButton.contains(mousePos) || sellButton.contains(mousePos)) {
                    buyButton.setActive(buyButton.contains(mousePos));
                    sellButton.setActive(sellButton.contains(mousePos));
                    refreshAllLists();
                }

                if (currentList.contains(mousePos)) {
                    selectedCard = currentList.getSelectedCardName(mousePos);
                }


                if (doneButton.isActive()) {
                    if (selectedCard.equals(""))
                        return false;
                    if (sellButton.isActive()) {
                        Account.getCurrentAccount().sellCard(selectedCard);
                        getPlayerCards();
                        refreshAllLists();
                        updateDaricShow();

                    }
                    if (buyButton.isActive()) {
                        Account.getCurrentAccount().buyCard(selectedCard);
                        getPlayerCards();
                        refreshAllLists();
                        updateDaricShow();
                    }
                }

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
        });

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

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);

        drawBackGround(batch);

        heroButton.draw(batch);
        minionButton.draw(batch);
        spellButton.draw(batch);
        itemButton.draw(batch);
        sellButton.draw(batch);
        buyButton.draw(batch);
        backButton.draw(batch);
        doneButton.draw(batch);
        daricPicture.draw(batch);

        currentList.draw(batch);

        batch.begin();
        BitmapFont font =AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class);
        font.draw(batch, selectedCard, 700, 800);
        batch.end();


    }

    @Override
    public void dispose() {
        music.stop();
        music.dispose();

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
