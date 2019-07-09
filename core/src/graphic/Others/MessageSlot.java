package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import connection.Message;
import graphic.main.AssetHandler;
import graphic.main.Main;
import model.other.Account;

import java.awt.*;
import java.util.ArrayList;

public class MessageSlot {

    private static final int xStart = 575;
    private static BitmapFont usernameFont;
    private static BitmapFont messageFont;
    private static PatternPNG pattern;
    private static GlyphLayout glyphLayout;
    private static ArrayList<Texture> emojies;


    static {
        emojies = new ArrayList<Texture>();
        for (int i = 1; i < 35; ++i) {
            emojies.add(AssetHandler.getData().get("chat/" + i + ".png", Texture.class));
        }
        usernameFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getRegions(), true);
        usernameFont.setColor(Main.toColor(new Color(0xFF27EE)));
        messageFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);
        messageFont.setColor(0, 0, 0, 1);
        pattern = new PatternPNG(AssetHandler.getData().get("pattern/message.png", Texture.class));
        glyphLayout = new GlyphLayout();
    }
    private ArrayList<Message> messages = new ArrayList<Message>();
    private ArrayList<Rectangle> slots = new ArrayList<Rectangle>();

    public MessageSlot(ArrayList<Message> messages, float yStart) {
        int index = messages.size() - 1;
        while (yStart < 150) {
            index--;
            yStart += 80;
        }
        while (yStart < 670 && index > -1) {
            if (messages.get(index).getUserName().equals(Account.getCurrentAccount().getUsername()))
                slots.add(new Rectangle(xStart + 90, yStart, 350, 70));
            else
                slots.add(new Rectangle(xStart + 10, yStart, 350, 70));
            this.messages.add(messages.get(index));
            index--;
            yStart += 80;
        }
    }

    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        for (int i = 0; i < messages.size() && i < slots.size(); ++i) {
            Message message = messages.get(i);
            Rectangle slot = slots.get(i);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if (message.getUserName().equals(Account.getCurrentAccount().getUsername()))
                shapeRenderer.setColor(Main.toColor(new Color(0x28FF00)));
            else
                shapeRenderer.setColor(Main.toColor(new Color(0xFFF6F4)));
            if (message.getMessage().matches("\\.:emoji:\\. \\d+")) {
                if (message.getUserName().equals(Account.getCurrentAccount().getUsername()))
                    shapeRenderer.rect(slot.x + 190, slot.y, 160, slot.height);
                else
                    shapeRenderer.rect(slot.x, slot.y, 160, slot.height);
            }
            else
                shapeRenderer.rect(slot.x, slot.y, slot.width, slot.height);
            shapeRenderer.end();

            if (message.getMessage().matches("\\.:emoji:\\. \\d+")) {
                if (message.getUserName().equals(Account.getCurrentAccount().getUsername()))
                    pattern.draw(batch, slot.x + 190, slot.y, 160, slot.height, true);
                else
                    pattern.draw(batch, slot.x, slot.y, 160, slot.height, true);
            }
            else
                pattern.draw(batch, slot.x, slot.y, slot.width, slot.height, true);
            if (message.getMessage().matches("\\.:emoji:\\. \\d+")) {
                int index = Integer.parseInt(message.getMessage().split(" ")[1]);
                batch.begin();

                if (message.getUserName().equals(Account.getCurrentAccount().getUsername()))
                    usernameFont.draw(batch, message.getUserName(), slot.x + 190, slot.y + slot.height - 10);
                else
                    usernameFont.draw(batch, message.getUserName(), slot.x, slot.y + slot.height - 10);

                if (message.getUserName().equals(Account.getCurrentAccount().getUsername()))
                    batch.draw(emojies.get(index), slot.x + 190 + 80, slot.y + 2, 70, 70);
                else
                    batch.draw(emojies.get(index), slot.x + 80, slot.y + 2, 70, 70);
                batch.end();
                continue;
            }
            batch.begin();
            usernameFont.draw(batch, message.getUserName(), slot.x + 10, slot.y + slot.height - 10);
            glyphLayout.setText(messageFont, message.getMessage());
            messageFont.draw(batch, message.getMessage(), slot.x + 30, slot.y + glyphLayout.height + 15);
            batch.end();
        }
    }

}
