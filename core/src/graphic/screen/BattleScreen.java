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
import graphic.main.AssetHandler;
import graphic.main.Main;
import model.cards.Army;
import model.game.Cell;
import model.game.Game;
import model.game.GameType;
import model.game.Player;
import model.other.Account;
import graphic.main.Button;

import java.awt.*;
import java.util.HashMap;

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
    private Vector2 manaStart1;
    private Vector2 manaStart2;
    private Texture hero1Icon1;
    private Texture hero1Icon2;
    private Texture heroHpIcon;

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

    private ArmyAnimation hero1;
    private ArmyAnimation hero2;

    private HashMap<Cell, Vector2> cellCords;
    private Cell selectedCell;

    @Override
    public void create() {
        BattleMenuHandler battleMenuHandler = new BattleMenuHandler();
        battleMenuHandler.setPlayersSteps();
        game = new Game(Account.getCurrentAccount(), battleMenuHandler.getFirstLevelPlayer(), GameType.KILL_HERO, 0);
        Thread playGame = new Thread(new Runnable() {
            @Override
            public void run() {
                game.startMatch();
            }
        });
        playGame.start();
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
        heroHpIcon = AssetHandler.getData().get("battle/icon general hp.png");
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

        endTurnButton = new Button("button/yellow.png", "button/yellow glow.png", 1300, 100, "End Turn", "fonts/Arial 24.fnt");
        endGameButton = new Button("button/red.png", "button/red glow.png", 1340, 40, 170, 69, "End Game", "fonts/Arial 16.fnt");
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

        hero1 = new ArmyAnimation(player1.getHero().getGifPath());
        hero2 = new ArmyAnimation(player2.getHero().getGifPath());

        cellCords = new HashMap<Cell, Vector2>();
        setCellCords();
    }

    public void setCellCords() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                float x = tableCord1.x + col * (cellSizeX + cellDistance);
                float y = tableCord1.y - row * (cellSizeY + cellDistance);
                cellCords.put(game.getTable()[row][col], new Vector2(x, y));
            }
        }
    }

    @Override
    public void update() {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        game = Game.getCurrentGame();
        player1 = game.getFirstPlayer();
        player2 = game.getSecondPlayer();

        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        endTurnButton.setActive(endTurnButton.contains(mousePos));
        endGameButton.setActive(endGameButton.contains(mousePos));

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
                } else if(endGameButton.isActive()){
                    ScreenManager.setScreen(new MenuScreen());
                } else if(getMouseCell() != null){
                    if(getMouseCell().getInsideArmy() != null && game.getWhoIsHisTurn().isFriend(getMouseCell().getInsideArmy())) {
                        selectedCell = getMouseCell();
                        Army army = selectedCell.getInsideArmy();
                        setCommand("select " + army.getID().getValue());
                    } else {
                        Cell cell = getMouseCell();
                        Army target = cell.getInsideArmy();
                        if(target == null){
//                            setCommand("move to" + );
                            game.getWhoIsHisTurn().moveArmy(selectedCell, cell);
                            hero1.run(cellCords.get(cell).x, cellCords.get(cell).y);
                        } else {
                            game.getWhoIsHisTurn().attack(selectedCell, cell);
                        }
                        selectedCell = null;
                    }
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

    public Cell getMouseCell() {
        for(Cell[] row : game.getTable()){
            for(Cell cell : row){
                float x = cellCords.get(cell).x;
                float y = cellCords.get(cell).y;
                if(mousePos.x > x && mousePos.x < x + cellSizeX && mousePos.y > y && mousePos.y < y + cellSizeY){
                    return cell;
                }
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

        drawTable(batch);
        batch.end();
        endTurnButton.draw(batch);
        endGameButton.draw(batch);
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

    public void drawTable( SpriteBatch batch) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = game.getTable()[row][col];
                float x = cellCords.get(cell).x;
                float y = cellCords.get(cell).y;
                Army army = game.getTable()[row][col].getInsideArmy();
                if(selectedCell == cell) {
                    batch.setColor(Main.toColor(new Color(0x55E7EAF9, true)));
                    batch.draw(tileSelected, x, y, cellSizeX, cellSizeY);
                    batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                }
                if(army == null){
                    batch.setColor(Main.toColor(new Color(0x3DB0C0F9, true)));
                    batch.draw(tile, x, y, cellSizeX, cellSizeY);
                    batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);

                } else {
                    if(player1.isFriend(army)){
                        batch.setColor(Main.toColor(new Color(0x750000E3, true)));
                        batch.draw(tile, x, y, cellSizeX, cellSizeY);
                        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                        batch.end();
                        hero1.draw(batch, x - 20, y);
                        batch.begin();
                    } else {
                        batch.setColor(Main.toColor(new Color(0x83C80000, true)));
                        batch.draw(tile, x, y, cellSizeX, cellSizeY);
                        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                        batch.end();
                        hero2.draw(batch, x - 20, y);
                        batch.begin();
                    }
                }

            }
        }
    }

    public void drawSelectedCell(Cell cell, SpriteBatch batch, float x, float y) {
        if(selectedCell == cell) {
            batch.draw(tileSelected, x, y, cellSizeX, cellSizeY);
        }
    }

    public static String getCommand() {
        return command;
    }

    public static void setCommand(String string) {
       command = string;
    }
}
