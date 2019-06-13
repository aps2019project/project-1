package graphic.screen;

import com.badlogic.gdx.Gdx;

public class ScreenManager {

    private static Screen screen;

    public static Screen getScreen() {
        return screen;
    }

    public static void setScreen(Screen scr) {
        if (screen != null)
            screen.dispose();
        screen = scr;
        screen.create();
        screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

}
