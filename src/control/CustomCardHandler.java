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
        data.add(scanner.nextLine());
        CustomCardHandlerScreen.showEnterPrice();
        data.add(scanner.nextLine());

        switch (cardType) {
            case HERO:
                getMinionAndHero();
                data.add(0, Integer.toString(Hero.getLastNumebr() + 1));
                CvsWriter.write("Heroes", data);
                break;
            case MINION:
                getMinionAndHero();
                data.add(0, Integer.toString(Minion.getLastNumebr() + 1));
                CvsWriter.write("Minions", data);
                break;
            case SPELL:
                getSpell();
                data.add(0, Integer.toString(Spell.getLastNumebr() + 1));
                CvsWriter.write("Spells", data);
                break;
        }
        return MENU;
    }

    public void getMinionAndHero() {
        if(cardType == CardType.MINION) {
            CustomCardHandlerScreen.showEnterMana();
            data.add(scanner.nextLine());
        }
        CustomCardHandlerScreen.showEnterHp();
        data.add(scanner.nextLine());

        CustomCardHandlerScreen.showEnterAp();
        data.add(scanner.nextLine());

        CustomCardHandlerScreen.showEnterAttackType();
        data.add(scanner.nextLine());

        CustomCardHandlerScreen.showEnterRange();
        data.add(scanner.nextLine());

        CustomCardHandlerScreen.showEnterSpecialPower();
        data.add(scanner.nextLine());

        if(cardType == CardType.MINION){
            CustomCardHandlerScreen.showEnterSpecialPowerActivation();
            data.add(scanner.nextLine());
        } else {
            CustomCardHandlerScreen.showEnterMP();
            data.add(scanner.nextLine());

            CustomCardHandlerScreen.showsSecialPowerCooldown();
            data.add(scanner.nextLine());
        }
    }

    public void getSpell() {
        CustomCardHandlerScreen.showEnterMana();
        data.add(scanner.nextLine());

        CustomCardHandlerScreen.showEnterTarget();
        data.add(scanner.nextLine());
    }

    public void getBuff() {

    }

}
