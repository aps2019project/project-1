package graphic.main;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import sun.font.TrueTypeFont;

public class AssetHandler {

    private static AssetManager assetManager = new AssetManager();

    public static AssetManager getData() {
        return assetManager;
    }

    public static void load() {
        assetManager.load("backGround/login_backGround.png", Texture.class);
        assetManager.load("backGround/shop1.png", Texture.class);
        assetManager.load("backGround/shop2.png", Texture.class);
        assetManager.load("backGround/shop3.png", Texture.class);
        assetManager.load("backGround/battle_background.png", Texture.class);
        assetManager.load("backGround/menu1.png", Texture.class);
        assetManager.load("backGround/menu2.png", Texture.class);
        assetManager.load("music/login.mp3", Music.class);
        assetManager.load("music/menu.mp3", Music.class);
        assetManager.load("music/shop.mp3", Music.class);
        assetManager.load("music/battle.mp3", Music.class);
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
        assetManager.load("battle/mana.png", Texture.class);
        assetManager.load("lantern_large_1.png", Texture.class);
        assetManager.load("lantern_large_2.png", Texture.class);
        assetManager.load("lantern_large_3.png", Texture.class);
        assetManager.load("button/menuButton.png", Texture.class);
        assetManager.load("button/menuButtonActive.png", Texture.class);
        assetManager.load("button/profile.png", Texture.class);
        assetManager.load("button/exit.png", Texture.class);
        assetManager.load("fonts/Arial 12.fnt", BitmapFont.class);
        assetManager.load("fonts/Arial 16.fnt", BitmapFont.class);
        assetManager.load("fonts/Arial 24.fnt", BitmapFont.class);
        assetManager.load("fonts/Arial 36.fnt", BitmapFont.class);
        assetManager.load("Card/backGround/deActive.png", Texture.class);
        assetManager.load("Card/backGround/active.png", Texture.class);
        assetManager.load("animation/snow.png", Texture.class);
        assetManager.load("animation/snow2.png", Texture.class);

        /*for (int i = 1; i < 10; ++i) {
            assetManager.load("fonts/" + i + ".ttf", TrueTypeFont.class);
        }*/

    }

}
