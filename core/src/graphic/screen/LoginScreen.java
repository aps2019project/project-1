package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;

import java.awt.*;

public class LoginScreen extends Screen {

    private Texture backGround;
    private String userName;
    private String password;
    private String confirmPassword;
    private boolean isLogin;
    private Button loginButton;
    private Button signUpButton;
    private Music music;
    private ShapeRenderer shapeRenderer;
    private Vector2 mousePos;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        music = AssetHandler.getData().get("music/login.mp3");
        music.setLooping(true);
        music.play();
        backGround = AssetHandler.getData().get("backGround/login_backGround.png");
        shapeRenderer = new ShapeRenderer();
        mousePos = new Vector2();

        userName = "";
        password = "";
        confirmPassword = "";


        Texture temp1 = AssetHandler.getData().get("button/login1.png");
        float x = (800 - (2 * temp1.getWidth())) / 2f;
        float y = Main.HEIGHT - (100 + temp1.getHeight());
        loginButton = new Button("button/login1.png", "button/login2.png", "sfx/click.mp3",x, y);

        x = temp1.getWidth() + x;
        signUpButton = new Button("button/signUp1.png", "button/signUp2.png", "sfx/click.mp3",x, y);
        signUpButton.setActive(true);

    }

    @Override
    public void update() {

        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {

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
                if (loginButton.contains(mousePos)) {
                    loginButton.setActive(true);
                    signUpButton.setActive(false);
                }
                else if (signUpButton.contains(mousePos)) {
                    loginButton.setActive(false);
                    signUpButton.setActive(true);
                }
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
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(0x24F3F6F1, true)));
        shapeRenderer.rect(0, 0, 800, 900);
        shapeRenderer.end();

        loginButton.draw(batch);
        signUpButton.draw(batch);


    }

    @Override
    public void dispose() {

        music.dispose();
    }
}
