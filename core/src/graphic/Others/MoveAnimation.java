package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;
import graphic.main.Gif;

public class MoveAnimation {

    private Texture texture;
    private Gif gif;
    private boolean isTexture;
    private boolean loop;
    private Vector2 startLoc;
    private Vector2 endLoc;
    private Vector2 currentLoc;
    private MoveType moveType;
    private float speed;

    public MoveAnimation(String path, float xStart, float yStart, float xEnd, float yEnd, MoveType moveType, boolean loop) {
        if (path.split("\\.")[1].equals("gif")) {
            isTexture = false;
            this.gif = new Gif(path);
        }
        else {
            this.texture = AssetHandler.getData().get(path);
            isTexture = true;
        }
        this.startLoc = new Vector2(xStart, yStart);
        this.currentLoc = new Vector2(startLoc);
        this.endLoc = new Vector2(xEnd, yEnd);
        this.moveType = moveType;
        this.loop = loop;
    }

    public MoveAnimation(String path, Vector2 startLoc, Vector2 endLoc, MoveType moveType, boolean loop) {
        if (path.split("\\.")[1].equals("gif")) {
            isTexture = false;
            this.gif = new Gif(path);
        }
        else {
            this.texture = AssetHandler.getData().get(path);
            isTexture = true;
        }
        this.startLoc = startLoc;
        this.currentLoc = new Vector2(startLoc);
        this.endLoc = endLoc;
        this.moveType = moveType;
        this.loop = loop;
    }

    public MoveAnimation(Texture texture, float xStart, float yStart, float xEnd, float yEnd, MoveType moveType, boolean loop) {
        this.texture = texture;
        this.startLoc = new Vector2(xStart, yStart);
        this.currentLoc = new Vector2(startLoc);
        this.endLoc = new Vector2(xEnd, yEnd);
        this.moveType = moveType;
        isTexture = true;
        this.loop = loop;
    }

    public MoveAnimation(Texture texture, Vector2 startLoc, Vector2 endLoc, MoveType moveType, boolean loop) {
        this.texture = texture;
        this.startLoc = startLoc;
        this.currentLoc = new Vector2(startLoc);
        this.endLoc = endLoc;
        this.moveType = moveType;
        isTexture = true;
        this.loop = loop;
    }

    public MoveAnimation(Gif gif, float xStart, float yStart, float xEnd, float yEnd, MoveType moveType, boolean loop) {
        this.gif = gif;
        this.startLoc = new Vector2(xStart, yStart);
        this.currentLoc = new Vector2(startLoc);
        this.endLoc = new Vector2(xEnd, yEnd);
        this.moveType = moveType;
        isTexture = false;
        this.loop = loop;
    }

    public MoveAnimation(Gif gif, Vector2 startLoc, Vector2 endLoc, MoveType moveType, boolean loop) {
        this.gif = gif;
        this.startLoc = startLoc;
        this.currentLoc = new Vector2(startLoc);
        this.endLoc = endLoc;
        this.moveType = moveType;
        isTexture = false;
        this.loop = loop;
    }

    public void draw(SpriteBatch batch, float speed, float width, float height) {
        this.speed = speed;
        updateLocation(speed);
        if (isTexture) {
            batch.begin();
            batch.draw(texture, currentLoc.x, currentLoc.y, width, height);
            batch.end();
        }
        else {
            gif.draw(batch, currentLoc.x, currentLoc.y, width, height);
        }
    }

    public void draw(SpriteBatch batch, float speed) {
        if (isTexture)
            draw(batch, speed, texture.getWidth(), texture.getHeight());
        else
            draw(batch, speed, gif.getWidth(), gif.getHeight());
    }

    public void draw(SpriteBatch batch) {
        draw(batch, speed);
    }

    public MoveAnimation setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    private void updateLocation(float speed) {
        Vector2 path = new Vector2(endLoc.x - startLoc.x, endLoc.y - startLoc.y);
        float xAdditional = speed * path.x / path.len();
        float yAdditional = speed * path.y / path.len();
        currentLoc.add(xAdditional, yAdditional);
    }

    public boolean isFinished() {
        return currentLoc.dst(endLoc) <= speed;
    }


}
