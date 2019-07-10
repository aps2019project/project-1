package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.*;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.game.Deck;
import model.other.Account;
import model.other.DeckSavingObject;
import model.other.exeptions.collection.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CollectionScreen extends Screen {

    private Account account;
    private Texture backGroundPic;
    private Texture middleGroundPic;
    private Texture frontPic;
    private Texture textSlot;
    private ArrayList<MoveAnimation> waterFallAnimation;
    private ArrayList<Button> allDecksButtons;
    private Button backButton;
    private Button selectAsMainDeckButton;
    private Button createDeckButton;
    private Button addToDeckButton;
    private Button deleteCardFromDeckButton;
    private Button doneTypingButton;
    private Button importDeckButton;
    private Button exportDeckButton;
    private CardShowSlot cardList;
    private Vector2 mousePos;
    private String selectedCard;
    private String deckCard = "";
    private String text = "";
    private String filePath;
    private boolean isTyping = false;
    private Deck currentDeck;
    private DeckTexture deckTexture;
    private BitmapFont font;


    @Override
    public void create() {
        setCameraAndViewport();
        playBackGroundMusic("music/collection.mp3");
        backGroundPic = AssetHandler.getData().get("backGround/collection1.png");
        middleGroundPic = AssetHandler.getData().get("backGround/collection2.png");
        textSlot = AssetHandler.getData().get("slots/text field.png");
        frontPic = AssetHandler.getData().get("backGround/collection3.png");

        allDecksButtons = new ArrayList<Button>();

        account = Account.getCurrentAccount();
        font = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);
        font.setColor(Main.toColor(new Color(0x001042)));
        createDeckButtons();
        mousePos = new Vector2();

        backButton = new Button("button/back.png", 0, 850, 50, 50);
        addToDeckButton = new Button("button/green.png", "button/green glow.png", 800, 50, "Add Card", "fonts/Arial 20.fnt");
        deleteCardFromDeckButton = new Button("button/red.png", "button/red glow.png", 450, 700, "remove card", "fonts/Arial 20.fnt");
        createDeckButton = new Button("button/yellow.png", "button/yellow glow.png", 1300, 30, "Create Deck", "fonts/Arial 20.fnt");
        doneTypingButton = new Button("button/shop done.png", 711, 300, "Done");
        selectAsMainDeckButton = new Button("button/green.png", "button/green glow.png", 450, 760, "Select Main", "fonts/Arial 20.fnt");
        importDeckButton = new Button("button/yellow.png", "button/yellow glow.png", 1100, 30, "Import Deck", "fonts/Arial 20.fnt");
        exportDeckButton = new Button("button/green.png", "button/green glow.png", 450, 820, "Export Deck", "fonts/Arial 20.fnt");

        createWaterFallAnimation();

        cardList = new CardShowSlot(Account.getCurrentAccount().getCollection(), 670, 140, 3, 2);
        selectedCard = "";
    }

    private void createDeckButtons() {
        float buttonHeight = AssetHandler.getData().get("button/deckSlot.png", Texture.class).getHeight();
        allDecksButtons.clear();
        for (int i = 0; i < account.getAllDecks().size(); ++i)

            if (account.getMainDeck() == null)
                allDecksButtons.add(new Button("button/deckSlot.png", "button/deckSlotGlow.png", 0, Main.HEIGHT - 100 - (i + 1) * buttonHeight));
            else if (account.getAllDecks().get(i).getName().equals(account.getMainDeck().getName()))
                allDecksButtons.add(new Button("button/deckSlot.png", "button/deckSlotGlow.png", 0, Main.HEIGHT - 100 - (i + 1) * buttonHeight, "Main", "fonts/Arial 20.fnt"));
            else
                allDecksButtons.add(new Button("button/deckSlot.png", "button/deckSlotGlow.png", 0, Main.HEIGHT - 100 - (i + 1) * buttonHeight));
    }

    @Override
    public void update() {
        camera.update();
        mousePos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        if (isTyping) {
            doneTypingButton.setActive(doneTypingButton.contains(mousePos));
        } else {
            if (!selectedCard.equals("") && currentDeck != null)
                addToDeckButton.setActive(addToDeckButton.contains(mousePos));
            if (currentDeck != null && !deckCard.equals(""))
                deleteCardFromDeckButton.setActive(deleteCardFromDeckButton.contains(mousePos));
            if (currentDeck != null && (account.getMainDeck() == null || (account.getMainDeck() == null && !currentDeck.getName().equals(account.getMainDeck().getName()))))
                selectAsMainDeckButton.setActive(selectAsMainDeckButton.contains(mousePos));
            if (currentDeck != null)
                exportDeckButton.setActive(exportDeckButton.contains(mousePos));
            backButton.setActive(backButton.contains(mousePos));
            createDeckButton.setActive(createDeckButton.contains(mousePos));
            importDeckButton.setActive(importDeckButton.contains(mousePos));
        }

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.PAGE_DOWN)
                    setMusicVolume(false);
                if (keycode == Input.Keys.PAGE_UP)
                    setMusicVolume(true);
                if (!isTyping) return false;

                else if (keycode == Input.Keys.BACKSPACE && text.length() > 0)
                    text = text.substring(0, text.length() - 1);

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (Main.isCharacterOK(character))
                text += character;
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                if (isTyping) {
                    if (doneTypingButton.isActive() && text.length() >= 3) {
                        Deck deck = new Deck(text);
                        try {
                            Account.getCurrentAccount().addDeck(deck);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        createDeckButtons();
                        isTyping = false;
                        text = "";
                        doneTypingButton.setActive(false);
                    }
                }

                boolean isDeckSelected = false;
                for (Button deckButton: allDecksButtons) {
                    if (deckButton.contains(mousePos)) {
                        currentDeck = account.getAllDecks().get(allDecksButtons.indexOf(deckButton));
                        deckTexture = new DeckTexture(currentDeck, 200, 870);
                        deckCard = "";
                        isDeckSelected = true;
                    }
                }

                if (isDeckSelected)
                    for (Button deckButton: allDecksButtons) {
                        deckButton.setActive(deckButton.contains(mousePos));
                    }

                if (deckTexture != null) {
                    if (deckTexture.contains(mousePos)) {
                        deckCard = deckTexture.getCardName(mousePos);
                        deckTexture.setActive(mousePos);
                    }
                }

                if (cardList.contains(mousePos)) {
                    cardList.update(mousePos);
                    selectedCard = cardList.getSelectedCard(mousePos);
                }

                if (backButton.isActive()) {
                    Account.updateAccount();
                    ScreenManager.setScreen(new MenuScreen());
                }

                if (addToDeckButton.isActive() && !selectedCard.equals("") && currentDeck != null) {
                    try {
                        Account.getCurrentAccount().addCardToDeck(selectedCard, currentDeck);
                    } catch (DeckIsFullException e) {
                        PopUp.getInstance().setText("Deck is Full. Delete some cards first.");
                    } catch (DeckAlreadyHaveHeroException e) {
                        PopUp.getInstance().setText("Deck Already Have a Hero.");
                    } catch (DeckAlreadyHaveItemException e) {
                        PopUp.getInstance().setText("Deck Already Have an Item");
                    } catch (CollectionException e) {
                        PopUp.getInstance().setText("Something went wrong. Try again later...");
                    }
                    deckTexture = new DeckTexture(currentDeck, 200, 870);
                }

                if (deleteCardFromDeckButton.isActive() && !deckCard.equals("")) {
                    try {
                        Account.getCurrentAccount().removeCardFromDeck(deckCard, currentDeck);
                    } catch (CollectionException e) {
                        PopUp.getInstance().setText("Can't delete this card");
                    }
                    deckTexture = new DeckTexture(currentDeck, 200, 870);
                    deckCard = "";
                    deleteCardFromDeckButton.setActive(false);
                }

                if (createDeckButton.isActive()) {
                    isTyping = true;
                    createDeckButton.setActive(false);
                }

                if (selectAsMainDeckButton.isActive() && currentDeck != null) {
                    Account.getCurrentAccount().setMainDeck(currentDeck);
                    createDeckButtons();
                    selectAsMainDeckButton.setActive(false);
                }

                if (importDeckButton.isActive()) {
                    importDeckFromFile();
                }

                if (exportDeckButton.isActive() && currentDeck != null) {
                    exportDeck();
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

    private void exportDeck() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getPath();
                }
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DeckSavingObject.saveDeck(currentDeck, filePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        exportDeckButton.setActive(false);
    }

    private void importDeckFromFile() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("Deck Files", "deck");
                fileChooser.setFileFilter(filter);
                fileChooser.addChoosableFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getPath();
                }
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Account.getCurrentAccount().addDeck(DeckSavingObject.readDeck(filePath));
                        } catch (FileNotFoundException e) {
                            PopUp.getInstance().setText(e.getMessage());
                        } catch (DontHaveCardException e) {
                            PopUp.getInstance().setText("You Don't Have All Cards in this deck");
                        }
                        createDeckButtons();
                    }
                });
            }
        });
        importDeckButton.setActive(false);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        if (isTyping)
            batch.setColor(Main.toColor(new Color(0x6EFFFFFF, true)));
        else
            batch.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));
        drawBackGround(batch);
        for (Button deckButton: allDecksButtons)
            deckButton.draw(batch);
        cardList.draw(batch);
        backButton.draw(batch);
        if (deckTexture != null)
            deckTexture.draw(batch);
        if (!selectedCard.equals("") && currentDeck != null)
            addToDeckButton.draw(batch);
        if (!deckCard.equals(""))
            deleteCardFromDeckButton.draw(batch);
        createDeckButton.draw(batch);
        if (currentDeck != null && (account.getMainDeck() == null || ( account.getMainDeck() != null && !currentDeck.getName().equals(account.getMainDeck().getName()))))
            selectAsMainDeckButton.draw(batch);
        importDeckButton.draw(batch);
        if (currentDeck != null)
            exportDeckButton.draw(batch);

        if (isTyping) {
            batch.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));
            batch.begin();
            float x = (Main.WIDTH - textSlot.getWidth()) / 2;
            float y = (Main.HEIGHT - textSlot.getHeight()) / 2;
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, text);
            batch.draw(textSlot, x, y);
            font.draw(batch, text, (Main.WIDTH - glyphLayout.width) / 2, Main.HEIGHT - (Main.HEIGHT - glyphLayout.height) / 2);
            batch.end();
            doneTypingButton.draw(batch);

        }
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGroundPic, 0 ,0);
        batch.draw(middleGroundPic, 0, 0);
        batch.end();
        for (MoveAnimation animation: waterFallAnimation)
            animation.draw(batch);
        batch.begin();
        batch.draw(frontPic, Main.WIDTH - frontPic.getWidth(), 0);
        batch.end();
    }

    private void createWaterFallAnimation() {
        waterFallAnimation = new ArrayList<MoveAnimation>();
        createWaterFallType6();
        createWaterFallType5();
        createWaterFallType1(10, 390, 360, 475, 35, 2.5, 5, Math.random(), 200);
        createWaterFallType3(20, 345, 345, 475 + Math.random() * 35, 780, 210);
        createWaterFallType1(20, 390, 360, 580, 50, 5, 10, Math.random(), 230);
        createWaterFallType4(10, 670, 10, 1, 250);
        createWaterFallType4(10, 700, 10, 1, 260);
        createWaterFallType4(20, 760, 40, 1, 270);
        createWaterFallType4(5, 860, 10, 5, 290);
        createWaterFallType4(5, 890, 10, 3, 295);
        createWaterFallType3(10, 355, 350, 915 - Math.random() * 40, 810, 300);
        createWaterFallType2();
        createWaterFallType1(10, 475, 455, 610, 25, 2.5, Math.random(), 5, 320);
    }

    private void createWaterFallType6() {
        for (int i = 0; i < 100; ++i) {
            float yStart = 350;
            float yEnd = 0;
            float xStart = (float) (568 + Math.random() * 50);
            float xEnd = (float) (xStart - 10 + 20 * Math.random());
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType5() {
        for (int i = 0; i < 100; ++i) {
            float yStart = 350;
            float yEnd = 100;
            float xStart = (float) (780 + Math.random() * 40);
            float xEnd = (float) (xStart - 5 + 10 * Math.random()) + 10;
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + 100).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType4(int i2, int i3, int i4, int i5, int i6) {
        for (int i = 0; i < i2; ++i) {
            float yStart = 390;
            float yEnd = 360;
            float xStart = (float) (i3 + Math.random() * i4);
            float xEnd = xStart + i5;
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + i6).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType3(int i2, int i3, int i4, double v, int i5, int i6) {
        for (int i = 0; i < i2; ++i) {
            float xStart = (float) (v);
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, (float) i3, (float) i5, (float) i4, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + i6).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType2() {
        for (int i = 0; i < 10; ++i) {
            float yStart = 480;
            float yEnd = 440;
            float xStart = (float) (570 + Math.random() * 25);
            float xEnd = (float) (575 + Math.random() * 10);
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + 310).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType1(int i2, int i3, int i4, int i5, int i6, double v, double random, double i7, int i8) {
        for (int i = 0; i < i2; ++i) {
            float xStart = (float) (i5 + Math.random() * i6);
            float xEnd = (float) (xStart - v + random * i7);
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, (float) i3, xEnd, (float) i4, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + i8).setSpeed((float) (0.4 + Math.random()));
        }
    }
}
