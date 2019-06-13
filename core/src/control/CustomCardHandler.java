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
    ArrayList<String> data = new ArrayList<String>();
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
                data.add(0, Integer.toString(Hero.getLastNumber() + 1));
                getMinionAndHero();
                CvsWriter.write("Heroes", data);
                Hero.createHero(data.toArray(new String[data.size()]));
                break;
            case MINION:
                data.add(0, Integer.toString(Minion.getLastNumber() + 1));
                getMinionAndHero();
                CvsWriter.write("Minions", data);
                Minion.createMinion(data.toArray(new String[data.size()]));
                break;
            case SPELL:
                data.add(0, Integer.toString(Spell.getLastNumber() + 1));
                getSpell();
                CvsWriter.write("Spells", data);
                Spell.createSpell(data.toArray(new String[data.size()]));
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
        data.add(scanner.nextLine().toLowerCase());

        CustomCardHandlerScreen.showEnterRange();
        data.add(scanner.nextLine());

        if(cardType == CardType.MINION){
            CustomCardHandlerScreen.showEnterSpecialPowerActivation();
            data.add(scanner.nextLine().toLowerCase());
        } else {
            CustomCardHandlerScreen.showEnterMP();
            data.add(scanner.nextLine());

            CustomCardHandlerScreen.showsSecialPowerCooldown();
            data.add(scanner.nextLine());
        }

        CustomCardHandlerScreen.showEnterSpecialPower();
        getBuff();
    }

    public void getSpell() {
        CustomCardHandlerScreen.showEnterMana();
        data.add(scanner.nextLine());

        CustomCardHandlerScreen.showEnterTarget();
        data.add(scanner.nextLine().toLowerCase());

        CustomCardHandlerScreen.showEnterSpecialPower();
        getBuff();
    }

    public void getBuff() {
        CustomCardHandlerScreen.showEnterBuffName();
        String buffName = scanner.nextLine();
        switch (cardType){
            case HERO:
                data.add(7, buffName);
                break;
            case MINION:
                data.add(8, buffName);
                break;
            case SPELL:
                data.add(buffName);
                break;
        }
        CustomCardHandlerScreen.showEnterBuffType();
        String buffType = scanner.nextLine().toLowerCase();
        data.add(buffType) ;

        if(buffType.equals("power") || buffType.equals("weakness")){
            CustomCardHandlerScreen.showEnterPowerBuffType();
            data.add(scanner.nextLine());
        }

        CustomCardHandlerScreen.showEnterBuffValue();
        data.add(scanner.nextLine()) ;

        CustomCardHandlerScreen.showEnterBuffDelay();
        data.add(scanner.nextLine()) ;

        CustomCardHandlerScreen.showEnterBuffLast();
        data.add(scanner.nextLine()) ;

        CustomCardHandlerScreen.showEnterBuffFrindOrEnemy();
        data.add(scanner.nextLine()) ;
    }

}
