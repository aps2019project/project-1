
package graphic.screen.gameMenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.MoveAnimation;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import graphic.screen.Screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FirstCustomMenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic;
    private Button exitButton;
    private Vector2 mousePos;
    private ArrayList<MoveAnimation> lanternAnimation;
    private boolean stone1Active = false,stone2Active = false,stone3Active = false;
    private TextureAtlas storySelect;
    private Button[] generals;
    private int numberOFGenerals = 11;
    @Override
    public void create() {
        setCameraAndViewport();
        shapeRenderer = new ShapeRenderer();
        storySelect = AssetHandler.getData().get("button/storySelectScreen.atlas");
        generals = new Button[numberOFGenerals + 1];
        backGroundPic = AssetHandler.getData().get("backGround/customFirstMenu.png");



        String font = "fonts/Arial 36.fnt";
        createGeneral();
        exitButton = new Button("button/exit.png", Main.WIDTH - 200, Main.HEIGHT - 200);
        createBackGroundMusic();        mousePos = new Vector2();
        mousePos = new Vector2();

    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);



        exitButton.setActive(exitButton.contains(mousePos));
        updateGenerals();
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
                if (button != Input.Buttons.LEFT)
                    return false;

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

        drawBackGround(batch);
        exitButton.draw(batch);
        drawGenerals(batch);
    }


    @Override
    public void dispose() {
        music.dispose();
    }
    private void createBackGroundMusic() {
        music = AssetHandler.getData().get("music/login.mp3");
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
    }


    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGroundPic, 0, 0);
        batch.end();
    }
    private void createGeneral() {
        InputStream input = null;
        try {
            input = new FileInputStream("Card/Hero/generals/generalsPlacesInCustomMenu.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(input);
        for(int i = 0; i < numberOFGenerals; i++) {
            String line = scanner.nextLine();
            int number = Integer.parseInt(line);
            line = scanner.nextLine().substring(2);
            int x = Integer.parseInt(line);
            line = scanner.nextLine().substring(3);
            int y1 = 900 - Integer.parseInt(line);
            line = scanner.nextLine().substring(3);
            System.out.println(line);
            int height = 900 -y1 - Integer.parseInt(line);
            float scale = (float) (height/1850.0);
            int width = (int)(2560*scale);
            generals[number] = new Button("Card/Hero/generals/" + Integer.toString(number) + ".png", "Card/Hero/generals/" + Integer.toString(number) + ".png","sfx/playerChangeButton1.mp3", x, y1, width, height);
        }
    }
    private void drawGenerals(SpriteBatch batch) {
        for(int i =0;i < numberOFGenerals; i++) {
            generals[i + 1].draw(batch);

        }
    }
    private void updateGenerals() {
        for(int i =0;i < numberOFGenerals; i++) {
            generals[i+1].setActive(generals[i+1].contains(mousePos));


        }
    }
}

