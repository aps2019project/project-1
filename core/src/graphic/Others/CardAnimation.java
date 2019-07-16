package graphic.Others;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.screen.BattleScreen;
import model.cards.Spell;

public abstract class CardAnimation {
    protected static float SPEED = 1 / 20f;
    protected float x = 0, y = 0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static void setSPEED(float speed) {
        SPEED = speed;
        for(CardAnimation cardAnimation : BattleScreen.getAnimations().values()){
            if(cardAnimation instanceof ArmyAnimation) {
                ArmyAnimation armyAnimation = (ArmyAnimation) cardAnimation;
                armyAnimation.getNormalGif().setSpeed(speed);
                armyAnimation.getRunGif().setSpeed(speed);
                armyAnimation.getAttackGif().setSpeed(speed);
                armyAnimation.getDeathGif().setSpeed(speed);
            }
            else{
                SpellAnimation spellAnimation = (SpellAnimation) cardAnimation;
                spellAnimation.getGif().setSpeed(speed);
            }
        }
    }

    public void updateLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(SpriteBatch batch);

    public abstract void draw(SpriteBatch batch, float x, float y);

    public abstract void draw(SpriteBatch batch, float x, float y, float width, float height);
}
