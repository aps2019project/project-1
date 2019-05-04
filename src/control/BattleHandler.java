package control;

import model.cards.Card;
import model.game.Cell;
import model.game.Game;
import view.BattleScreen;

import javax.management.BadAttributeValueExpException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BattleHandler extends Handler{
    private static Game game;
    public static BattlesOrderType getPlayingOrder() {
        return null;
    }
    @Override
    void handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().trim();
            if (command.matches("game info")) {
                BattleScreen.showGameInfo();
            } else if (command.matches("show my minions")) {
                BattleScreen.showMinionsOf(game.getWhoIsHisTurn().getAccount());
            } else if (command.matches("show opponent minions")) {
                BattleScreen.showMinionsOf(game.getWhoIsNotHisTurn().getAccount());
            } else if (command.matches("show Card info \\d+")) {
                BattleScreen.showCard(game.findInTable(command.split(" ")[3]).getInsideArmy());
            }else if (command.matches("select \\d+")) {
                if(!game.getWhoIsHisTurn().setSelectedCard(game.findInTable(command.split(" ")[1]))){
                    BattleScreen.showInvalidCardIdError();
                }
            }else if (command.matches("move to(\\d+,\\d+)")) {
                if(!game.getWhoIsHisTurn().moveArmy(game.getWhoIsHisTurn().getSelectedCardPlace()
                        ,getCell(command.split(" ")[1]))) {
                    BattleScreen.showInvalidMoveError();
                }
            }else if (command.matches("attack \\d+")) {
                if(!game.getWhoIsHisTurn().attack(game.findInTable(command.split(" ")[1]))) {
                    BattleScreen.showInvalidAttackError();
                }
            }else if (command.matches("attack combo \\d+( \\d+)+")) {
                //
            }else if (command.matches("use special power(\\d+,\\d+)")) {
                //
            }else if (command.matches("show hand")) {
                BattleScreen.showCardArray(game.getWhoIsHisTurn().getHand());
            }else if (command.matches("insert \\w+ in (\\d+,\\d+)")) {
                if(!game.getWhoIsHisTurn().moveFromHandToCell(command.split(" ")[1]
                        ,getCell(command.split(" ")[3]))) {
                    BattleScreen.showInvalidCardNameError();
                }
            }else if (command.matches("end turn")) {
                game.getWhoIsHisTurn().setEndTurn(true);
            }else if (command.matches("show collectables")) {
                //
            }else if (command.matches("show info")) {
                //
            }else if (command.matches("use (\\d+,\\d+)")) {
                //
            }else if (command.matches("show next card")) {
                //
            }else if (command.matches("enter graveyard")) {
                //
            }else if (command.matches("show info \\d+")) {
                //
            }else if (command.matches("show cards")) {
                //
            }else if (command.matches("help")) {
                //
            } else {
                //
            }
        }
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
}
