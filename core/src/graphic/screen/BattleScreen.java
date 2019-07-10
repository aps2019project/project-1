package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import connection.Client;
import connection.MouseState;
import graphic.Others.ArmyAnimation;
import graphic.Others.BattlePopUp;
import graphic.Others.CardTexture;
import graphic.Others.PopUp;
import graphic.main.*;
import graphic.main.Button;
import model.cards.*;
import model.game.Cell;
import model.game.CellEffect;
import model.game.Game;
import model.game.Player;
import model.other.Account;
import model.other.exeptions.battle.*;
import model.other.exeptions.battle.TargetCellIsEmptyException;

import java.awt.*;
import java.util.*;

public class BattleScreen extends Screen {

    private static String command;

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGround;
    private Game game;
    private Player player1;
    private Player player2;
    private Texture mana;
    private Texture tile;
    private Texture tileSelected;
    private Texture tileHand;
    private Vector2 manaStart1;
    private Vector2 manaStart2;
    private Texture hero1Icon;
    private Texture hero2Icon;
    private Texture heroHpIcon;
    private Texture apIcon;
    private Texture hpIcon;

    private Vector2 tableCord1;
    private Vector2 tableCord2;
    private Vector2 tableCord3;
    private Vector2 tableCord4;
    private Vector2 mousePos;
    private float cellSizeX;
    private float cellSizeY;
    private float cellDistance;

    private Button endTurnButton;
    private Button endGameButton;
    private Button graveyardButton;
    private Button fastForwardButton;
    private boolean fastForward = false;

    private Cell selectedCell;
    private Army selectedArmy;

    private static HashMap<Army, ArmyAnimation> animations = new HashMap<Army, ArmyAnimation>();
    ;

    private Vector2 handStartCord;

    private static HashMap<Cell, Card> handCardsPlayer1;
    private static HashMap<Cell, Card> handCardsPlayer2;

    private Cell selectedCellHand;

    private static ArrayList<BattlePopUp> popUps = new ArrayList<BattlePopUp>();
    private Texture graveyardBg;
    private Vector2 graveyardCord;
    private boolean showingGraveyard;

    private Texture lava;
    private Texture flag;
    private Texture endgameBg;
    private Texture winnerNameSlot;

    private Sound endgameSound;
    private Sound attackSound;
    private Sound deathSound;
    private Sound runSound;
    private boolean endgameSoundPlayed;

    private Gif usableItem;
    private Gif hero1Sp;
    private Gif hero2Sp;

    private boolean heroSpSelected = false;
    private long turnTimePassed;
    private long turnTimeLimit = 30000;

    private Texture timer;

    private HashMap<Item, Gif> itemsGifs = new HashMap<Item, Gif>();

    private Vector2 playerItemStartCord;

    private CardListTexture graveyardList;

    private ArrayList<Gif> animationEvents = new ArrayList<Gif>();

    private MouseState mouseState = MouseState.NOTHING;

