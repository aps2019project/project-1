package control;

import model.cards.Card;
import model.cards.Hero;
import model.game.*;
import model.other.Account;
import view.AccountScreen;
import view.BattleScreen;

import java.util.ArrayList;

import static control.HandlerType.MENU;

public class BattleMenuHandler extends Handler{
    private Account account = Account.getCurrentAccount();
    private Account secondAccount;
    private GameType type;
    private IntelligentPlayer firstLevelPlayer;
    private IntelligentPlayer secondLevelPlayer;
    private IntelligentPlayer thirdLevelPlayer;
    private IntelligentPlayer customPlayer;
    private ArrayList<Deck> customDecks = new ArrayList<>();
    private Hero hero;

    @Override
    HandlerType handleCommands() {
        if(!account.getMainDeck().checkIfValid()) {
            BattleScreen.showErrorInvalidDeck();
            return MENU;
        }
        setPlayersSteps();
        gotoChoosePlayerPage();
        while (scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            System.out.println(pageState);
            if(command.matches("\\d")) {
                if (pageState == PageState.CHOOSE_NUMBER_OF_PLAYERS) {
                    handleChoosePlayer();
                } else if (pageState == PageState.SINGLE_PLAYER_GAME_TYPES) {
                    handleSinglePlayer();
                } else if (pageState == PageState.MULTI_PLAYER_GAME_TYPES_FIRST) {
                    handleMultiPlayerFirstMenu();
                } else if (pageState == PageState.MULTI_PLAYER_GAME_TYPES_SECOND) {
                    handleMultiPlayerSecondMenu();
                } else if (pageState == PageState.STORY) {
                    handleStory();
                } else if (pageState == PageState.CUSTOM_FIRST) {
                    handleCustomFirstPage();
                } else if (pageState == PageState.CUSTOM_SECOND) {
                    handleCustomSecondPage();
                }
            } else if (command.matches("exit")) {
                if (pageState == PageState.CHOOSE_NUMBER_OF_PLAYERS) {

                    return MENU;
                } else if (pageState == PageState.SINGLE_PLAYER_GAME_TYPES) {
                    gotoChoosePlayerPage();
                } else if (pageState == PageState.MULTI_PLAYER_GAME_TYPES_FIRST) {
                    gotoChoosePlayerPage();
                } else if (pageState == PageState.MULTI_PLAYER_GAME_TYPES_SECOND) {
                    gotoMultiPlayerFirstMenu();
                } else if (pageState == PageState.STORY) {
                    gotoSinglePlayerMenu();
                } else if (pageState == PageState.CUSTOM_FIRST) {
                    gotoSinglePlayerMenu();
                } else if (pageState == PageState.CUSTOM_SECOND) {
                    gotoCustomMenuFirstPage();
                }
            }
        }
        return null;
    }

    public void setCustomDecks() {
        //
    }

    public void setPlayersSteps() {
        //customPlayer = new IntelligentPlayer(new Account("customPlayer","1234"));
        Account account = new Account("firstLevelPlayer","1234");
        try {
            Card.makeStroyDeck(1, account);
        } catch (Exception e){}
        firstLevelPlayer = new IntelligentPlayer(account);
        account = new Account("secondLevelPlayer","1234");
        try {
            Card.makeStroyDeck(2, account);
        } catch (Exception e){}
        secondLevelPlayer = new IntelligentPlayer(account);
        account = new Account("thirdLevelPlayer","1234");
        try {
            Card.makeStroyDeck(3, account);
        } catch (Exception e){}
        thirdLevelPlayer = new IntelligentPlayer(account);
    }

    private static PageState pageState = PageState.NOTHING;


    private  void gotoChoosePlayerPage() {
        pageState = PageState.CHOOSE_NUMBER_OF_PLAYERS;
        BattleScreen.showSelectNumberOfPlayerMenu();

    }
    private void gotoSinglePlayerMenu() {
        pageState = PageState.SINGLE_PLAYER_GAME_TYPES;
        BattleScreen.showSinglePlayerMenu();
    }

    private void gotoMultiPlayerFirstMenu() {
        pageState = PageState.MULTI_PLAYER_GAME_TYPES_FIRST;
        showAllAccounts();
    }

    private void gotoMultiPlayerSecondMenu() {
        pageState = PageState.MULTI_PLAYER_GAME_TYPES_SECOND;
        BattleScreen.showModes();
    }

    private void gotoStoryMenu() {
        pageState = PageState.STORY;
        BattleScreen.showStoryMenu();
    }

