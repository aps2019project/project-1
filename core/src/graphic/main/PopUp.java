package graphic.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.Map;

public class PopUp {
    private static PopUp instance = new PopUp();

    private PopUp() {
    }

    public static PopUp getInstance() {
        return instance;
    }

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private GlyphLayout glyphLayout = new GlyphLayout();
    private String text = "";
    private BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/Arial 48B.fnt"));
    private float time = 6;

    public void setText(String text) {
        this.time = 0;
        this.text = text;
        glyphLayout.setText(font, text);
    }

    public void draw(SpriteBatch batch) {
        if (time > 5 || text.equals(""))
            return;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        font.setColor(1, 0.604f, 0, 1 - time / 5);
        font.draw(batch, text, (Main.WIDTH - glyphLayout.width) / 2, 850 - (50 - glyphLayout.height) / 2);
        batch.end();
        time += Gdx.graphics.getDeltaTime();
        if (time > 5) text = "";
    }

}
