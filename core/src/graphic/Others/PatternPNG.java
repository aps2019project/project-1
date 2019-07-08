package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PatternPNG {
    private Texture pattern;

    public PatternPNG(Texture pattern) {
        this.pattern = pattern;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        int xSize = pattern.getWidth();
        int ySize = pattern.getHeight();
        batch.begin();
        for (int j = (int) y; j < (y + height) + ySize; j += ySize) {
            for (int i = (int) x; i < (x + width) + xSize; i += xSize) {
                batch.draw(pattern, i, j, xSize, ySize);
            }
        }
        batch.end();
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height, boolean fixed) {
        if (!fixed) {
            this.draw(batch, x, y, width, height);
            return;
        }
        float xSize = width / ((int)(width / pattern.getWidth() + 1));
        float ySize = height / ((int)(height / pattern.getHeight() + 1));
        batch.begin();
        for (int j = (int) y; j <= y + height - ySize; j += ySize) {
            for (int i = (int) x; i < x + width; i += xSize) {
                batch.draw(pattern, i, j, xSize, ySize);
            }
        }
        batch.end();
    }

}