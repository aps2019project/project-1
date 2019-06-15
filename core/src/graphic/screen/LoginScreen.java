package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.SlotType;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.other.Account;

import java.awt.*;

public class LoginScreen extends Screen {

    private Texture backGround;
    private Texture userNameSlot;
    private Texture passwordSlot;
    private Texture confirmPasswordSlot;
    private Texture emptySlot;
    private Texture checkIcon;
    private Texture crossIcon;
    private String userName;
    private String password;
    private String confirmPassword;
    private SlotType currentSlot;
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
        setCameraAndVeiwport();
        createBackGroundMusic();
        loadAssets();
        createNewObjects();
        createFonts();
        createButtons();
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
                    addedChar = String.valueOf(keycode - Input.Keys.NUM_0).charAt(0);
                }

                if (addedChar != '\0') {
                    switch (currentSlot) {
                        case USERNAME:
                            userName = userName + addedChar;
                            break;
                        case PASSWORD:
                            password = password + addedChar;
                            break;
                        case CONFIRM:
                            confirmPassword = confirmPassword + addedChar;
                            break;
                    }
                }


                if (keycode == Input.Keys.BACKSPACE) {
                    switch (currentSlot) {
                        case USERNAME:
                            if (!userName.equals(""))
                                userName = userName.substring(0, userName.length() - 1);
                            break;
                        case PASSWORD:
                            if (!password.equals(""))
                                password = password.substring(0, password.length() - 1);
                            break;
                        case CONFIRM:
                            if (!confirmPassword.equals(""))
                                confirmPassword = confirmPassword.substring(0, confirmPassword.length() - 1);                            break;
                    }
                }

                if (keycode == Input.Keys.TAB) {
                    if (currentSlot.equals(SlotType.USERNAME))
                        currentSlot = SlotType.PASSWORD;
                    else if (currentSlot.equals(SlotType.PASSWORD) && signUpButton.isActive())
                        currentSlot = SlotType.CONFIRM;
                    else
                        currentSlot = SlotType.USERNAME;

                }

                if (keycode == Input.Keys.ENTER) {
                    if (signUpButton.isActive() && Account.isUserNameAvailable(userName)) {
                        if (password.equals("") || confirmPassword.equals(""))
                            return false;
                        if (password.equals(confirmPassword)) {
                            Account.setCurrentAccount(new Account(userName, password));
                            ScreenManager.setScreen(new MenuScreen());
                        }
                    }
                    if (loginButton.isActive() && Account.doesAccountExist(userName)) {
                        if (Account.checkIfPasswordIsCorrect(userName,password)) {
                            Account.setCurrentAccount(Account.findAccount(userName));
                            ScreenManager.setScreen(new MenuScreen());
                        }

                    }
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
                    userName = "";
                    password = "";
                    confirmPassword = "";
                    currentSlot = SlotType.USERNAME;
                }
                else if (signUpButton.contains(mousePos)) {
                    loginButton.setActive(false);
                    signUpButton.setActive(true);
                    userName = "";
                    password = "";
                    confirmPassword = "";
                    currentSlot = SlotType.USERNAME;
                }

                if (closeButton.isActive()) {
                    ScreenManager.getScreen().dispose();
                    Gdx.app.exit();
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
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        drawBackGround(batch);
        drawButtons(batch);
        glyphLayout.setText(font, "lqj");
        if (signUpButton.isActive()) {
            drawSignUpSlots(batch);
        } else if (loginButton.isActive()) {
            drawLoginSlot(batch);
        }

    }
    @Override
    public void dispose() {
        music.dispose();
    }

    private void createButtons() {
        Texture temp1 = AssetHandler.getData().get("button/login1.png");
        float x = (800 - (2 * temp1.getWidth())) / 2f;
        float y = Main.HEIGHT - (100 + temp1.getHeight());
        loginButton = new Button("button/login1.png", "button/login2.png", "sfx/click.mp3",x, y);
        x = temp1.getWidth() + x;
        signUpButton = new Button("button/signUp1.png", "button/signUp2.png", "sfx/click.mp3",x, y);
        signUpButton.setActive(true);
        closeButton = new Button("button/button_close.png", "button/button_close.png", "sfx/click.mp3", Main.WIDTH - 80, Main.HEIGHT - 80);
    }

    private void createNewObjects() {
        mousePos = new Vector2();
        userName = "";
        password = "";
        confirmPassword = "";
        glyphLayout = new GlyphLayout();
        currentSlot = SlotType.USERNAME;
    }

    private void loadAssets() {
        backGround = AssetHandler.getData().get("backGround/login_backGround.png");
        emptySlot = AssetHandler.getData().get("slots/empty.png");
        userNameSlot = AssetHandler.getData().get("slots/userName.png");
        passwordSlot = AssetHandler.getData().get("slots/password.png");
        confirmPasswordSlot = AssetHandler.getData().get("slots/confirm.png");
        shapeRenderer = new ShapeRenderer();
    }

    private void createBackGroundMusic() {
        music = AssetHandler.getData().get("music/login.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    private void createFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/9.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Main.toColor(new Color(0xFFFFFF));
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(0xFFFEFEFD, true)));
        shapeRenderer.rect(0, 0, (800/1600f)* camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();
    }

    private void drawButtons(SpriteBatch batch) {
        loginButton.draw(batch);
        signUpButton.draw(batch);
        closeButton.draw(batch);
    }

    private void drawLoginSlot(SpriteBatch batch) {
        batch.begin();
        if (userName.equals("") && currentSlot != SlotType.USERNAME)
            batch.draw(userNameSlot, (800 - userNameSlot.getWidth()) / 2, 500);
        else
            batch.draw(emptySlot, (800 - emptySlot.getWidth()) / 2, 500);
        font.draw(batch, userName, 50 + (800 - emptySlot.getWidth()) / 2, 500 + glyphLayout.height + (emptySlot.getHeight() - glyphLayout.height) / 2);

        if (password.equals("") && currentSlot != SlotType.PASSWORD)
            batch.draw(passwordSlot, (800 - passwordSlot.getWidth()) / 2, 300);
        else
            batch.draw(emptySlot, (800 - emptySlot.getWidth()) / 2, 300);
        font.draw(batch, password, 50 + (800 - emptySlot.getWidth()) / 2, 450 + glyphLayout.height + (emptySlot.getHeight() - glyphLayout.height) / 2);
        batch.end();
    }

    private void drawSignUpSlots(SpriteBatch batch) {
        batch.begin();
        if (userName.equals("") && currentSlot != SlotType.USERNAME) {
            batch.draw(userNameSlot, (800 - emptySlot.getWidth()) / 2, 650);
        }
        else {
            batch.draw(emptySlot, (800 - emptySlot.getWidth()) / 2, 650);
        }
        font.draw(batch, userName, 50 +(800 - emptySlot.getWidth()) / 2, 650 + glyphLayout.height + (emptySlot.getHeight() - glyphLayout.height) / 2);

        if (password.equals("") && currentSlot != SlotType.PASSWORD)
            batch.draw(passwordSlot, (800 - emptySlot.getWidth()) / 2, 450);
        else
            batch.draw(emptySlot, (800 - emptySlot.getWidth()) / 2, 450);
        font.draw(batch, password, 50 + (800 - emptySlot.getWidth()) / 2, 450 + glyphLayout.height + (emptySlot.getHeight() - glyphLayout.height) / 2);

        if (confirmPassword.equals("") && currentSlot != SlotType.CONFIRM)
            batch.draw(confirmPasswordSlot, (800 - emptySlot.getWidth()) / 2, 250);
        else
            batch.draw(emptySlot, (800 - emptySlot.getWidth()) / 2, 250);
        font.draw(batch, confirmPassword, 50 +(800 - emptySlot.getWidth()) / 2, 250 + glyphLayout.height + (emptySlot.getHeight() - glyphLayout.height) / 2);
        batch.end();
    }
}
