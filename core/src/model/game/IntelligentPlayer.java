package model.game;

import model.cards.Army;
import model.cards.Card;
import model.other.Account;
import model.other.exeptions.battle.InvalidCellExceprion;
import model.other.exeptions.battle.NotEnoughManaException;
import model.variables.CardsArray;

public class IntelligentPlayer extends Player {
    private Game game;
    public IntelligentPlayer(Account account) {
        super(account);
    }
    public IntelligentPlayer(Account account,Deck deck) {
        super(account,deck);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void play() {
        endTurn = false;
        this.setUpBuffs();
        this.checkPassive();
        increaseTurnNumber();
        setMana();
        deck.transferCardTo(hand);
        CardsArray ourArmies = game.getAllAccountArmiesInCellArray(game.getAllCellsInTable(),account);

        if(ourArmies.getAllCards().size() != 0) {
            try {
                Card card = ourArmies.getRandomCard();
                Cell destinationCell = Cell.getRandomCell(game.getAllCellsWithUniqueDistance(card.getWhereItIs(), 2));
                super.moveArmy(card.getWhereItIs(), destinationCell);
                super.moveFromHandToCell(hand.getRandomCard(), Cell.getRandomCell(game.getAllCellsNearArmies(ourArmies)));
            } catch (IllegalStateException i ){}
            catch (NotEnoughManaException e){}
            catch (InvalidCellExceprion e){}
        }
        endTurn = true;
    }
}