    @Override
    public void create() {
        mousePos = new Vector2();
        setCameraAndViewport();
        shapeRenderer = new ShapeRenderer();

        System.out.println("checking created game");
        while (!Game.isGameCreated());
        game = Game.getCurrentGame();
        player1 = game.getFirstPlayer();
        player2 = game.getSecondPlayer();

        backGround = AssetHandler.getData().get("backGround/battle_background.png");
        music = AssetHandler.getData().get("music/battle.mp3");
        mana = AssetHandler.getData().get("battle/mana.png");
        tile = AssetHandler.getData().get("battle/Tile.png");
        tileSelected = AssetHandler.getData().get("battle/tile action.png");
        tileHand = AssetHandler.getData().get("battle/tile hand.png");
        heroHpIcon = AssetHandler.getData().get("battle/icon general hp.png");
        apIcon = AssetHandler.getData().get("battle/ap icon.png");
        hpIcon = AssetHandler.getData().get("battle/hp icon.png");
        graveyardBg = AssetHandler.getData().get("battle/Graveyard bg.png");
        lava = AssetHandler.getData().get("battle/lava.png");
        flag = AssetHandler.getData().get("battle/flag.png");
        endgameBg = AssetHandler.getData().get("battle/endgame bg.png");
        winnerNameSlot = AssetHandler.getData().get("button/daric slot.png");
        endgameSound = AssetHandler.getData().get("sfx/end game.mp3");
        attackSound = AssetHandler.getData().get("sfx/attack.mp3");
        runSound = AssetHandler.getData().get("sfx/run.mp3");
        deathSound = AssetHandler.getData().get("sfx/death.mp3");
        timer = AssetHandler.getData().get("battle/timer.png");

        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

        endTurnButton = new Button("button/yellow.png", "button/yellow glow.png", 1300, 100, "End Turn", "fonts/Arial 24.fnt");
        endGameButton = new Button("button/red.png", "button/red glow.png", 1400, 40, 170, 69, "End Game", "fonts/Arial 16.fnt");
        fastForwardButton = new Button("button/yellow.png", "button/yellow glow.png", 1230, 40, 170, 69, "Normal Speed", "fonts/Arial 16.fnt");
        graveyardButton = new Button("button/yellow.png", "button/yellow glow.png", 100, 50, 200, 80, "Graveyard", "fonts/Arial 24.fnt");

        manaStart1 = new Vector2(270, 730);
        manaStart2 = new Vector2(1330 - mana.getWidth(), 730);

        mousePos = new Vector2();

        hero1Icon = AssetHandler.getData().get(player1.getHero().getIconId());
        hero2Icon = AssetHandler.getData().get(player2.getHero().getIconId());

        tableCord1 = new Vector2(330, 525);
        tableCord2 = new Vector2(1270, 525);
        tableCord3 = new Vector2(330, 125);
        tableCord4 = new Vector2(1270, 125);

        cellDistance = 10;

        cellSizeX = (tableCord2.x - tableCord1.x - 8 * cellDistance) / 9;
        cellSizeY = (tableCord1.y - tableCord3.y - 4 * cellDistance) / 5;

        setCellCords();

        animations.put(player1.getHero(), new ArmyAnimation(player1.getHero().getGifPath()));
        animations.put(player2.getHero(), new ArmyAnimation(player2.getHero().getGifPath()));
        for (Card card : game.getAllInGameCards()) {
            if (card.getType() == CardType.ITEM || card.getType() == CardType.SPELL || card.getType() == CardType.HERO)
                continue;
            if (animations.containsKey((Army) card)) continue;
            ArmyAnimation animation = new ArmyAnimation(card.getGifPath());
            animations.put((Army) card, animation);
        }

        handStartCord = new Vector2(400, 20);
        setHandCells();

        graveyardCord = new Vector2(-graveyardBg.getWidth(), 180);

        if (player1.getUsableItem() != null)
            usableItem = new Gif(new Animation<TextureRegion>(1 / 20f, new TextureAtlas(player1.getUsableItem().getGifPath()).findRegions("gif"), Animation.PlayMode.LOOP));
        if (player1.getHero().getSpellPath() != null)
            hero1Sp = new Gif(new Animation<TextureRegion>(1 / 20f, new TextureAtlas(player1.getHero().getSpellPath()).findRegions("gif"), Animation.PlayMode.LOOP));
        if (player2.getHero().getSpellPath() != null)
            hero2Sp = new Gif(new Animation<TextureRegion>(1 / 20f, new TextureAtlas(player2.getHero().getSpellPath()).findRegions("gif"), Animation.PlayMode.LOOP));

        setItemGifs();
        setItemGifsInPlayer(game.getFirstPlayer());
        setItemGifsInPlayer(game.getSecondPlayer());
        playerItemStartCord = new Vector2(55, 505);

    }

    public void setItemGifs() {
        for (Cell cell : game.getAllCellsInTable()) {
            if (cell.getInsideItem() != null) {
                itemsGifs.put(cell.getInsideItem(), new Gif(new Animation<TextureRegion>(1 / 20f, new TextureAtlas(cell.getInsideItem().getGifPath()).findRegions("gif"), Animation.PlayMode.LOOP)));
            }
        }
    }

    public void setItemGifsInPlayer(Player player) {
        for (Item item : player.getCollectibleItem().getAllItems()) {
            itemsGifs.put(item, new Gif(new Animation<TextureRegion>(1 / 20f, new TextureAtlas(item.getGifPath()).findRegions("gif"), Animation.PlayMode.LOOP)));

        }
    }

    public void setAnimations() {
        game.getFirstPlayer().setHandAnimations(animations);
        game.getSecondPlayer().setHandAnimations(animations);
    }

