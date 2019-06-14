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

    @Override
    public void create() {
        setCameraAndVeiwport();
        shapeRenderer = new ShapeRenderer();
        backGround = AssetHandler.getData().get("backGround/battle_background.png");
        music = AssetHandler.getData().get("music/battle.mp3");
        mana = AssetHandler.getData().get("battle/mana.png");
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
        manaStart1 = new Vector2(100, 100);
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
//        batch.draw(mana, 300,300);
        
//        for(int i = 0; i<player1.getMana(); i++){
//            batch.draw(mana, (int)(manaStart1.x + i*mana.getWidth()), (int)manaStart1.y);
//        }
        batch.draw(backGround, 0, 0);
        batch.end();




    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
