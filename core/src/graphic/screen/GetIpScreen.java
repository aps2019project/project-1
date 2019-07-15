package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import connection.Client;

public class GetIpScreen extends Screen {
    private ShapeRenderer shapeRenderer;
    private String ip = "";
    private BitmapFont font;
    private GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void create() {
        setCameraAndViewport();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("fonts/Arial 36.fnt"));
        Pixmap pm = new Pixmap(Gdx.files.internal("mouse.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();
    }

    @Override
    public void update() {
        camera.update();
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACKSPACE && !ip.equals(""))
                    ip = ip.substring(0, ip.length() - 1);
                else if (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9)
                    ip += (keycode - Input.Keys.NUM_0);
                else if (keycode >= Input.Keys.NUMPAD_0 && keycode <= Input.Keys.NUMPAD_9)
                    ip += (keycode - Input.Keys.NUMPAD_0);
                else if (keycode == Input.Keys.PERIOD)
                    ip += '.';

                if (keycode == Input.Keys.ENTER) {
                    Client.connect(ip);
                }

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });

    }

    @Override
    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1 ,1);
        shapeRenderer.rect((1600 - 500) / 2f, (700 - 80) / 2f, 500, 80);
        shapeRenderer.end();
        batch.begin();
        glyphLayout.setText(font, "Enter IP:");
        font.setColor(1, 1 ,1 ,1);
        font.draw(batch, "Enter IP:",(1600 - glyphLayout.width) / 2, 500);
        glyphLayout.setText(font, ip);
        font.setColor(0, 0, 0, 1);
        font.draw(batch, ip,(1600 - glyphLayout.width) / 2, 700 - (700 - glyphLayout.height) / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
    }
}
