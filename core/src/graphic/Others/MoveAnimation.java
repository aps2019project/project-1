package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;

import java.util.Random;

public class MoveAnimation {

    private Texture texture;
    private Vector2 startLoc;
    private Vector2 endLoc;
    private Vector2 currentLoc;
    private MoveType moveType;
    private Random random;


    public MoveAnimation(Texture texture, Vector2 startLoc, Vector2 endLoc, MoveType moveType) {
        this.texture = texture;
        this.startLoc = startLoc;
        this.currentLoc = startLoc;
        this.endLoc = endLoc;
        this.moveType = moveType;
        random = new Random();
    }

    public MoveAnimation(String path, Vector2 startLoc, Vector2 endLoc, MoveType moveType) {
        this.texture = AssetHandler.getData().get(path);
        this.startLoc = startLoc;
        this.currentLoc = startLoc;
        this.endLoc = endLoc;
        this.moveType = moveType;
        random = new Random();
    }

    public MoveAnimation(String path, float xStart, float yStart, float xEnd, float yEnd, MoveType moveType) {
        this.texture = AssetHandler.getData().get(path);
        this.startLoc = new Vector2(xStart, yStart);
        this.currentLoc = startLoc;
        this.endLoc = new Vector2(xEnd, yEnd);
        this.moveType = moveType;
        random = new Random();
    }

    public MoveAnimation(Texture texture, float xStart, float yStart, float xEnd, float yEnd, MoveType moveType) {
        this.texture = texture;
        this.startLoc = new Vector2(xStart, yStart);
        this.currentLoc = startLoc;
        this.endLoc = new Vector2(xEnd, yEnd);
        this.moveType = moveType;
        random = new Random();
    }

    public void draw(SpriteBatch batch, int speed, float width, float height) {
        updateLocation(speed);
        batch.begin();
        batch.draw(texture, currentLoc.x, currentLoc.y, width, height);
        batch.end();
    }

    public void draw(SpriteBatch batch, int speed) {
        draw(batch, speed, texture.getWidth(), texture.getHeight());
    }

    private void updateLocation(int speed) {
        int xAdditional = speed * Integer.signum((int)endLoc.x - (int)startLoc.x);
        float x = currentLoc.x + xAdditional;
        float y = 0;
        if (this.moveType == MoveType.SIMPLE)
            y = currentLoc.y + xAdditional * (endLoc.y - startLoc.y) / (endLoc.x - startLoc.x);
        else if (this.moveType == MoveType.RANDOM)
            y = currentLoc.y + xAdditional * (endLoc.y - startLoc.y) / (endLoc.x - startLoc.x) + (float)((Math.random() * ((10) + 1)) - 5);
        if (x > endLoc.x + 5 && x < endLoc.x - 5)
            currentLoc = startLoc;
        else
            currentLoc.set(x, y);
    }


}
