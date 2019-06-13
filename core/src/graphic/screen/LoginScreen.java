package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
    private Texture loginBack;
    private Texture signUpBack;
    private String userName;
    private String password;
    private String confirmPassword;
    private int order = 1;
    private Button loginButton;
    private Button signUpButton;
    private Button closeButton;
    private Music music;
    private ShapeRenderer shapeRenderer;
    private Vector2 mousePos;
    private BitmapFont font;
    private GlyphLayout glyphLayout;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        music = AssetHandler.getData().get("music/login.mp3");
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
        backGround = AssetHandler.getData().get("backGround/login_backGround.png");
        loginBack = AssetHandler.getData().get("backGround/loginInfo.png");
        signUpBack = AssetHandler.getData().get("backGround/signUpInfo.png");
        shapeRenderer = new ShapeRenderer();
        mousePos = new Vector2();

        font = new BitmapFont();
        glyphLayout = new GlyphLayout();

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

        closeButton = new Button("button/button_close.png", "button/button_close.png", "sfx/click.mp3", Main.WIDTH - 60, Main.HEIGHT - 60);


    }

    @Override
    public void update() {

        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        closeButton.setActive(closeButton.contains(mousePos));

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                char addedChar = '\0';
                if (keycode >= Input.Keys.A && keycode <= Input.Keys.Z) {
                    addedChar = (char) (keycode - Input.Keys.A + 'a');
                } else if (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9) {
                    addedChar = (char) (keycode - Input.Keys.NUM_0 + '0');
                }

                if (addedChar != '\0') {
                    switch (order) {
                        case 1:
                            userName = userName + (char) (keycode - Input.Keys.A + 'a');
                            break;
                        case 2:
                            password = password + (char) (keycode - Input.Keys.A + 'a');
                            break;
                        case 3:
                            confirmPassword = confirmPassword + (char) (keycode - Input.Keys.A + 'a');
                            break;
                    }
                }


                if (keycode == Input.Keys.BACKSPACE) {
                    switch (order) {
                        case 1:
                            if (!userName.equals(""))
                                userName = userName.substring(0, userName.length() - 1);
                            break;
                        case 2:
                            if (!password.equals(""))
                                password = password.substring(0, password.length() - 1);
                            break;
                        case 3:
                            if (!confirmPassword.equals(""))
                                confirmPassword = confirmPassword.substring(0, confirmPassword.length() - 1);                            break;
                    }
                }
                if (keycode == Input.Keys.TAB) {
                    order++;
                    if (signUpButton.isActive() && order > 3)
                        order = 1;
                    if (loginButton.isActive() && order > 2)
                        order = 1;
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
                if (loginButton.contains(mousePos)) {
                    loginButton.setActive(true);
                    signUpButton.setActive(false);
                }
                else if (signUpButton.contains(mousePos)) {
                    loginButton.setActive(false);
                    signUpButton.setActive(true);
                }

                if (closeButton.isActive()) {
                    ScreenManager.getScreen().dispose();
                    System.exit(0);
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
        shapeRenderer.setColor(Main.toColor(new Color(0xFFFEFEFD, true)));
        shapeRenderer.rect(0, 0, 800, 900);
        shapeRenderer.end();

        loginButton.draw(batch);
        signUpButton.draw(batch);
        closeButton.draw(batch);

        font.setColor(0,0,0,1);
        if (signUpButton.isActive()) {
            batch.begin();
            batch.draw(signUpBack, (800 - signUpBack.getWidth()) / 2, (800 - signUpBack.getHeight()) / 2);
            font.draw(batch, userName, 270, 535);
            font.draw(batch, password, 270, 435);
            font.draw(batch, confirmPassword, 270, 335);
            batch.end();
        }
        else if (loginButton.isActive()) {
            batch.begin();
            batch.draw(loginBack, (800 - loginBack.getWidth()) / 2, (800 - loginBack.getHeight()) / 2);
            font.draw(batch, userName, 160, 570);
            font.draw(batch, password, 160, 455);
            batch.end();
        }


    }

    @Override
    public void dispose() {

        music.dispose();
    }
}
