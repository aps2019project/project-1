package control;

import model.cards.Army;
import model.cards.Card;
import model.cards.Item;
import model.game.Cell;
import model.game.Game;
import model.other.Account;
import model.variables.CardsArray;
import view.BattleScreen;

import javax.management.BadAttributeValueExpException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BattleHandler extends Handler{
    private static Game game = Game.getCurrentGame();
    {
        BattleScreen.setGame();
    }
    public void getOrder() {
        handleCommands();
    }

    @Override
    HandlerType handleCommands() {
        while (!game.getWhoIsHisTurn().isEndTurn() && !game.isExitFromGame() && scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            game = Game.getCurrentGame();
            if (command.matches("game info")) {
                BattleScreen.showGameInfo();
            } else if (command.matches("show my minions")) {
                BattleScreen.showMinionsOf(game.getWhoIsHisTurn().getAccount());
            } else if (command.matches("show opponent minions")) {
                BattleScreen.showMinionsOf(game.getWhoIsNotHisTurn().getAccount());
            } else if (command.matches("show card info [^ ]+")) {
                BattleScreen.showCard(game.findInTable(command.split(" ")[3]).getInsideArmy());
            }else if (command.matches("select [^ ]+")) {
                select();
            }else if (command.matches("move to[(]\\d+,\\d+[)]")) {
                if(!game.getWhoIsHisTurn().moveArmy(game.getWhoIsHisTurn().getSelectedCardPlace()
                        ,getCell(command.split(" ")[1]))) {
                    BattleScreen.showInvalidMoveError();
                }
            }else if (command.matches("attack [^ ]+")) {
//                if(!game.getWhoIsHisTurn().attack(game.findInTable(command.split(" ")[1]))) {
//                    BattleScreen.showInvalidAttackError();
//                }
            }else if (command.matches("attack combo [^ ]+( [^ ]+)+")) {
                Cell opponentCardCell = game.findInTable(command.split(" ")[2]);
                Cell myCardCell = game.findInTable(command.split(" ")[3]);
                ArrayList<Cell> cells = new ArrayList<Cell>();
                int counter = 4;
                while(counter < command.split(" ").length) {
                    cells.add(game.findInTable(command.split(" ")[counter]));
                    counter++;
                }
                game.getWhoIsHisTurn().attackCombo(opponentCardCell,myCardCell,cells);
            }else if (command.matches("use special power[(]\\d+,\\d+[)]")) {
                if(!game.getWhoIsHisTurn().heroHaveSpecialPower()) {
                    BattleScreen.showDoesNotHaveSpecialPower();
                }
//                if(!game.getWhoIsHisTurn().useSpecialPower(getCell(command))){
//                    BattleScreen.showNotEnoughMana();
//                }

            }else if (command.matches("show hand")) {
                BattleScreen.showCardArray(game.getWhoIsHisTurn().getHand());
            }else if (command.matches("insert \\w+ in[(]\\d+,\\d+[)]")) {
//                if(!game.getWhoIsHisTurn().moveFromHandToCell(command.split(" ")[1]
//                        ,getCell(command.split(" ")[2]))) {
//                    BattleScreen.showInvalidCardNameError();
//                }
            }else if (command.matches("end turn")) {
                game.getWhoIsHisTurn().setEndTurn(true);
            }else if (command.matches("show collectables")) {
                BattleScreen.showCollectibles(game.getWhoIsHisTurn().getCollectibleItem());
            }else if (command.matches("show info")) {
                if(!BattleScreen.showCard(game.getWhoIsHisTurn().getSelectedItem())) {
                    BattleScreen.showInvalidCardNameError();
                }
            }else if (command.matches("use [(]\\d+,\\d+[)]")) {
                try {
                    game.getWhoIsHisTurn().useCollectibleItem(game.getWhoIsHisTurn().getSelectedItem().getName(), getCell(command).getInsideArmy());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }else if (command.matches("show next card")) {
                BattleScreen.showNextCardFromDeck();
            }else if (command.matches("enter graveyard")) {
                game.getWhoIsHisTurn().goToGraveYard();
            }else if (command.matches("show info [^ ]")) {
                if(game.getWhoIsHisTurn().isInGraveYard())
                    BattleScreen.showCard(game.getWhoIsHisTurn().getGraveYard().find(command.split(" ")[2]));
                else
                    BattleScreen.showErrorYourNotInGraveYard();
            }else if (command.matches("show cards")) {
                if(game.getWhoIsHisTurn().isInGraveYard())
                    BattleScreen.showCardArray(game.getWhoIsHisTurn().getGraveYard());
                else
                    BattleScreen.showErrorYourNotInGraveYard();
            }else if (command.matches("help")) {
                whatYouCanDo(game.getWhoIsHisTurn().getAccount());
            } else if (command.matches("exit")) {
                if(game.getWhoIsHisTurn().isInGraveYard()) {
                    game.getWhoIsHisTurn().exitFromGraveYard();
                }
                else {
                    BattleScreen.showErrorYourNotInGraveYard();
                }
            } else if(command.matches("end game")) {
                game.exitFromGame();
            }
            else {
                BattleScreen.showInvalidCommand();
            }
        }
        return HandlerType.BATTLE;
    }

    public void whatYouCanDo(Account account) {
        CardsArray allArmies = game.getAllAccountArmiesInCellArray(game.getAllCellsInTable(),account);
        for(Card card : allArmies.getAllCards()) {
            BattleScreen.showArmyCanAttackTo((Army)card);
            BattleScreen.showArmyCanGoTo((Army)card);
        }
        BattleScreen.showCardArray(game.getPlayer(account).getHand());
    }

    public Cell getCell(String input) {
        Pattern pattern = Pattern.compile("\\w*[(](\\d+)[,](\\d+)[)]");
        Matcher matcher = pattern.matcher(input);
        if(matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return game.getTable()[x][y];
        }
        return null;
    }

    public void select() {
        if(!game.getWhoIsHisTurn().setSelectedCard(game.findInTable(command.split(" ")[1]))) {
            if (game.getWhoIsHisTurn().getCollectibleItem().find(command.split(" ")[1]) != null) {
                try {
                    Item item = (Item) game.getWhoIsHisTurn().getCollectibleItem().find(command.split(" ")[1]);
                    game.getWhoIsHisTurn().setSelectedItem(item);
                } catch (NullPointerException e) {
                    System.out.println(e);
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                BattleScreen.showInvalidCardIdError();
            }
        }
    }


}
