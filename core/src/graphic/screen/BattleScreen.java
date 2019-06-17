package graphic.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;
import graphic.main.Main;
import model.game.Game;
import model.game.GameType;
import model.game.Player;
import model.other.Account;

import javax.xml.soap.Text;
import java.awt.*;

public class BattleScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGround;
    private Game game;
    private Player player1;
    private Player player2;
    private Texture mana;
    private Vector2 manaStart1;
    private Vector2 manaStart2;
    private Texture hero1Icon1;
    private Texture hero1Icon2;

    private Vector2 tableCord1;
    private Vector2 tableCord2;
    private Vector2 tableCord3;
    private Vector2 tableCord4;
    private float cellSizeX;
    private float cellSizeY;
    private float cellDistance;

    @Override
    public void create() {
        setCameraAndVeiwport();
        shapeRenderer = new ShapeRenderer();

        game = Game.getCurrentGame();
        player1 = game.getFirstPlayer();
        player2 = game.getSecondPlayer();

        backGround = AssetHandler.getData().get("backGround/battle_background.png");
        music = AssetHandler.getData().get("music/battle.mp3");
        mana = AssetHandler.getData().get("battle/mana.png");
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
        manaStart1 = new Vector2(270, 730);
        manaStart2 = new Vector2(1330 - mana.getWidth(), 730);
        hero1Icon1 = AssetHandler.getData().get(player1.getHero().getIconId());
        hero1Icon2 = AssetHandler.getData().get(player2.getHero().getIconId());

        tableCord1 = new Vector2(330, 525);
        tableCord2 = new Vector2(1270, 525);
        tableCord3 = new Vector2(330, 125);
        tableCord4 = new Vector2(1270, 125);

        cellDistance = 10;

        cellSizeX = (tableCord2.x - tableCord1.x - 8*cellDistance) / 9;
        cellSizeY = (tableCord1.y - tableCord3.y - 4*cellDistance) / 5;
    }

    @Override
    public void update() {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        game = Game.getCurrentGame();
        player1 = game.getFirstPlayer();
        player2 = game.getSecondPlayer();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0);
        drawMana(batch);
        drawHeroIcon(batch);
        drawTable(shapeRenderer);

        batch.end();

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
        batch.draw(hero1Icon1, 70, 700);
        batch.draw(hero1Icon2, 1310, 700);
    }

    public void drawTable(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                float x = tableCord1.x + col * (cellSizeX + cellDistance);
                float y = tableCord1.y - row * (cellSizeY + cellDistance);
//                if(game.getTable()[row][col].getInsideArmy() == null){
                    shapeRenderer.setColor(0, 0, 50/255f, 0.2f);
                    shapeRenderer.rect(x, y, cellSizeX, cellSizeY);
//                }
            }
        }
        shapeRenderer.end();
    }
}
