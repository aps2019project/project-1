package graphic.main;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetHandler {

    private static AssetManager assetManager = new AssetManager();

    public static AssetManager getData() {
        return assetManager;
    }

    public static synchronized void load() {
        assetManager.load("backGround/login_backGround.png", Texture.class);
        assetManager.load("backGround/shop1.png", Texture.class);
        assetManager.load("backGround/shop2.png", Texture.class);
        assetManager.load("backGround/shop3.png", Texture.class);
        assetManager.load("backGround/battle_background.png", Texture.class);
        assetManager.load("backGround/menu1.png", Texture.class);
        assetManager.load("backGround/menu2.png", Texture.class);
        assetManager.load("backGround/collection1.png", Texture.class);
        assetManager.load("backGround/collection2.png", Texture.class);
        assetManager.load("backGround/collection3.png", Texture.class);
        assetManager.load("backGround/background_ChooseNumberOfPlayersMenu.psd", Texture.class);
        assetManager.load("backGround/brand.png", Texture.class);
        assetManager.load("backGround/storyMenu.jpg", Texture.class);
        assetManager.load("backGround/customFirstMenu.png", Texture.class);
        assetManager.load("backGround/secondCustomMenu.jpg", Texture.class);
        assetManager.load("backGround/global background.png", Texture.class);
        assetManager.load("backGround/global forground.png", Texture.class);
        assetManager.load("backGround/auction back.png", Texture.class);


        assetManager.load("button/decks/activeDeck.png", Texture.class);

        assetManager.load("animation/waterFall.png", Texture.class);
        assetManager.load("music/login.mp3", Music.class);
        assetManager.load("music/menu.mp3", Music.class);
        assetManager.load("music/shop.mp3", Music.class);
        assetManager.load("music/battle.mp3", Music.class);
        assetManager.load("music/collection.mp3", Music.class);
        assetManager.load("sfx/click.mp3", Sound.class);
        assetManager.load("button/login1.png", Texture.class);
        assetManager.load("button/login2.png", Texture.class);
        assetManager.load("button/signUp1.png", Texture.class);
        assetManager.load("button/signUp2.png", Texture.class);
        assetManager.load("button/button_close.png", Texture.class);

        assetManager.load("slots/confirm.png", Texture.class);
        assetManager.load("slots/empty.png", Texture.class);
        assetManager.load("slots/password.png", Texture.class);
        assetManager.load("slots/userName.png", Texture.class);
        assetManager.load("slots/offline account.png", Texture.class);
        assetManager.load("slots/online account.png", Texture.class);

        assetManager.load("battle/mana.png", Texture.class);
        assetManager.load("battle/icon general hp.png", Texture.class);
        assetManager.load("lantern_large_1.png", Texture.class);
        assetManager.load("lantern_large_2.png", Texture.class);
        assetManager.load("lantern_large_3.png", Texture.class);
        assetManager.load("button/menuButton.png", Texture.class);
        assetManager.load("button/menuButtonActive.png", Texture.class);
        assetManager.load("button/profile.png", Texture.class);
        assetManager.load("button/exit.png", Texture.class);
        assetManager.load("button/auction card.png", Texture.class);

        assetManager.load("fonts/Arial 12.fnt", BitmapFont.class);
        assetManager.load("fonts/Arial 16.fnt", BitmapFont.class);
        assetManager.load("fonts/Arial 20.fnt", BitmapFont.class);
        assetManager.load("fonts/Arial 24.fnt", BitmapFont.class);
        assetManager.load("fonts/Arial 36.fnt", BitmapFont.class);

        assetManager.load("Card/backGround/hero deActive.png", Texture.class);
        assetManager.load("Card/backGround/hero active.png", Texture.class);
        assetManager.load("Card/backGround/spell deActive.png", Texture.class);
        assetManager.load("Card/backGround/spell active.png", Texture.class);
        assetManager.load("Card/backGround/price tag.png", Texture.class);
        assetManager.load("animation/snow.png", Texture.class);
        assetManager.load("animation/snow2.png", Texture.class);
        assetManager.load("Card/Hero/Icon/1.png", Texture.class);
        assetManager.load("Card/Hero/Icon/2.png", Texture.class);
        assetManager.load("Card/Hero/Icon/3.png", Texture.class);
        assetManager.load("Card/Hero/Icon/4.png", Texture.class);
        assetManager.load("Card/Hero/Icon/5.png", Texture.class);
        assetManager.load("Card/Hero/Icon/6.png", Texture.class);
        assetManager.load("Card/Hero/Icon/7.png", Texture.class);
        assetManager.load("Card/Hero/Icon/8.png", Texture.class);
        assetManager.load("Card/Hero/Icon/9.png", Texture.class);
        assetManager.load("Card/Hero/Icon/10.png", Texture.class);
        assetManager.load("Card/Hero/Icon/11.png", Texture.class);
        assetManager.load("battle/Tile.png", Texture.class);
        assetManager.load("battle/tile action.png", Texture.class);
        assetManager.load("button/yellow.png", Texture.class);
        assetManager.load("button/yellow glow.png", Texture.class);
        assetManager.load("button/red.png", Texture.class);
        assetManager.load("button/red glow.png", Texture.class);
        assetManager.load("button/green.png", Texture.class);
        assetManager.load("button/green glow.png", Texture.class);
        assetManager.load("button/shop left.png", Texture.class);
        assetManager.load("button/shop left active.png", Texture.class);
        assetManager.load("button/shop middle.png", Texture.class);
        assetManager.load("button/shop middle active.png", Texture.class);
        assetManager.load("button/shop right.png", Texture.class);
        assetManager.load("button/shop right active.png", Texture.class);
        assetManager.load("button/shop sb.png", Texture.class);
        assetManager.load("button/shop sb active.png", Texture.class);
        assetManager.load("button/shop done.png", Texture.class);
        assetManager.load("button/back.png", Texture.class);
        assetManager.load("button/deckSlot.png", Texture.class);
        assetManager.load("button/deckSlotGlow.png", Texture.class);
        assetManager.load("button/daric slot.png", Texture.class);
        assetManager.load("simpleIcons/fire2.png", Texture.class);
        assetManager.load("simpleIcons/fire1.png", Texture.class);
        assetManager.load("simpleIcons/fire3.png", Texture.class);
        assetManager.load("button/choosePlayerButton3.psd", Texture.class);
        assetManager.load("button/choosePlayerButton3-1.psd", Texture.class);
        assetManager.load("button/choosePlayerButton1.psd", Texture.class);
        assetManager.load("button/choosePlayerButton1-1.psd", Texture.class);
        assetManager.load("button/choosePlayerButton2.psd", Texture.class);
        assetManager.load("button/choosePlayerButton2-1.psd", Texture.class);
        assetManager.load("button/lastGameButton.png", Texture.class);
        assetManager.load("sfx/playerChangeButton1.mp3", Sound.class);
        assetManager.load("sfx/playerChangeButton2.mp3", Sound.class);
        assetManager.load("sfx/playerChangeButton3.mp3", Sound.class);
        assetManager.load("battle/tile action.png", Texture.class);
        assetManager.load("simpleIcons/stone1.png", Texture.class);
        assetManager.load("simpleIcons/stone2.png", Texture.class);
        assetManager.load("simpleIcons/stone3.png", Texture.class);
        assetManager.load("button/storySelectScreen.atlas", TextureAtlas.class);
        assetManager.load("button/decks/deActiveDeck.png", Texture.class);
        assetManager.load("battle/tile hand.png", Texture.class);
        assetManager.load("battle/ap icon.png", Texture.class);
        assetManager.load("battle/hp icon.png", Texture.class);
        assetManager.load("battle/Graveyard bg.png", Texture.class);
        assetManager.load("battle/lava.png", Texture.class);
        assetManager.load("battle/flag.png", Texture.class);
        assetManager.load("battle/endgame bg.png", Texture.class);
        assetManager.load("slots/text field.png", Texture.class);
        assetManager.load("backGround/custom card bg.png", Texture.class);
        assetManager.load("sfx/end game.mp3", Sound.class);
        assetManager.load("sfx/attack.mp3", Sound.class);
        assetManager.load("sfx/run.mp3", Sound.class);
        assetManager.load("sfx/death.mp3", Sound.class);
        assetManager.load("battle/timer.png", Texture.class);
        assetManager.load("button/card in deck.png", Texture.class);
        assetManager.load("button/card in deck1.png", Texture.class);
        assetManager.load("button/deckName.png", Texture.class);
        assetManager.load("button/secondCustom1.png", Texture.class);
        assetManager.load("button/secondCustom1-1.png", Texture.class);
        assetManager.load("button/increaseButton.png", Texture.class);
        assetManager.load("button/decreaseButton.png", Texture.class);
        assetManager.load("button/multiplayer1.png", Texture.class);
        assetManager.load("button/multiplayer1-1.png", Texture.class);

        assetManager.load("pattern/basketball.png", Texture.class);
        assetManager.load("pattern/title.png", Texture.class);
        assetManager.load("pattern/carbon.png", Texture.class);
        assetManager.load("pattern/cube.png", Texture.class);
        assetManager.load("pattern/diagmond.png", Texture.class);
        assetManager.load("pattern/flowers.png", Texture.class);
        assetManager.load("pattern/wall.png", Texture.class);
        assetManager.load("pattern/wood.png", Texture.class);
        assetManager.load("pattern/chatSlot.png", Texture.class);
        assetManager.load("pattern/wild-flowers.png", Texture.class);
        assetManager.load("pattern/message.png", Texture.class);

        for (int i = 1; i < 35; ++i) {
            assetManager.load("chat/" + i + ".png", Texture.class);
        }

        for(int i = 1; i <= 10; i++)
            assetManager.load("Card/Hero/generals/" + i + ".png", Texture.class);
        for(int i = 1; i <= 10; i++) {
            assetManager.load("Card/Hero/general spell/" + i + ".png", Texture.class);
            assetManager.load("Card/Hero/general spell/" + i + ".atlas", TextureAtlas.class);
        }
        for (int i = 1; i <= 10; ++i)
            assetManager.load("Card/Hero/" + i + ".atlas", TextureAtlas.class);
        for (int i = 1; i <= 20; ++i)
            assetManager.load("Card/Item/" + i + ".atlas", TextureAtlas.class);
        for (int i = 1; i <= 20; ++i)
            assetManager.load("Card/Spell/" + i + ".atlas", TextureAtlas.class);
        for (int i = 1; i <= 40; ++i)
            assetManager.load("Card/Minion/" + i + ".atlas", TextureAtlas.class);
    }

}
