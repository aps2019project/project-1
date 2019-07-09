package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.reflect.TypeToken;
import connection.Client;
import connection.Message;
import graphic.Others.MessageSlot;
import graphic.Others.PatternPNG;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.other.SavingObject;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class GlobalInformationScreen extends Screen {

    private Texture backGround;
    private Texture forGround;
    private Button backButton;
    private Button chatButton;
    private Button scoreBoardButton;
    private ArrayList<SavingObject> allAccounts;
    private HashSet<String> onlineAccounts;
    private Vector2 mousePos;
    private Texture offlineSlot;
    private Texture onlineSlot;
    private BitmapFont accountFont;
    private BitmapFont chatTitleFont;
    private GlyphLayout glyphLayout;
    private ShapeRenderer shapeRenderer;
    private static final float X = 575;
    private static final float Y = 150;
    private float yStart = 210;
    private String message;
    private PatternPNG wildFlower;
    private PatternPNG chatSlot;
    private PatternPNG chatBackground;
    private BitmapFont messageFont;
    private ArrayList<Sprite> emojies;
    private int emojiIndex;
    private MessageSlot messageSlot;


    @Override
    public void create() {
        setCameraAndViewport();
        mousePos = new Vector2();

        backGround = AssetHandler.getData().get("backGround/global background.png");
        forGround = AssetHandler.getData().get("backGround/global forground.png");
        onlineSlot = AssetHandler.getData().get("slots/online account.png");
        offlineSlot = AssetHandler.getData().get("slots/offline account.png");

        accountFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);
        chatTitleFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 36.fnt", BitmapFont.class).getRegions(), true);
        messageFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getRegions(), true);
        messageFont.setColor(Main.toColor(new Color(0x041E52)));

        backButton = new Button("button/back.png", 0, Main.HEIGHT - 50, 50, 50);
        chatButton = new Button("button/yellow.png", "button/green.png", 10, 460, 200, 70,"Chat", "fonts/Arial 24.fnt");
        scoreBoardButton = new Button("button/yellow.png", "button/green.png", 10, 370, 200, 70, "Score Board", "fonts/Arial 24.fnt");
        scoreBoardButton.setActive(true);
        messageSlot = new MessageSlot(new ArrayList<Message>(), yStart);
        glyphLayout = new GlyphLayout();
        allAccounts = new ArrayList<SavingObject>();
        onlineAccounts = new HashSet<String>();
        shapeRenderer = new ShapeRenderer();
        message = "";
        wildFlower = new PatternPNG(AssetHandler.getData().get("pattern/wild-flowers.png", Texture.class));
        chatSlot = new PatternPNG(AssetHandler.getData().get("pattern/chatSlot.png", Texture.class));
        chatBackground = new PatternPNG(AssetHandler.getData().get("pattern/diagmond.png", Texture.class));

        emojies = new ArrayList<Sprite>();
        for (int i = 1; i < 35; ++i) {
            emojies.add(AssetHandler.getData().get("chat/emoji.txt", TextureAtlas.class).createSprite(String.valueOf(i)));
        }
        emojiIndex = 0;

        playBackGroundMusic("music/login.mp3");
    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        if (scoreBoardButton.isActive()) {
            allAccounts.clear();
            Client.sendCommand("get accounts");
            Type type = new TypeToken<ArrayList<SavingObject>>() {
            }.getType();
            allAccounts.addAll(Client.getData(type, ArrayList.class));

            onlineAccounts.clear();
            Client.sendCommand("get online users");
            type = new TypeToken<HashSet<String>>() {
            }.getType();
            onlineAccounts.addAll(Client.getData(type, HashSet.class));
        }
        if (chatButton.isActive()) {
            Client.sendCommand("get chat");
            Type type = new TypeToken<ArrayList<Message>>() {
            }.getType();
            ArrayList<Message> messages = Client.getData(type, ArrayList.class);
            messageSlot = new MessageSlot(messages, yStart);
        }
        backButton.setActive(backButton.contains(mousePos));

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.PAGE_UP)
                    setMusicVolume(true);
                if (keycode == Input.Keys.PAGE_DOWN)
                    setMusicVolume(false);
                if (keycode == Input.Keys.LEFT) {
                    if (emojiIndex < 1)
                        emojiIndex = 33;
                    else
                        emojiIndex--;
                }
                if (keycode == Input.Keys.RIGHT)
                    emojiIndex = (emojiIndex + 1) % 34;
                if (keycode <= Input.Keys.Z && keycode >= Input.Keys.A)
                    message += (char)(keycode - Input.Keys.A + (int)'a');
                if (keycode <= Input.Keys.NUM_9 && keycode >= Input.Keys.NUM_0)
                    message += (char)(keycode - Input.Keys.NUM_0 + (int)'0');
                if (keycode == Input.Keys.SPACE)
                    message += " ";
                if (keycode == Input.Keys.BACKSPACE && !message.equals(""))
                    message = message.substring(0, message.length() - 2);
                if (keycode == Input.Keys.PERIOD)
                    message += '.';
                if (keycode == Input.Keys.ENTER && !message.equals("")) {
                    Client.sendCommand("new message");
                    Client.sendData(new Message(message));
                    message = "";
                    yStart = 210;
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
                if (backButton.isActive()) {
                    ScreenManager.setScreen(new MenuScreen());
                }
                else if (scoreBoardButton.contains(mousePos) || chatButton.contains(mousePos)) {
                    scoreBoardButton.setActive(scoreBoardButton.contains(mousePos));
                    chatButton.setActive(chatButton.contains(mousePos));
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
                yStart += 10 * amount;
                return false;
            }
        });

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        drawBackGround(batch);
        if (scoreBoardButton.isActive())
            drawAccounts(batch);
        else if (chatButton.isActive())
            drawChat(batch);
        drawButtons(batch);
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    private void drawButtons(SpriteBatch batch) {
        backButton.draw(batch);
        chatButton.draw(batch);
        scoreBoardButton.draw(batch);
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(forGround, Main.WIDTH - forGround.getWidth(), 0);
        batch.end();
    }

    private void drawAccounts(SpriteBatch batch) {
        batch.begin();
        float yStart = Main.HEIGHT - (Main.HEIGHT - allAccounts.size() * 80) / 2;
        for (SavingObject account: allAccounts) {
            if (onlineAccounts.contains(account.getUsername()))
                batch.draw(onlineSlot, (Main.WIDTH - onlineSlot.getWidth()) / 2, yStart - 70);
            else
                batch.draw(offlineSlot, (Main.WIDTH - onlineSlot.getWidth()) / 2, yStart - 70);
            glyphLayout.setText(accountFont, account.getUsername());
            accountFont.draw(batch, account.getUsername(), (Main.WIDTH - glyphLayout.width) / 2 + 15, yStart - (71 - glyphLayout.height) / 2);
            yStart -= 80;
        }
        batch.end();
    }

    private void drawChat(SpriteBatch batch) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(0xD8000000, true)));
        shapeRenderer.rect(X, Y, 450, 600);
        shapeRenderer.end();
        chatBackground.draw(batch, 0, 0, Main.WIDTH, Main.HEIGHT, true);


        messageSlot.draw(batch, shapeRenderer);
        drawTitleBar(batch);
        drawMessageSlot(batch);




    }

    private void drawTitleBar(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(0xA66600)));
        shapeRenderer.rect(X, Y + 530, 450, 70);
        shapeRenderer.end();
        wildFlower.draw(batch, X, Y + 530, 450, 70, true);

        batch.begin();
        glyphLayout.setText(chatTitleFont, "Global Chat");
        chatTitleFont.draw(batch, "Global Chat", X + (450 - glyphLayout.width) / 2, 600 + Y - (70 - glyphLayout.height) / 2);
        batch.end();
    }

    private void drawMessageSlot(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(0xFF000A63, true)));
        shapeRenderer.rect(X, Y, 450, 58);
        shapeRenderer.end();
        chatSlot.draw(batch, X, Y, 450, 58, true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(0xCED7CF)));
        shapeRenderer.rect(X + 10, Y + 10, 350, 58 - 20);
        shapeRenderer.end();


        batch.begin();
        String onBatch = "type message...";
        if (!message.equals(""))
            onBatch = message;
        glyphLayout.setText(messageFont, onBatch);
        messageFont.draw(batch, onBatch, X + 15, Y + 55 - (55 - glyphLayout.height) / 2);
        batch.end();
    }

}
