package graphic.Others;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import graphic.main.Gif;
import graphic.main.GifType;
import graphic.screen.BattleScreen;

import javax.xml.soap.Text;

import static graphic.main.GifType.*;

public class ArmyAnimation {

    private static float SPEED = 1 / 20f;

    private float x, y;
    private Gif currentGif;
    private Gif normalGif;
    private Gif attackGif;
    private Gif deathGif;
    private Gif runGif;
    private Sound attackSound;
    private Sound deathSound;
    private Sound runSound;
    private MoveAnimation runAnimation;
    private boolean flipped = false;

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
        flipped = true;
        normalGif.flip();
        runGif.flip();
        deathGif.flip();
        attackGif.flip();
    }

    public static void setSPEED(float speed) {
        SPEED = speed;
        for(ArmyAnimation armyAnimation : BattleScreen.getAnimations().values()){
            armyAnimation.normalGif.setSpeed(speed);
            armyAnimation.runGif.setSpeed(speed);
            armyAnimation.attackGif.setSpeed(speed);
            armyAnimation.deathGif.setSpeed(speed);
        }
    }

    public static int getMovingSpeed() {
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

    public boolean haveGif(Gif gif){
        if(attackGif == gif) return true;
        if(deathGif == gif) return true;
        return false;
    }
}