    private void makeFirstStory() {
        type = GameType.KILL_HERO;
        playGame(0,firstLevelPlayer);
    }

    private void makeSecondStory() {
        type = GameType.CAPTURE_THE_FLAG;
        playGame(1,secondLevelPlayer);
    }

    private void makeThirdStory() {
        type = GameType.ROLLUP_FLAGS;
        playGame(3,thirdLevelPlayer);
    }

    private void gotoCustomMenuFirstPage() {
        pageState = PageState.CUSTOM_FIRST;
        BattleScreen.showCustomMenuFirstPage();
    }

    private void gotoCustomMenuSecondPage() {
        pageState = PageState.CUSTOM_SECOND;
        BattleScreen.decks(customDecks);
    }

    private void showAllAccounts() {
        for (int i = 0; i < Account.getAccounts().size(); i++) {
            if (Account.getAccounts().get(i).equals(Account.getCurrentAccount()))
                continue;
            AccountScreen.showAccountDetail(Account.getAccounts().get(i), i + 1);
        }
    }

    private void playGame(int numberOfFlags) {
        if(!secondAccount.getMainDeck().checkIfValid()) {
            BattleScreen.showInvalidDeckInMultiPlayer();
            return;
        }
        Game game = new Game(account,secondAccount,type,numberOfFlags);
        game.startMatch();
        MatchResult result = game.getResults();
        //set reward
    }

    private void playGame(int numberOfFlags,IntelligentPlayer player) {
        Game game = new Game(account,player,type,numberOfFlags);
        game.startMatch();
        MatchResult result = game.getResults();
    }

    private void handleChoosePlayer() {
        if(command.equals("1")) {
            gotoSinglePlayerMenu();
        }
        else if(command.equals("2")) {
            gotoMultiPlayerFirstMenu();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }

    private void handleMultiPlayerFirstMenu() {
        if(command.matches("select user \\w+")) {
            secondAccount = Account.findAccount(command.split(" ")[2]);
            if(secondAccount == null) BattleScreen.showInvalidAccountUserName();
            gotoMultiPlayerSecondMenu();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }

    private void handleMultiPlayerSecondMenu() {
        if(command.matches("start multiplayer game 1")) {
            type = GameType.KILL_HERO;
            playGame(0);
        }
        else if(command.matches("start multiplayer game 2")) {
            type = GameType.CAPTURE_THE_FLAG;
            playGame(1);
        }
        else if(command.matches("start multiplayer game 3 \\d+")) {
            type = GameType.ROLLUP_FLAGS;
            playGame(Integer.parseInt(command.split(" ")[4]));
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }

    private void handleSinglePlayer() {
        if(command.equals("1")) {
            gotoStoryMenu();
        }
        else if(command.equals("2")) {
            gotoCustomMenuFirstPage();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }

    private void handleStory() {
        if(command.equals("1")) {
            makeFirstStory();
        }
        else if(command.equals("2")) {
            makeSecondStory();
        }
        else if(command.equals("3")) {
            makeThirdStory();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }

    private void handleCustomFirstPage() {
        if(command.matches("\\d+")) {
            try {
                hero = (Hero) Hero.getHeroes().get(Integer.parseInt(command)).clone();
                gotoCustomMenuSecondPage();
            }
            catch(Exception e) {
                BattleScreen.showInvalidClone();
            }
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }

    private void handleCustomSecondPage() {
        if(command.matches("start game \\d+ 1")) {
            type = GameType.KILL_HERO;
            customPlayer.setDeck(customDecks.get(Integer.parseInt(command.split(" ")[2])));
            playGame(0,customPlayer);
        }
        else if(command.matches("start game \\d+ 2")) {
            type = GameType.CAPTURE_THE_FLAG;
            customPlayer.setDeck(customDecks.get(Integer.parseInt(command.split(" ")[2])));
            playGame(1,customPlayer);
        }
        else if(command.matches("start game \\d+ 3 \\d+")) {
            type = GameType.ROLLUP_FLAGS;
            customPlayer.setDeck(customDecks.get(Integer.parseInt(command.split(" ")[2])));
            playGame(Integer.parseInt(command.split(" ")[4]),customPlayer);
        }

        else {
            BattleScreen.showInvalidCommand();
        }
    }

}
enum PageState {
    CHOOSE_NUMBER_OF_PLAYERS,
    SINGLE_PLAYER_GAME_TYPES,
    MULTI_PLAYER_GAME_TYPES_FIRST,
    MULTI_PLAYER_GAME_TYPES_SECOND,
    STORY,
    CUSTOM_FIRST,
    CUSTOM_SECOND,
    NOTHING,
}
