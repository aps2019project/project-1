package view;

import model.cards.*;
import model.game.Cell;
import model.game.Flag;
import model.game.Game;
import model.other.Account;
import model.variables.CardsArray;

import static model.game.GameType.*;

public class BattleScreen {
    private static Game game;
    public static void showGameInfo(){
        System.out.println("firstPlayer mana is"+game.getFirstPlayer().getMana());
        System.out.println("secondPlayer mana is"+game.getSecondPlayer().getMana());
        if(game.getType() == KILL_HERO) {
            System.out.println("firstPlayer's hero hp is"+game.getFirstPlayer().getHero().getHp());
            System.out.println("secondPlayer's hero hp is"+game.getSecondPlayer().getHero().getHp());
        }
        else if(game.getType() == CAPTURE_THE_FLAG) {
            System.out.println("flag's place is ("+game.getFlags().get(0).getCell().getX()+","+game.getFlags().get(0).getCell().getY()+")");
            System.out.println("flag's taken by "+game.getFlags().get(0).getArmy().getName());
        }
        else if(game.getType() == ROLLUP_FLAGS) {
            for(Flag flag : game.getFlags()) {
                if(flag.isTaken()) {
                    System.out.println(flag.getArmy().getName()+" from "+flag.getArmy().getAccount()+"has flag");
                }
            }
        }
    }
    public static void showMinionsOf(Account account) {
        for(Cell[] rows : game.getTable()) {
            for(Cell cell : rows) {
                if(!cell.isEmpty() && cell.getInsideArmy().getAccount() == account) {
                    showArmy(cell.getInsideArmy());
                }
            }
        }
    }
    public static void showArmy(Army army) {
        System.out.println(army.getID()+": "+army.getName()+", health: "+army.getHp()
                +", location: ("+army.getWhereItIs().getX()+","+army.getWhereItIs().getY()+"), power: "+army.getAr());
    }
    public static void showHero(Hero hero) {
        System.out.println("Hero:");
        System.out.println("Name: "+hero.getName());
        System.out.println("Cost: "+hero.getCoolDown());//
        System.out.println("Desc: "+hero.getMp());//
    }
    public static void showMinion(Minion minion) {
        System.out.println("Minion:");
        System.out.println("Name: "+minion.getName());
        System.out.println("HP: "+minion.getHp());
        System.out.println("Range: "+minion.getRace());//
        System.out.println("Combo-ability: ");//
        System.out.println("Cost: "+minion.getRace());//
        System.out.println("Desc:");//
    }
    public static void showSpell(Spell spell) {
        System.out.println("Spell:");
        System.out.println("Name: "+spell.getName());
        System.out.println("MP: ");//
        System.out.println("Cost: ");//
        System.out.println("Desc: ");//
    }
    public static void showCard(Card card) {
        if(card instanceof Hero) showHero((Hero)card);
        else if(card instanceof Minion) showMinion((Minion)card);
        else if(card instanceof Spell) showSpell((Spell)card);
    }
    public static void showInvalidCardIdError() {
        System.out.println("Invalid card id");
    }
    public static void showInvalidCardNameError() {
        System.out.println("Invalid card name");
    }
    public static void showInvalidMoveError() {
        System.out.println("Invalid target");
    }
    public static void showInvalidAttackError() {
        System.out.println("opponent minion is unavailable for attack");
    }
    public static void showCardArray(CardsArray cards) {
        for(Card card : cards.getAllCards()) {
            showCard(card);
        }
    }
}
