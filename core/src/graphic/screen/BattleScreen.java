package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import control.BattleMenuHandler;
import graphic.Others.ArmyAnimation;
import graphic.Others.BattlePopUp;
import graphic.Others.CardTexture;
import graphic.Others.PopUp;
import graphic.main.AssetHandler;
import graphic.main.Main;
import model.cards.Army;
import model.cards.Card;
import model.cards.CardType;
import model.cards.Minion;
import model.game.Cell;
import model.game.Game;
import model.game.GameType;
import model.game.Player;
import model.other.Account;
import graphic.main.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BattleScreen extends Screen {

    private static String command;

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGround;
    private Game game;
    private Player player1;
    private Player player2;
    private Texture mana;
    private Texture tile;
    private Texture tileSelected;
    private Texture tileHand;
    private Vector2 manaStart1;
    private Vector2 manaStart2;
    private Texture hero1Icon1;
    private Texture hero1Icon2;
    private Texture heroHpIcon;
    private Texture apIcon;
    private Texture hpIcon;

    private Vector2 tableCord1;
    private Vector2 tableCord2;
    private Vector2 tableCord3;
    private Vector2 tableCord4;
    private Vector2 mousePos;
    private float cellSizeX;
    private float cellSizeY;
    private float cellDistance;

    private Button endTurnButton;
    private Button endGameButton;
    private Button graveyardButton;

    private ArmyAnimation hero1;
    private ArmyAnimation hero2;

    private Cell selectedCell;
    private Army selectedArmy;

    private static HashMap<Army, ArmyAnimation> animations;

    private Vector2 handStartCord;

//    private ArrayList<Cell> handCells;

    private static HashMap<Cell, Card> handCards;

    private Cell selectedCellHand;

    private static ArrayList<BattlePopUp> popUps;

    private Texture graveyardBg;
    private Vector2 graveyardCord;
    private boolean showingGraveyard;

    @Override
    public void create() {
        animations = new HashMap<Army, ArmyAnimation>();
        BattleMenuHandler battleMenuHandler = new BattleMenuHandler();
        popUps = new ArrayList<BattlePopUp>();

        battleMenuHandler.setPlayersSteps();
        game = new Game(Account.getCurrentAccount(), battleMenuHandler.getFirstLevelPlayer(), GameType.KILL_HERO, 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                game.startMatch();
            }
        }).start();

        setCameraAndViewport();
        shapeRenderer = new ShapeRenderer();

        game = Game.getCurrentGame();
        player1 = game.getFirstPlayer();
        player2 = game.getSecondPlayer();

        backGround = AssetHandler.getData().get("backGround/battle_background.png");
        music = AssetHandler.getData().get("music/battle.mp3");
        mana = AssetHandler.getData().get("battle/mana.png");
        tile = AssetHandler.getData().get("battle/Tile.png");
        tileSelected = AssetHandler.getData().get("battle/tile action.png");
        tileHand = AssetHandler.getData().get("battle/tile hand.png");
        heroHpIcon = AssetHandler.getData().get("battle/icon general hp.png");
        apIcon = AssetHandler.getData().get("battle/ap icon.png");
        hpIcon = AssetHandler.getData().get("battle/hp icon.png");
        graveyardBg = AssetHandler.getData().get("battle/Graveyard bg.png");

        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

        endTurnButton = new Button("button/yellow.png", "button/yellow glow.png", 1300, 100, "End Turn", "fonts/Arial 24.fnt");
        endGameButton = new Button("button/red.png", "button/red glow.png", 1340, 40, 170, 69, "End Game", "fonts/Arial 16.fnt");
        graveyardButton = new Button("button/yellow.png", "button/yellow glow.png", 100, 50, 200, 80, "Graveyard", "fonts/Arial 24.fnt");

        manaStart1 = new Vector2(270, 730);
        manaStart2 = new Vector2(1330 - mana.getWidth(), 730);

        mousePos = new Vector2();

        hero1Icon1 = AssetHandler.getData().get(player1.getHero().getIconId());
        hero1Icon2 = AssetHandler.getData().get(player2.getHero().getIconId());

        tableCord1 = new Vector2(330, 525);
        tableCord2 = new Vector2(1270, 525);
        tableCord3 = new Vector2(330, 125);
        tableCord4 = new Vector2(1270, 125);

        cellDistance = 10;

        cellSizeX = (tableCord2.x - tableCord1.x - 8*cellDistance) / 9;
        cellSizeY = (tableCord1.y - tableCord3.y - 4*cellDistance) / 5;

        setCellCords();

        animations.put(player1.getHero(), new ArmyAnimation(player1.getHero().getGifPath()));
        animations.put(player2.getHero(), new ArmyAnimation(player2.getHero().getGifPath()));

        handStartCord = new Vector2(400, 20);

        setHandCells();

        graveyardCord = new Vector2(-graveyardBg.getWidth(), 180);
    }

    public void setAnimations() {
        game.getFirstPlayer().setHandAnimations(animations);
        game.getSecondPlayer().setHandAnimations(animations);
    }

    public void setCellCords() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                float x = tableCord1.x + col * (cellSizeX + cellDistance);
                float y = tableCord1.y - row * (cellSizeY + cellDistance);
                game.getTable()[row][col].setScreenX(x);
                game.getTable()[row][col].setScreenY(y);
            }
        }
    }

    public void setHandCells() {
        handCards = new HashMap<Cell, Card>();
        for(int i = 0; i<5; i++){
            handCards.put(new Cell((int)handStartCord.x + i*160, (int)handStartCord.y), null);
        }
    }

    public void updateHandCells() {
        ArrayList<Cell> handCells = new ArrayList<Cell>(handCards.keySet());
        for(int i =0; i<player1.getHand().getAllCards().size(); i++){
            handCards.put(handCells.get(i), player1.getHand().getAllCards().get(i));
        }
    }

    @Override
    public void update() {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        game = Game.getCurrentGame();
        player1 = game.getFirstPlayer();
        player2 = game.getSecondPlayer();

        updateHandCells();

        setAnimations();

        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        endTurnButton.setActive(endTurnButton.contains(mousePos));
        endGameButton.setActive(endGameButton.contains(mousePos));
        graveyardButton.setActive(graveyardButton.contains(mousePos));

        updateGraveyard();

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.A){
                    System.out.println(player1.getSelectedCardPlace());
                    hero1.attack();
                } else if(keycode == Input.Keys.D) {
                    hero1.death();
                } else if(keycode == Input.Keys.R) {
                    hero1.run(200, 200);
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
                if(endTurnButton.isActive()){
                    selectedCell = null;
                    setCommand("end turn");
                    synchronized (game){
                        game.notify();
                    }
                } else if(endGameButton.isActive()){
                    game.exitFromGame();
                    ScreenManager.setScreen(new MenuScreen());
                } else if(graveyardButton.isActive()){
                    showingGraveyard = !showingGraveyard;
                } else if(getMouseCell() != null){
                    if(getMouseCell().getInsideArmy() != null && game.getWhoIsHisTurn().isFriend(getMouseCell().getInsideArmy())) {
                        selectedCell = getMouseCell();
                        selectedCellHand = null;
                        selectedArmy = selectedCell.getInsideArmy();
                        setCommand("select " + selectedArmy.getID().getValue());
                        synchronized (game){
                            game.notify();
                        }
                    } else if(selectedArmy != null && getMouseCell().getInsideArmy() == null) {
                        Cell cell = getMouseCell();
                        if (!game.getWhoIsHisTurn().canMove(selectedCell, cell)){
                            setPopUp("Invalid Cell");
                            return false;
                        }
                        game.getWhoIsHisTurn().moveArmy(selectedCell, cell);
                        selectedCell = null;
                        selectedArmy = null;
                    } else if(selectedCellHand != null && getMouseCell().getInsideArmy() == null) {
                        Cell cell = getMouseCell();
                        if(game.getWhoIsHisTurn().moveFromHandToCell(handCards.get(selectedCellHand), cell));
                            handCards.put(selectedCellHand, null);
                        selectedCellHand = null;
                    } else if(selectedArmy != null && getMouseCell().getInsideArmy() != null) {
                        Cell cell = getMouseCell();
                        Army target = cell.getInsideArmy();
                        if(game.getWhoIsHisTurn().isInRange(selectedCell, cell)) {
                            animations.get(selectedArmy).attack();
                            if (game.getWhoIsHisTurn().isInRange(cell, selectedCell))
                                animations.get(target).attack();
                        }
                        game.getWhoIsHisTurn().attack(selectedCell, cell);
                        selectedCell = null;
                        selectedArmy = null;
                    }
                } else if(getMouseHandCell() != null){
                    selectedCellHand = getMouseHandCell();
                    selectedCell = null;
                    selectedArmy = null;
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
        });
    }

    public void updateGraveyard() {
        int speed = 10;
        if(showingGraveyard){
            if(graveyardCord.x < -10)
                graveyardCord.x += speed;
        } else{
            if(graveyardCord.x > -graveyardBg.getWidth())
                graveyardCord.x -= speed;
        }
    }

    public Cell getMouseCell() {
        for(Cell[] row : game.getTable()){
            for(Cell cell : row){
                float x = cell.getScreenX();
                float y = cell.getScreenY();
                if(mousePos.x > x && mousePos.x < x + cellSizeX && mousePos.y > y && mousePos.y < y + cellSizeY){
                    return cell;
                }
            }
        }
        return null;
    }

    public Cell getMouseHandCell() {
        for(Cell cell : handCards.keySet()) {
            float x = cell.getX();
            float y = cell.getY();
            if(mousePos.x > x && mousePos.x < x + 160 && mousePos.y > y && mousePos.y < y + 160){
                return cell;
            }
        }
        return null;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0);
        drawMana(batch);
        drawHeroIcon(batch);
        drawPlayersName(batch);
        drawHeroesHp(batch);

        batch.end();
        drawTable(batch);
        drawHand(batch);


        drawPopUps(batch);

        drawGraveYard(batch);
        batch.end();

        endTurnButton.draw(batch);
        endGameButton.draw(batch);
        graveyardButton.draw(batch);
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public void drawMana(SpriteBatch batch) {
        for (int i = 0; i < player1.getMana(); i++) {
            batch.draw(mana, (int) (manaStart1.x + i * mana.getWidth()), (int) manaStart1.y);
        }
        for (int i = 0; i < player2.getMana(); i++) {
            batch.draw(mana, (int) (manaStart2.x - i * mana.getWidth()), (int) manaStart2.y);
        }
    }

    public void drawHeroIcon(SpriteBatch batch) {
        if(game.getWhoIsHisTurn().getAccount().getUsername().equals(player1.getAccount().getUsername())){
            batch.draw(hero1Icon1, 70, 700);
            batch.setColor(Main.toColor(new Color(0xBE928F87, true)));
            batch.draw(hero1Icon2, 1310, 700);
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        } else {
            batch.draw(hero1Icon2, 1310, 700);
            batch.setColor(Main.toColor(new Color(0xBE928F87, true)));
            batch.draw(hero1Icon1, 70, 700);
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }
    }

    public void drawHeroesHp(SpriteBatch batch) {
        batch.draw(heroHpIcon, 142, 690);
        batch.draw(heroHpIcon, 1382 , 690);

        BitmapFont font = AssetHandler.getData().get("fonts/Arial 36.fnt");
        GlyphLayout glyphLayout1 = new GlyphLayout();
        GlyphLayout glyphLayout2 = new GlyphLayout();

        glyphLayout1.setText(font, Integer.toString(player1.getHero().getHp()));
        font.draw(batch,Integer.toString(player1.getHero().getHp()), 165, 750);

        glyphLayout2.setText(font, Integer.toString(player2.getHero().getHp()));
        font.draw(batch,Integer.toString(player2.getHero().getHp()), 1405, 750);
    }

    public void drawPlayersName(SpriteBatch batch){
        BitmapFont font = AssetHandler.getData().get("fonts/Arial 36.fnt");
        GlyphLayout glyphLayout1 = new GlyphLayout();
        GlyphLayout glyphLayout2 = new GlyphLayout();

        glyphLayout1.setText(font, player1.getAccount().getUsername());
        font.draw(batch, player1.getAccount().getUsername(), 290, 830);

        glyphLayout2.setText(font, player2.getAccount().getUsername());
        font.draw(batch, player2.getAccount().getUsername(), 1310 - glyphLayout2.width, 830);

    }

    public void drawGraveYard(SpriteBatch batch) {
        batch.draw(graveyardBg, graveyardCord.x, graveyardCord.y);
        ArrayList<Minion> minions = player1.getGraveYard().getAllMinions();
        CardListTexture graveyardList = new CardListTexture(4, 3, graveyardCord.x, graveyardCord.y - 400);
        for(Minion minion : minions){
            CardTexture cardTexture = new CardTexture(minion.getName(), minion.getDescription(), minion.getPrice(), minion.getAp(), minion.getHp(), minion.getGifPath());
            graveyardList.addCardTexture(cardTexture);
        }
        batch.end();
        graveyardList.draw(batch);
        batch.begin();
    }


    public void drawTable( SpriteBatch batch) {
        batch.begin();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = game.getTable()[row][col];
                float x = cell.getScreenX();
                float y = cell.getScreenY();
                Army army = game.getTable()[row][col].getInsideArmy();
                if(selectedCell == cell) {
                    batch.setColor(Main.toColor(new Color(0x55E7EAF9, true)));
                    batch.draw(tileSelected, x, y, cellSizeX, cellSizeY);
                    batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                }
                if(army == null){
                    if(selectedCellHand != null && player1.isAroundArmies(cell))
                        batch.setColor(Main.toColor(new Color(0xBEBFF7F9, true)));
                    else if(selectedArmy != null && selectedArmy.canMoveTo(cell))
                        batch.setColor(Main.toColor(new Color(0xBEBFF7F9, true)));
                    else
                        batch.setColor(Main.toColor(new Color(0x3DB0C0F9, true)));
                    batch.draw(tile, x, y, cellSizeX, cellSizeY);
                    batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);

                } else {
                    if(player1.isFriend(army)){
                        batch.setColor(Main.toColor(new Color(0x750000E3, true)));
                        batch.draw(tile, x, y, cellSizeX, cellSizeY);
                        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                        batch.end();
                        animations.get(army).draw(batch, x - 20, y);
                        batch.begin();
                    } else {
                        batch.setColor(Main.toColor(new Color(0x83C80000, true)));
                        batch.draw(tile, x, y, cellSizeX, cellSizeY);
                        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                        batch.end();
                        animations.get(army).draw(batch, x - 10, y, 160, 160);
                        batch.begin();
                    }

                    batch.draw(apIcon, animations.get(army).getX() + 15, animations.get(army).getY());
                    batch.draw(hpIcon, animations.get(army).getX() + 75, animations.get(army).getY());

                    BitmapFont font = AssetHandler.getData().get("fonts/Arial 24.fnt");
                    GlyphLayout ap = new GlyphLayout();
                    GlyphLayout hp = new GlyphLayout();

                    font.setColor(Main.toColor(new Color(0xFFDEA900, true)));
                    ap.setText(font, Integer.toString(army.getAp()));
//                    font.draw(batch,Integer.toString(army.getAp()), animations.get(army).getX() + 35 , animations.get(army).getY() + 25);
                    font.draw(batch,Integer.toString(army.getAp()), (animations.get(army).getX() +15+ apIcon.getWidth()/2) - ap.width/2, animations.get(army).getY() + 25);

                    font.setColor(Main.toColor(new Color(0xFFBD1900, true)));
                    hp.setText(font, Integer.toString(army.getHp()));
//                    font.draw(batch,Integer.toString(army.getHp()), animations.get(army).getX() + 95, animations.get(army).getY() + 25);
                    font.draw(batch,Integer.toString(army.getHp()), (animations.get(army).getX() +75+ hpIcon.getWidth()/2) - hp.width/2, animations.get(army).getY() + 25);

                    font.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));


                }

            }
        }
        batch.end();
    }

    public void drawHand(SpriteBatch batch) {
        for(Cell cell : handCards.keySet()){

            if(selectedCellHand == cell) {
                batch.setColor(Main.toColor(new Color(0xFFDCDCDC, true)));
            }
            else {
                batch.setColor(Main.toColor(new Color(0xFF232323, true)));
            }
            batch.draw(tileHand, cell.getX(), cell.getY(), 160, 160);
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
            if(handCards.get(cell) == null){
                continue;
            }
            if(handCards.get(cell).getType() == CardType.SPELL){
                continue;
            }
            batch.end();

            animations.get(handCards.get(cell)).draw(batch, cell.getX() - 15, cell.getY() + 10, 180, 180);
            batch.begin();

            batch.draw(mana, cell.getX()+70, cell.getY());

            BitmapFont font = AssetHandler.getData().get("fonts/Arial 16.fnt");
            font.setColor(Main.toColor(new Color(0xFF000000, true)));
            font.draw(batch,Integer.toString(handCards.get(cell).getNeededManaToPut()), cell.getX() +85, cell.getY() + 25);
            font.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));

        }
    }

    public static void drawPopUps(SpriteBatch batch) {
        Iterator<BattlePopUp> iterator = popUps.iterator();
        while (iterator.hasNext()) {
            BattlePopUp popUp = iterator.next();
            if(popUp.getTime() > 3) {
                iterator.remove();
                continue;
            }
            popUp.draw(batch);
        }
    }

    public static ArrayList<BattlePopUp> getPopUps() {
        return popUps;
    }

    public static String getCommand() {
        return command;
    }

    public static void setCommand(String string) {
       command = string;

    }

    public static HashMap<Army, ArmyAnimation> getAnimations() {
        return animations;
    }

    public static HashMap<Cell, Card> getHandCards() {
        return handCards;
    }

    public static void setPopUp(String text) {
        PopUp.getInstance().setText(text);
    }
}
