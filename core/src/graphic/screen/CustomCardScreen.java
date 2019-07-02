package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import control.CvsWriter;
import graphic.Others.CardTexture;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import graphic.screen.MenuScreen;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;
import model.Buff.Buff;
import model.Buff.BuffType;
import model.cards.*;
import model.cards.CardType.*;
import view.CustomCardHandlerScreen;

import java.util.ArrayList;

import static model.cards.CardType.HERO;
import static model.cards.CardType.MINION;

public class CustomCardScreen extends Screen {

    private Texture backGround;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private Texture textField;
    private String text = "";
    private String input = "";
    private String state = "";
    private ArrayList<String> data = new ArrayList<String>();
    private CardType cardType;
    private BuffType buffType;
    private boolean nextStep = false;
    private CardListTexture cardList;
    private Button doneButton;
    private Vector2 mousePos = new Vector2();
    private String selectedCard;

    @Override
    public void create() {
        setCameraAndViewport();
        backGround = AssetHandler.getData().get("backGround/custom card bg.png");
        textField = AssetHandler.getData().get("slots/text field.png");
        font = AssetHandler.getData().get("fonts/Arial 36.fnt");
        glyphLayout = new GlyphLayout();
        playBackGroundMusic("music/login.mp3");
        doneButton = new Button("button/shop done.png", 1400, 450, "Done", "fonts/Arial 36.fnt");
    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        if(text.matches("")){
            text = "Enter Card Type";
        }
        if(state.contains("Finished")){
            writeCard();
        } else if(state.contains("Card Texture")){
            setCardTexture();
            doneButton.setActive(doneButton.contains(mousePos));
        } else if(nextStep){
            if(text.contains("Card Type")){
                text = "Enter Card Name";
            } else if(text.contains("Card Name")){
                text = "Enter Card Price";
            } else if(state.contains("Get Buff")){
                getBuff();
            } else{
                switch (cardType){
                    case HERO:
                    case MINION:
                        getMinionAndHero();
                        break;
                    case SPELL:
                        getSpell();
                        break;
                }
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
                } else if(keycode == Input.Keys.SPACE){
                    addedChar = ' ';
                }

                if (addedChar != '\0') {
                    input = input + addedChar;
                }


                if (keycode == Input.Keys.BACKSPACE) {
                    input = input.substring(0, input.length() - 1);
                }

                if (keycode == Input.Keys.RIGHT && cardList != null)
                    cardList.nextPage();
                else if (keycode == Input.Keys.LEFT && cardList != null)
                    cardList.previousPage();
                else if (keycode == Input.Keys.PAGE_DOWN)
                    setMusicVolume(false);
                else if (keycode == Input.Keys.PAGE_UP)
                    setMusicVolume(true);

                if (keycode == Input.Keys.ENTER) {
                    if(text.contains("Card Type")){
                        cardType = CardType.valueOf(input.toUpperCase());
//                        addNumber();
                    } else if(text.contains("Buff Name")){
                        addBuffName();
                    } else if(text.contains("Buff Type")){
                        buffType = BuffType.valueOf(input.toUpperCase());
                        data.add(input);
                    } else if(text.contains("Card Created")){
                        ScreenManager.setScreen(new MenuScreen());
                    }
                    else
                        data.add(input.toLowerCase());
                    input = "";
                    nextStep = true;
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
                if(state.contains("Card Texture") ){
                    if(cardList.contains(mousePos)){
                        selectedCard = cardList.getSelectedCardName(mousePos);
                    }
                    if(doneButton.isActive() && selectedCard != null){
                        addNumber();
                    }
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
    public void render(SpriteBatch batch){
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(backGround, 0, 0);

        glyphLayout.setText(font, text);
        font.draw(batch, text, (1600 - glyphLayout.width)/2, 730 );
        if(!state.contains("Finished") && !state.contains("Card Texture")) {
            batch.draw(textField, (1600 - textField.getWidth() * 2) / 2, 480, textField.getWidth() * 2, textField.getHeight());
            font.setColor(Color.BLACK);
            font.draw(batch, input, (1600 - textField.getWidth() * 2) / 2 + 80, 550);
            font.setColor(Color.WHITE);
        } else if(state.contains("Card Texture")){
            batch.end();
            drawList(batch);
            doneButton.draw(batch);
            batch.begin();
        }
        batch.end();
    }

    @Override
    public void dispose(){
        music.stop();
        music.dispose();
    }

    private void getMinionAndHero(){
        if(text.contains("Price") && cardType == MINION) {
            text = "Enter Mana";
        } else if(text.contains("Mana") && cardType == MINION || text.contains("Price") && cardType == HERO){
            text = "Enter HP";
        } else if(text.contains("HP")){
            text = "Enter AP";
        } else if(text.contains("AP")){
            text = "Enter Attack Type";
        } else if(text.contains("Attack Type")){
            text = "Enter Range";
        } else if(text.contains("Range")){
            if(cardType == MINION) {
                text = "Enter Special power activation";
                state = "Get Buff";
            }
            else
                text = "Enter MP";
        } else if(text.contains("MP")){
            text = "Enter CoolDown";
            state = "Get Buff";
        }
    }

    private void getSpell(){
        if(text.contains("Price")) {
            text = "Enter Mana";
        } else if(text.contains("Mana")) {
            text = "Enter Target";
            state = "Get Buff";
        }
    }

    private void getBuff() {
        if(text.contains("Buff Name")){
            text = "Enter Buff Type";
        } else if(text.contains("Buff Type")){
            if(buffType == BuffType.POWER || buffType == BuffType.WEAKNESS) {
                text = "Enter AP or HP";
            } else
                text = "Enter Buff Value";
        } else if(text.contains("AP or HP")){
            text = "Enter Buff Value";
        } else if(text.contains("Value")){
            text = "Enter Buff Delay";
        } else if(text.contains("Delay")){
            text = "Enter Buff Last";
        } else if(text.contains("Last")){
            text = "Enter Buff Friend or Enemy";
        } else if(text.contains("Friend or Enemy")){
            state = "Select Card Texture";
            text = "Select Card Texture";
        }
        else {
            text = "Enter Buff Name";
        }

    }

    private void addNumber() {
        for(Card card : Card.getCards().getAllCards()){
            if(card.getName().equals(selectedCard))
                data.add(0, Integer.toString(card.getNumber()));
        }
        state = "Finished";
    }

    private void addBuffName(){
        switch (cardType){
            case HERO:
                data.add(7, input);
                break;
            case MINION:
                data.add(8, input);
                break;
            case SPELL:
                data.add(input);
                break;
        }
    }

    private void writeCard(){
        if(state.contains("Card Created")) return;
        switch (cardType){
            case HERO:
                CvsWriter.write("Heroes", data);
                Hero.createHero(data.toArray(new String[data.size()]));
                break;
            case MINION:
                CvsWriter.write("Minions", data);
                Minion.createMinion(data.toArray(new String[data.size()]));
                break;
            case SPELL:
                CvsWriter.write("Spells", data);
                Spell.createSpell(data.toArray(new String[data.size()]));
                break;
        }
        state = "Card Created";
        text = "Your Card Created Successfully. Press Enter to Continue";
    }

    public void setCardTexture() {
        if(cardList == null) {
            cardList = new CardListTexture(4, 2, 40, 0);
            switch (cardType) {
                case HERO:
                    for (Hero temp : Hero.getHeroes()) {
                        cardList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));
                        if(cardList.getCardTextures().size() >= 10)
                            return;
                    }
                    break;
                case MINION:
                    for (Minion temp : Minion.getMinions()) {
                        cardList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(), temp.getAp(), temp.getHp(), temp.getGifPath()));
                        if(cardList.getCardTextures().size() >= 40)
                            return;
                    }
                    break;
                case SPELL:
                    for (Spell temp : Spell.getSpells()){
                        cardList.addCardTexture(new CardTexture(temp.getName(), temp.getDescription(), temp.getPrice(),temp.getGifPath()));
                        if(cardList.getCardTextures().size() >= 20)
                            return;
                    }
                    break;
            }
            for(CardTexture cardTexture : cardList.getCardTextures()){
                System.out.println(cardTexture.getName());
            }
        }
    }

    public void drawList(SpriteBatch batch) {
        if(cardList != null) {
            cardList.draw(batch);
        }
    }
}
