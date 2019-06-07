package control;

import model.cards.CardType;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.other.Account;
import view.CustomCardHandlerScreen;
import view.MenuScreen;

import java.util.ArrayList;

import static control.HandlerType.*;

public class CustomCardHandler extends Handler {
    ArrayList<String> data = new ArrayList<>();
    CardType cardType;

    CustomCardHandler() {
        MenuScreen.showWelcomeLine(Account.getCurrentAccount().getUsername());
        MenuScreen.options();
    }

    @Override
    public HandlerType handleCommands() {
        CustomCardHandlerScreen.start();

        CustomCardHandlerScreen.showEnterType();
        cardType = CardType.valueOf(scanner.nextLine().toUpperCase());
        CustomCardHandlerScreen.showEnterName();
        data.add(scanner.next());
        CustomCardHandlerScreen.showEnterPrice();
        data.add(scanner.next());

        switch (cardType) {
            case HERO:
                data.add(0, Integer.toString(Hero.getLastNumebr() + 1));
                CvsWriter.write("Heroes", data);
                break;
            case MINION:
                data.add(0, Integer.toString(Minion.getLastNumebr() + 1));
                CvsWriter.write("Minions", data);
                break;
            case SPELL:
                data.add(0, Integer.toString(Spell.getLastNumebr() + 1));
                CvsWriter.write("Spells", data);
                break;
        }
        return MENU;
    }

}
