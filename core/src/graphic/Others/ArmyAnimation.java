package graphic.Others;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import graphic.main.Gif;
import graphic.main.GifType;
import graphic.screen.BattleScreen;

import static graphic.main.GifType.*;

public class ArmyAnimation extends CardAnimation{

    private Gif currentGif;
    private Gif normalGif;
    private Gif attackGif;
    private Gif deathGif;
    private Gif runGif;
    private MoveAnimation runAnimation;

    public ArmyAnimation(String dataPath) {
        Animation animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("attack"), Animation.PlayMode.LOOP);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        attackGif = new Gif(animation);
        attackGif.setType(ATTACK);
        animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("breathing"));
        normalGif = new Gif(animation);
        normalGif.setType(NORMAL);
        animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("death"));
        deathGif = new Gif(animation);
        deathGif.setType(DEATH);
        animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("run"), Animation.PlayMode.LOOP);
        runGif = new Gif(animation);
        currentGif = attackGif;
        runAnimation = null;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        if (runAnimation != null && !runAnimation.isFinished()) {
            runAnimation.draw(batch, getMovingSpeed(), width, height);
            return;
        }
        else if (runAnimation != null)
            runAnimation = null;

        if (currentGif != normalGif && currentGif.isFinished()) {
            currentGif = normalGif;
        }
        if (currentGif.isFinished())
            normalGif.setTime();
        currentGif.draw(batch, x, y, width, height);
    }



    public void draw(SpriteBatch batch, float x, float y) {
        this.x = x;
        this.y = y;
        draw(batch, x, y, 150, 150);
    }

    public void draw(SpriteBatch batch){
        draw(batch, x, y);
    }

    public void run( float xEnd, float yEnd) {
        runAnimation = new MoveAnimation(runGif, x, y, xEnd, yEnd, MoveType.SIMPLE, false);
        this.x = xEnd;
        this.y = yEnd;
    }

    public void death() {
        currentGif = deathGif;
        deathGif.setTime();
    }

    public void attack() {
        currentGif = attackGif;
        attackGif.setTime();
    }

    public float getX() {
        if(runAnimation != null){
            return runAnimation.getCurrentLoc().x;
        }
        return x;
    }

    public float getY() {
        if(runAnimation != null){
            return runAnimation.getCurrentLoc().y;
        }
        return y;
    }

    public void flip(){
        normalGif.flip();
        runGif.flip();
        deathGif.flip();
        attackGif.flip();
    }

    private static int getMovingSpeed() {
        if(SPEED == 1/20f)
            return 5;
        return 10;
    }

    public Gif getDeathGif() {
        deathGif.setX(x);
        deathGif.setY(y);
        return deathGif;
    }

    public Gif getAttackGif() {
        attackGif.setX(x);
        attackGif.setY(y);
        return attackGif;
    }

    public Gif getNormalGif() {
        normalGif.setX(x);
        normalGif.setY(y);
        return normalGif;
    }

    public Gif getRunGif() {
        runGif.setX(x);
        runGif.setY(y);
        return runGif;
    }

    public boolean haveGif(Gif gif){
        if(attackGif == gif) return true;
        return deathGif == gif;
    }
}
