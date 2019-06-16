package graphic.screen;

import com.badlogic.gdx.audio.Music;
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

    private Color cellColorDefault = new Color(0, 0, 50, 0.2f);
    private Color cellColorPlayer1 = new Color(0, 0, 200, 0.2f);
    private Color cellColorPlayer2 = new Color(200, 0, 0, 0.2f);

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
    }

    @Override
    public void update() {
        camera.update();
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


        batch.end();

    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public void drawMana(SpriteBatch batch) {
        for(int i = 0; i<player1.getMana(); i++){
            batch.draw(mana, (int)(manaStart1.x + i*mana.getWidth()), (int)manaStart1.y);
        }
        for(int i = 0; i<player2.getMana(); i++){
            batch.draw(mana, (int)(manaStart2.x - i*mana.getWidth()), (int)manaStart2.y);
        }
    }

    public void drawHeroIcon(SpriteBatch batch) {
        batch.draw(hero1Icon1, 70, 700);
        batch.draw(hero1Icon2, 1310, 700);
    }
}