    public void setCellCords() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                float x = tableCord1.x + col * (cellSizeX + cellDistance);
                float y = tableCord1.y - row * (cellSizeY + cellDistance);
                game.getTable()[row][col].setScreenX(x);
                game.getTable()[row][col].setScreenY(y);
            }
        }
    }

    public void setHandCells() {
        handCardsPlayer1 = new HashMap<Cell, Card>();
        handCardsPlayer2 = new HashMap<Cell, Card>();
        for (int i = 0; i < 5; i++) {
            handCardsPlayer1.put(new Cell((int) handStartCord.x + i * 160, (int) handStartCord.y), null);
            handCardsPlayer2.put(new Cell((int) handStartCord.x + i * 160, (int) handStartCord.y), null);
        }
    }

    public void updateHandCells(Player player) {
        ArrayList<Cell> handCells;
        if (player == player1) {
            handCells = new ArrayList<Cell>(handCardsPlayer1.keySet());
            for (int i = 0; i < player.getHand().getAllCards().size(); i++) {
                handCardsPlayer1.put(handCells.get(i), player.getHand().getAllCards().get(i));
            }
        } else {
            handCells = new ArrayList<Cell>(handCardsPlayer2.keySet());
            for (int i = 0; i < player.getHand().getAllCards().size(); i++) {
                handCardsPlayer2.put(handCells.get(i), player.getHand().getAllCards().get(i));
            }
        }

    }

    @Override
    public void update() {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        game = Game.getCurrentGame();
        player1 = game.getFirstPlayer();
        player2 = game.getSecondPlayer();

        if (game.isGameEnded()) {
            endGame();
        }

        checkTurnTime();
        setAnimations();
        updateGraveyard();
        updateHandCells(game.getWhoIsHisTurn());

        if (game.isAccountTurn(Account.getCurrentAccount()) || game.isIntelligentPlayerTurn()) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            mousePos = viewport.unproject(mousePos);

        } else {
            Client.setMousePos();
            if(Client.getMousePos() != null) {
                mousePos.set(Client.getMousePos());
                mouseState = Client.getMouseState();
                checkTurnTime();
            } else {
                mousePos = new Vector2();
                mouseState = MouseState.NOTHING;
            }
        }

        endTurnButton.setActive(endTurnButton.contains(mousePos));
        endGameButton.setActive(endGameButton.contains(mousePos));
        graveyardButton.setActive(graveyardButton.contains(mousePos));
        fastForwardButton.setActive(fastForwardButton.contains(mousePos));

        if(game.isAccountTurn(Account.getCurrentAccount())){
            mouseState = MouseState.NOTHING;
            Gdx.input.setInputProcessor(new InputProcessor() {
                @Override
                public boolean keyDown(int keycode) {
                    if (keycode == Input.Keys.RIGHT)
                        graveyardList.nextPage();
                    if (keycode == Input.Keys.LEFT)
                        graveyardList.previousPage();
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
                    mouseState = MouseState.TOUCH_DOWN;
                    Client.sendMousePos(mousePos, mouseState);
                    mouseTouchDown();
                    return false;
                }

                @Override
                public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                    mouseState = MouseState.TOUCH_UP;
                    Client.sendMousePos(mousePos, mouseState);
                    mouseTouchUp();
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
        } else{
                if(mouseState == MouseState.TOUCH_DOWN)
                    mouseTouchDown();
                if(mouseState == MouseState.TOUCH_UP)
                    mouseTouchUp();
        }
    }

    public void mouseTouchDown() {
        if (game.isGameEnded()) {
            ScreenManager.setScreen(new MenuScreen());
        }
        if (endTurnButton.isActive()) {
            endTurn();
        } else if (endGameButton.isActive()) {
            game.exitFromGame();
            endGame();
        } else if (graveyardButton.isActive()) {
            showingGraveyard = !showingGraveyard;
        } else if (fastForwardButton.isActive()) {
            setFastForward();
        } else if (checkHeroSp()) {
            if (game.getWhoIsHisTurn().canUseHeroSp())
                heroSpSelected = true;
        } else if (getMouseCell() != null) {
            if (getMouseCell().getInsideArmy() != null && game.getWhoIsHisTurn().isFriend(getMouseCell().getInsideArmy())) {
                selectedCell = getMouseCell();
                selectedCellHand = null;
                heroSpSelected = false;
                selectedArmy = selectedCell.getInsideArmy();
//                        setCommand("select " + selectedArmy.getID().getValue());
//                        synchronized (game){
//                            game.notify();
//                        }
            } else if (selectedArmy != null && getMouseCell().getInsideArmy() == null) {
                Cell cell = getMouseCell();
                if (!game.getWhoIsHisTurn().canMove(selectedCell, cell)) {
                    setPopUp("Invalid Cell");
                    return;
                }
                runSound.play();
                game.getWhoIsHisTurn().moveArmy(selectedCell, cell);
                selectedCell = null;
                selectedArmy = null;
            } else if (selectedCellHand != null && getMouseCell().getInsideArmy() == null) {
//                        Cell cell = getMouseCell();
//                        if(game.getWhoIsHisTurn().moveFromHandToCell(handCards.get(selectedCellHand), cell));
//                            handCards.put(selectedCellHand, null);
//                        selectedCellHand = null;
            } else if (selectedArmy != null && getMouseCell().getInsideArmy() != null) {
                Cell cell = getMouseCell();
                Army target = cell.getInsideArmy();
                if (game.getWhoIsHisTurn().isInRange(selectedCell, cell)) {
                    attackSound.play();
                    animations.get(selectedArmy).getAttackGif().setTime();
                    animationEvents.add(animations.get(selectedArmy).getAttackGif());
                    if (game.getWhoIsHisTurn().isInRange(cell, selectedCell)) {
                        attackSound.play();
                        animations.get(target).getAttackGif().setTime();
                        animationEvents.add(animations.get(target).getAttackGif());
                    }
                }
                try {
                    game.getWhoIsHisTurn().attack(selectedCell, cell);
                } catch (TargetCellIsEmptyException e) {
                    setPopUp("Target Cell Is Empty");
                } catch (TargetNotInRageException e) {
                    setPopUp("Target Not In Range");
                }
                if (selectedCell.getInsideArmy().getHp() <= 0) {
                    deathSound.play();
                    animations.get(selectedArmy).getDeathGif().setTime();
                    animationEvents.add(animations.get(selectedArmy).getDeathGif());
                }
                if (cell.getInsideArmy().getHp() <= 0) {
                    deathSound.play();
                    animations.get(target).getDeathGif().setTime();
                    animationEvents.add(animations.get(target).getDeathGif());
                }
                selectedCell = null;
                selectedArmy = null;
            } else if (heroSpSelected) {
                try {
                    game.getWhoIsHisTurn().useSpecialPower(getMouseCell());
                } catch (NotEnoughManaException e) {
                    setPopUp("Not Enough Mana");
                }
            }
        } else if (getMouseHandCell() != null) {
            selectedCellHand = getMouseHandCell();
            selectedCell = null;
            selectedArmy = null;
            heroSpSelected = false;
        }
    }

    public void mouseTouchUp() {
        if (selectedCellHand != null && getMouseCell() != null && getMouseCell().getInsideArmy() == null) {
            Cell cell = getMouseCell();
            try {
                if (game.getWhoIsHisTurn().moveFromHandToCell(getHandCards(game.getWhoIsHisTurn()).get(selectedCellHand), cell)) ;
            } catch (NotEnoughManaException e) {
                setPopUp("Not Enough Mana");
            } catch (InvalidCellExceprion e) {
                setPopUp("Invalid Cell");
            }
            getHandCards(game.getWhoIsHisTurn()).put(selectedCellHand, null);
            selectedCellHand = null;
        }
    }

    public void endTurn() {
        selectedCell = null;
        selectedArmy = null;
        selectedCellHand = null;
        heroSpSelected = false;
        setCommand("end turn");
        synchronized (game) {
            game.notify();
        }
    }

    public void checkTurnTime() {
        //return;
        turnTimePassed = System.currentTimeMillis() - game.getTurnStartTime();
        if (game.isAccountTurn(Account.getCurrentAccount())) {
            if (turnTimePassed >= turnTimeLimit) {
                mouseState = MouseState.END_TURN;
                Client.sendMousePos(mousePos, mouseState);
                endTurn();
            }
        } else {
            if(mouseState == MouseState.END_TURN) {
                endTurn();
            }
        }

    }

    public void setFastForward() {
        if (fastForward) {
            fastForward = false;
            fastForwardButton.setText("Fast Speed");
            ArmyAnimation.setSPEED(1 / 20f);
        } else {
            fastForward = true;
            fastForwardButton.setText("Normal Speed");
            ArmyAnimation.setSPEED(1 / 40f);
        }
    }

    public void endGame() {
//        Iterator<Thread> iterator = Thread.getAllStackTraces().keySet().iterator();
//        while (iterator.hasNext()) {
//            Thread thread = iterator.next();
//            if (thread.getName().equals("Thread-2")) {
//                thread.interrupt();
//                return;
//            }
//        }
        if (game.isExitFromGame())
            ScreenManager.setScreen(new MenuScreen());
    }

    public void setEndGameScreen(SpriteBatch batch) {
        if (!endgameSoundPlayed) {
            endgameSound.play();
            endgameSoundPlayed = true;
        }
        Texture heroIcon;
        if (game.getWinner().getUsername().equals(player1.getAccount().getUsername()))
            heroIcon = hero1Icon;
        else
            heroIcon = hero2Icon;
        batch.setColor(Main.toColor(new Color(0xAB92918C, true)));
        batch.draw(endgameBg, 0, 0);
        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        batch.draw(heroIcon, 300, 325, heroIcon.getWidth() * 2, heroIcon.getHeight() * 2);
        batch.draw(winnerNameSlot, 630, 400, winnerNameSlot.getWidth() * 2, winnerNameSlot.getHeight() * 2);

        String text = game.getWinner().getUsername() + " Won";
        BitmapFont font = AssetHandler.getData().get("fonts/Arial 36.fnt");
        font.setColor(Main.toColor(new Color(0xFF060200, true)));
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        font.draw(batch, text, 630 + (winnerNameSlot.getWidth() * 2 - glyphLayout.width) / 2, 510);
        font.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));

    }

    public void updateGraveyard() {
        int speed = 10;
        if (showingGraveyard) {
            if (graveyardCord.x < -10)
                graveyardCord.x += speed;
        } else {
            if (graveyardCord.x > -graveyardBg.getWidth())
                graveyardCord.x -= speed;
        }
    }

    public void flipAnimations() {
        for (Army army : animations.keySet()) {
            if (player2.isFriend(army) && animations.get(army) != null)
                animations.get(army).flip();
        }
    }

    public Cell getMouseCell() {
        for (Cell[] row : game.getTable()) {
            for (Cell cell : row) {
                float x = cell.getScreenX();
                float y = cell.getScreenY();
                if (mousePos.x > x && mousePos.x < x + cellSizeX && mousePos.y > y && mousePos.y < y + cellSizeY) {
                    return cell;
                }
            }
        }
        return null;
    }

    public Cell getMouseHandCell() {
        for (Cell cell : getHandCards(game.getWhoIsHisTurn()).keySet()) {
            float x = cell.getX();
            float y = cell.getY();
            if (mousePos.x > x && mousePos.x < x + 160 && mousePos.y > y && mousePos.y < y + 160) {
                return cell;
            }
        }
        return null;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(backGround, 0, 0);
        drawMana(batch);
        drawHeroIcon(batch);
        drawPlayersName(batch);
        drawHeroesHp(batch);

        batch.end();

        drawTable(batch);
        drawEvents(batch);
        batch.begin();

        drawHand(batch);
        drawPopUps(batch);
        drawTimer(batch);

        batch.end();

        endTurnButton.draw(batch);
        endGameButton.draw(batch);
        graveyardButton.draw(batch);
        fastForwardButton.draw(batch);
        drawUsableItem(batch);
        drawHeroesSP(batch);
        drawPlayerItems(batch);

        batch.begin();
        drawGraveYard(batch);
        batch.end();

        if (game.isGameEnded()) {
            batch.begin();
            setEndGameScreen(batch);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        try {
            music.dispose();
        } catch (Exception e) {
            System.out.println("no music playing");
        }
    }

    public void drawMana(SpriteBatch batch) {
        for (int i = 0; i < player1.getMana(); i++) {
            batch.draw(mana, (int) (manaStart1.x + i * mana.getWidth()), (int) manaStart1.y);
        }
        for (int i = 0; i < player2.getMana(); i++) {
            batch.draw(mana, (int) (manaStart2.x - i * mana.getWidth()), (int) manaStart2.y);
        }
    }

    public void drawHeroIcon(SpriteBatch batch) {
        if (game.getWhoIsHisTurn().getAccount().getUsername().equals(player1.getAccount().getUsername())) {
            batch.draw(hero1Icon, 70, 700);
            batch.setColor(Main.toColor(new Color(0xBE928F87, true)));
            batch.draw(hero2Icon, 1310, 700);
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        } else {
            batch.draw(hero2Icon, 1310, 700);
            batch.setColor(Main.toColor(new Color(0xBE928F87, true)));
            batch.draw(hero1Icon, 70, 700);
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }
    }

    public void drawHeroesHp(SpriteBatch batch) {
        batch.draw(heroHpIcon, 142, 690);
        batch.draw(heroHpIcon, 1382, 690);

        BitmapFont font = AssetHandler.getData().get("fonts/Arial 36.fnt");
        GlyphLayout glyphLayout1 = new GlyphLayout();
        GlyphLayout glyphLayout2 = new GlyphLayout();

        glyphLayout1.setText(font, Integer.toString(player1.getHero().getHp()));
        font.draw(batch, Integer.toString(player1.getHero().getHp()), 165, 750);

        glyphLayout2.setText(font, Integer.toString(player2.getHero().getHp()));
        font.draw(batch, Integer.toString(player2.getHero().getHp()), 1405, 750);
    }

    public void drawPlayersName(SpriteBatch batch) {
        BitmapFont font = AssetHandler.getData().get("fonts/Arial 36.fnt");
        GlyphLayout glyphLayout1 = new GlyphLayout();
        GlyphLayout glyphLayout2 = new GlyphLayout();

        glyphLayout1.setText(font, player1.getAccount().getUsername());
        font.draw(batch, player1.getAccount().getUsername(), 290, 830);

        glyphLayout2.setText(font, player2.getAccount().getUsername());
        font.draw(batch, player2.getAccount().getUsername(), 1310 - glyphLayout2.width, 830);

    }

    public void drawGraveYard(SpriteBatch batch) {
        graveyardList = new CardListTexture(3, 1, graveyardCord.x + 30, graveyardCord.y + 150);
        batch.draw(graveyardBg, graveyardCord.x, graveyardCord.y);
        ArrayList<Minion> minions = player1.getGraveYard().getAllMinions();
        for (Minion minion : minions) {
            CardTexture cardTexture = new CardTexture(minion.getName(), minion.getDescription(), minion.getPrice(), minion.getAp(), minion.getHp(), minion.getGifPath());
            graveyardList.addCardTexture(cardTexture);
        }
        batch.end();
        graveyardList.draw(batch);
        batch.begin();
    }


    public void drawTable(SpriteBatch batch) {
        flipAnimations();
        batch.begin();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = game.getTable()[row][col];
                float x = cell.getScreenX();
                float y = cell.getScreenY();
                Army army = game.getTable()[row][col].getInsideArmy();
                drawTileEffect(batch, cell, x, y);
                if (cell.getFlag() != null) {
                    batch.draw(flag, x, y);
                }
                if (selectedCell == cell) {
                    batch.setColor(Main.toColor(new Color(0x55E7EAF9, true)));
                    batch.draw(tileSelected, x, y, cellSizeX, cellSizeY);
                    batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                }
                if (army == null) {
                    if (selectedCellHand != null && player1.isAroundArmies(cell))
                        batch.setColor(Main.toColor(new Color(0xBEBFF7F9, true)));
                    else if (selectedArmy != null && selectedArmy.canMoveTo(cell))
                        batch.setColor(Main.toColor(new Color(0xBEBFF7F9, true)));
                    else
                        batch.setColor(Main.toColor(new Color(0x3DB0C0F9, true)));
                    batch.draw(tile, x, y, cellSizeX, cellSizeY);
                    batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                    drawItem(batch, cell);

                } else {
// <<<<<<< erfan
//                     if(player1.isFriend(army)){
//                         batch.setColor(Main.toColor(new Color(0x750000E3, true)));
//                         batch.draw(tile, x, y, cellSizeX, cellSizeY);
//                         batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
//                         batch.end();
//                         try {
//                             animations.get(army).draw(batch, x - 20, y);
//                         } catch (NullPointerException e) {
//                             System.out.println("dont find army animation");
//                         }
//                         batch.begin();
//                     } else {
//                         batch.setColor(Main.toColor(new Color(0x83C80000, true)));
//                         batch.draw(tile, x, y, cellSizeX, cellSizeY);
//                         batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
//                         batch.end();
//                         try {
//                             animations.get(army).draw(batch, x - 10, y, 160, 160);
//                         } catch (NullPointerException e){
//                         System.out.println("dont find army animation");
//                         }
//                         batch.begin();
//                     }
//                     try {
//                         batch.draw(apIcon, animations.get(army).getX() + 15, animations.get(army).getY());
//                         batch.draw(hpIcon, animations.get(army).getX() + 75, animations.get(army).getY());
//                     } catch (NullPointerException e) {
//                         System.out.println("dont find army animation");
//                     }

//                     BitmapFont font = AssetHandler.getData().get("fonts/Arial 24.fnt");
//                     GlyphLayout ap = new GlyphLayout();
//                     GlyphLayout hp = new GlyphLayout();

//                     font.setColor(Main.toColor(new Color(0xFFDEA900, true)));
//                     ap.setText(font, Integer.toString(army.getAp()));
// //                    font.draw(batch,Integer.toString(army.getAp()), animations.get(army).getX() + 35 , animations.get(army).getY() + 25);
//                     try {
//                         font.draw(batch, Integer.toString(army.getAp()), (animations.get(army).getX() + 15 + apIcon.getWidth() / 2) - ap.width / 2, animations.get(army).getY() + 25);
//                     } catch (NullPointerException e) {
//                         System.out.println("dont find army animation");
//                     }
//                     font.setColor(Main.toColor(new Color(0xFFBD1900, true)));
//                     hp.setText(font, Integer.toString(army.getHp()));
// //                    font.draw(batch,Integer.toString(army.getHp()), animations.get(army).getX() + 95, animations.get(army).getY() + 25);
//                     try {
//                         font.draw(batch,Integer.toString(army.getHp()), (animations.get(army).getX() +75+ hpIcon.getWidth()/2) - hp.width/2, animations.get(army).getY() + 25);
//                     } catch (NullPointerException e) {
//                         System.out.println("dont find army animation");
//                     }
//                     font.setColor(Main.toColor(new Color(0xFFFFFFFF, true)))
                    try {
                        if (player1.isFriend(army)) {
                            batch.setColor(Main.toColor(new Color(0x750000E3, true)));
                            batch.draw(tile, x, y, cellSizeX, cellSizeY);
                            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                            batch.end();
                            if (animations.get(army) != null)
                                drawArmy(batch, army, x, y);
                            batch.begin();
                        } else {
                            batch.setColor(Main.toColor(new Color(0x83C80000, true)));
                            batch.draw(tile, x, y, cellSizeX, cellSizeY);
                            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
                            batch.end();
                            if (animations.get(army) != null)
                                drawArmy(batch, army, x, y);
                            batch.begin();
                        }
                        if (animations.get(army) != null) {
                            batch.draw(apIcon, animations.get(army).getX() + 15, animations.get(army).getY());
                            batch.draw(hpIcon, animations.get(army).getX() + 75, animations.get(army).getY());
                        }

                        BitmapFont font = AssetHandler.getData().get("fonts/Arial 24.fnt");
                        GlyphLayout ap = new GlyphLayout();
                        GlyphLayout hp = new GlyphLayout();

                        font.setColor(Main.toColor(new Color(0xFFDEA900, true)));
                        ap.setText(font, Integer.toString(army.getAp()));
                        font.draw(batch, Integer.toString(army.getAp()), (animations.get(army).getX() + 15 + apIcon.getWidth() / 2) - ap.width / 2, animations.get(army).getY() + 25);

                        font.setColor(Main.toColor(new Color(0xFFBD1900, true)));
                        hp.setText(font, Integer.toString(army.getHp()));
                        font.draw(batch, Integer.toString(army.getHp()), (animations.get(army).getX() + 75 + hpIcon.getWidth() / 2) - hp.width / 2, animations.get(army).getY() + 25);
                        font.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));
                    } catch (NullPointerException n) {
                    }

                }

            }
        }
        batch.end();
    }

    public void drawArmy(SpriteBatch batch, Army army, float x, float y) {
        if (animationEvents.size() != 0 && animations.get(army).haveGif(animationEvents.get(0)))
            return;
        animations.get(army).draw(batch, x - 20, y);
    }

    public void drawEvents(SpriteBatch batch) {
        if (animationEvents.size() == 0)
            return;
        animationEvents.get(0).draw(batch);
        if (animationEvents.get(0).isFinished()) {
            if (animationEvents.get(0).getType() == GifType.DEATH)
                game.setupCardsDeaf();
            animationEvents.remove(0);
        }
    }

    public void drawItem(SpriteBatch batch, Cell cell) {
        if (cell.getInsideItem() != null) {
            batch.end();
            itemsGifs.get(cell.getInsideItem()).draw(batch, cell.getScreenX(), cell.getScreenY(), 90, 90);
            batch.begin();
        }
    }

    public void drawTileEffect(SpriteBatch batch, Cell cell, float x, float y) {
        if (cell.getCellEffect() == CellEffect.FIERY) {
            batch.draw(lava, x, y, cellSizeX, cellSizeY);
        } else if (cell.getCellEffect() == CellEffect.POISON) {
            batch.setColor(Main.toColor(new Color(0xCB187600, true)));
            batch.draw(tile, x, y, cellSizeX, cellSizeY);
        } else if (cell.getCellEffect() == CellEffect.HOLY) {
            batch.setColor(Main.toColor(new Color(0xFFF8FF00, true)));
            batch.draw(tile, x, y, cellSizeX, cellSizeY);
        }
    }

    public void drawHand(SpriteBatch batch) {
        HashMap<Cell, Card> handCards = getHandCards(game.getWhoIsHisTurn());
        for (Cell cell : handCards.keySet()) {
            if (selectedCellHand == cell) {
                batch.setColor(Main.toColor(new Color(0xFFDCDCDC, true)));
            } else {
                batch.setColor(Main.toColor(new Color(0xFF232323, true)));
            }
            batch.draw(tileHand, cell.getX(), cell.getY(), 160, 160);
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
            if (handCards.get(cell) == null) {
                continue;
            }
            if (handCards.get(cell).getType() == CardType.SPELL) {
                continue;
            }
            batch.end();

            animations.get(handCards.get(cell)).draw(batch, cell.getX() - 15, cell.getY() + 10, 180, 180);
            batch.begin();

            batch.draw(mana, cell.getX() + 70, cell.getY());

            BitmapFont font = AssetHandler.getData().get("fonts/Arial 16.fnt");
            font.setColor(Main.toColor(new Color(0xFF000000, true)));
            font.draw(batch, Integer.toString(handCards.get(cell).getNeededManaToPut()), cell.getX() + 85, cell.getY() + 25);
            font.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));

        }
    }

    public static void drawPopUps(SpriteBatch batch) {
        try {
            Iterator<BattlePopUp> iterator = popUps.iterator();
            while (iterator.hasNext()) {
                BattlePopUp popUp = iterator.next();
                if (popUp.getTime() > 3) {
                    iterator.remove();
                    continue;
                }
                popUp.draw(batch);
            }
        } catch (ConcurrentModificationException c) {
        }
    }

    public void drawUsableItem(SpriteBatch batch) {
        try {
            usableItem.draw(batch, 120, 120, 150, 150);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("usableItem is null!!");
        }
    }

    public void drawHeroesSP(SpriteBatch batch) {
        BitmapFont font = AssetHandler.getData().get("fonts/Arial 16.fnt");
        font.setColor(Main.toColor(new Color(0xFF000000, true)));
        if (hero1Sp != null) {
            if (!player1.canUseHeroSp())
                batch.setColor(Main.toColor(new Color(0xA4B4B2B1, true)));
            hero1Sp.draw(batch, 120, 560, 120, 120);
            batch.begin();
            batch.draw(mana, 163, 560);
            font.draw(batch, Integer.toString(player1.getHero().getMp()), 178, 585);
            batch.end();
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }
        if (hero2Sp != null) {
            if (!player2.canUseHeroSp())
                batch.setColor(Main.toColor(new Color(0xA4B4B2B1, true)));
            hero2Sp.draw(batch, 1380, 560, 120, 120);
            batch.begin();
            batch.draw(mana, 1420, 550);
            font.draw(batch, Integer.toString(player1.getHero().getMp()), 1435, 580);
            batch.end();
            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);

        }
        font.setColor(Main.toColor(new Color(0xFFFFFFFF, true)));
    }

    public boolean checkHeroSp() {
        return mousePos.x >= 120 && mousePos.x <= 120 + 120 && mousePos.y >= 550 && mousePos.y <= 550 + 120;
    }

    public void drawTimer(SpriteBatch batch) {
        if (turnTimePassed <= turnTimeLimit / 2)
            batch.setColor(2 * (float) turnTimePassed / turnTimeLimit, 1, 0, 1);
        else
            batch.setColor(1, 1f - (float) (2 * turnTimePassed - turnTimeLimit) / turnTimeLimit, 0, 1);
        batch.draw(timer, 1326, 190, (255 - 2 * 26) * (1 - (float) turnTimePassed / turnTimeLimit), 7.5f);
        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
    }

    public void drawPlayerItems(SpriteBatch batch) {
        for (int i = 0; i < player1.getCollectibleItem().getAllItems().size(); i++) {
            Gif itemGif = findItemGif(player1.getCollectibleItem().getAllItems().get(i));
            itemGif.draw(batch, playerItemStartCord.x, playerItemStartCord.y - i * 100, 90, 90);
        }
    }

    public Gif findItemGif(Item item) {
        for (Item item1 : itemsGifs.keySet()) {
            if (item1.getName().equals(item.getName()))
                return itemsGifs.get(item1);
        }
        return null;
    }

    public static ArrayList<BattlePopUp> getPopUps() {
        return popUps;
    }

    public static String getCommand() {
        return command;
    }

    public static void setCommand(String string) {
        command = string;

    }

    public static HashMap<Army, ArmyAnimation> getAnimations() {
        return animations;
    }

    public HashMap<Cell, Card> getHandCards(Player player) {
        if(player == player1)
            return handCardsPlayer1;
        return handCardsPlayer2;
    }

    public static void setPopUp(String text) {
        PopUp.getInstance().setText(text);
    }
}
