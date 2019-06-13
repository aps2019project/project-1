package graphic.main;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import sun.font.TrueTypeFont;

public class AssetHandler {

    private static AssetManager assetManager = new AssetManager();

    public static AssetManager getData() {
        return assetManager;
    }

    public static void load() {
        assetManager.load("backGround/login_backGround.png", Texture.class);
        assetManager.load("backGround/loginInfo.png", Texture.class);
        assetManager.load("backGround/signUpInfo.png", Texture.class);
        assetManager.load("badlogic.jpg", Texture.class);
        assetManager.load("music/login.mp3", Music.class);
        assetManager.load("sfx/click.mp3", Sound.class);
        assetManager.load("button/login1.png", Texture.class);
        assetManager.load("button/login2.png", Texture.class);
        assetManager.load("button/signUp1.png", Texture.class);
        assetManager.load("button/signUp2.png", Texture.class);
        assetManager.load("button/button_close.png", Texture.class);

        /*for (int i = 1; i < 10; ++i) {
            assetManager.load("fonts/" + i + ".ttf", TrueTypeFont.class);
        }*/

    }

}
