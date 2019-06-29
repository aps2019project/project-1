package graphic.screen.gameMenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.main.AssetHandler;
import graphic.screen.Screen;
import model.cards.CardType;

import java.util.ArrayList;

public class CustomCardScreen extends Screen {

    private Texture backGround;
    private Music music;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private Texture textField;
    private String text = "";
    private String input = "";

    private ArrayList<String> data = new ArrayList<String>();
    private CardType cardType;
    private boolean nextStep = false;

    @Override
    public void create() {
        setCameraAndViewport();
        backGround = AssetHandler.getData().get("backGround/custom card bg.png");
        music = AssetHandler.getData().get("music/login.mp3");
        textField = AssetHandler.getData().get("slots/text field.png");
        font = AssetHandler.getData().get("fonts/Arial 36.fnt");

        glyphLayout = new GlyphLayout();

        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();


    }

    @Override
    public void update() {
        if(text.matches("")){
            text = "Enter Card Type";
        }
        if(nextStep){
            if(text.matches("Enter Card Type")){
                text = "Enter Card Name";
            }
            nextStep = false;
        }

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
                    input = input + addedChar;
                }


                if (keycode == Input.Keys.BACKSPACE) {
                    input = input.substring(0, input.length() - 1);
                }

                if (keycode == Input.Keys.ENTER) {
                    if(text.matches("Enter Card Type")){
                        cardType = CardType.valueOf(input.toUpperCase());
                        input = "";
                        nextStep = true;
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
    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(backGround, 0, 0);

        glyphLayout.setText(font, text);
        font.draw(batch, text, (1600 - glyphLayout.width)/2, 730 );
        batch.draw(textField, (1600 - textField.getWidth()*2)/2, 480, textField.getWidth()* 2, textField.getHeight());

        font.setColor(Color.BLACK);
        font.draw(batch, input, (1600 - textField.getWidth()*2)/2 +80, 550);
        font.setColor(Color.WHITE);
        batch.end();
    }

    @Override
    public void dispose(){

    }

}
