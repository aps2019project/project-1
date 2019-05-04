package view;

import model.cards.*;
import model.game.Cell;
import model.game.Deck;
import model.game.Flag;
import model.game.Game;
import model.other.Account;
import model.variables.CardsArray;

import java.util.ArrayList;

import static model.game.GameType.*;

public class BattleScreen extends Screen{
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
    public static void showNextCardFromDeck() {
        System.out.println("next card is ");
        showCard(game.getWhoIsHisTurn().getDeck().getNextCard());
    }
    public static void showErrorYourNotInGraveYard() {
        System.out.println("your not in grave yard");
    }
    public static void showArmyCanGoTo(Army army) {
        System.out.println("you can move " + army.getID() + " to cells:");
        ArrayList<Cell> cells = game.getAllCellsWithUniqueDistance(army.getWhereItIs(),2);
        for(Cell cell : cells) {
            showCellsCoordinate(cell);
        }
        System.out.println();
    }
    public static void showArmyCanAttackTo(Army army) {
        ArrayList<Cell> boardCells;
        if(army.getAttackType() == AttackType.MELEE)       boardCells = game.getAllNearCells(army.getWhereItIs());
        else if(army.getAttackType() == AttackType.RANGED) boardCells = game.getAllNonNearCells(army.getWhereItIs());
        else                                               boardCells = game.getAllCellsInTable();
        CardsArray allThisCardsEnemies = game.getAllAccountArmiesInCellArray(boardCells,game.getAnotherAccount(army.getAccount()));
        System.out.println(army.getID()+" can attack to:");
        for(Card card : allThisCardsEnemies.getAllCards()) {
            System.out.print(card.getID()+" that it is in ");
            showCellsCoordinate(card.getWhereItIs());
        }
    }
    public static void showCellsCoordinate(Cell cell) {
        System.out.print(" ("+cell.getX()+","+cell.getY()+")");
    }
    public static void showErrorInvalidDeck() {
        System.out.println("selected deck is invalid");
    }
    public static void showSelectNumberOfPlayerMenu() {
        System.out.println("1. single player");
        System.out.println("2. multi player");
    }
    public static void showSinglePlayerMenu() {
        System.out.println("1. story");
        System.out.println("2. custom game");
    }
    public static void showStoryMenu() {
        System.out.println("1. first step");
        System.out.println("2. second step");
        System.out.println("3. third step");
    }

    public static void showCustomMenu() {
        System.out.println("1. first hero");
        System.out.println("2. second hero");
        System.out.println("3. third hero");
    }
    public static void decks(ArrayList<Deck> decks) {
        System.out.println("your decks are:");
        for(Deck deck : decks) {
            System.out.println(deck.getName());
        }
    }
    public static void showModes() {
        System.out.println("1. kill hero");
        System.out.println("2. capture the flag");
        System.out.println("3. rollup flags");
    }
    public static void showInvalidCommand() {
        System.out.println("invalid command");
    }
}
