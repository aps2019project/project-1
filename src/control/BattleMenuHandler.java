package control;

import model.other.Account;
import view.BattleScreen;

public class BattleMenuHandler extends Handler{
    private Account account;
    private static PageState pageState = PageState.NOTHING;
    public void goToBattleMenu(Account account) {
        if(!account.getMainDeck().checkIfValid()) {
            BattleScreen.showErrorInvalidDeck();
            exit();
        }
        pageState = PageState.CHOOSE_NUMBER_OF_PLAYERS;
        BattleScreen.showSelectNumberOfPlayerMenu();
    }
    public void gotoSinglePlayerMenu() {
        pageState = PageState.SINGLE_PLAYER_GAME_TYPES;
        BattleScreen.showSinglePlayerMenu();
    }
    public void gotoMultiPlayerMenu() {
        pageState = PageState.MULTI_PLAYER_GAME_TYPES;
        BattleScreen.showModes();
    }
    public void gotoStoryMenu() {

    }
    public void makeFirstStory() {

    }
    public void makeSecondStory() {

    }
    public void makeThirdStory() {

    }
    public void gotoCustomMenu() {

    }
    public void exit() {

    }

    @Override
    HandlerType handleCommands() {
        if(command.matches("\\d")) {
            if(pageState == PageState.CHOOSE_NUMBER_OF_PLAYERS) {
                handleChoosePlayer();
            }
            else if(pageState == PageState.SINGLE_PLAYER_GAME_TYPES) {
                handleSinglePlayer();
            }
            else if(pageState == PageState.MULTI_PLAYER_GAME_TYPES) {
                handleMultiPlayer();
            }
            else if(pageState == PageState.STORY) {
                handleStory();
            }
            else if(pageState == PageState.CUSTOM) {
                handleCustom();
            }

        }
        return HandlerType.BATTLE;
    }

    public void handleChoosePlayer() {
        if(command == "1") {
            gotoSinglePlayerMenu();
        }
        else if(command == "2") {
            gotoMultiPlayerMenu();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }
    public void handleMultiPlayer() {
        if(command == "1") {
            gotoStoryMenu();
        }
        else if(command == "2") {
            gotoCustomMenu();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }
    public void handleSinglePlayer() {
        if(command == "1") {
            gotoStoryMenu();
        }
        else if(command == "2") {
            gotoCustomMenu();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }
    public void handleStory() {
        if(command == "1") {
            makeFirstStory();
        }
        else if(command == "2") {
            makeSecondStory();
        }
        else if(command == "3") {
            makeThirdStory();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }
    public void handleCustom() {
        if(command == "1") {
            gotoSinglePlayerMenu();
        }
        else if(command == "2") {
            gotoMultiPlayerMenu();
        }
        else if(command == "3") {
            gotoMultiPlayerMenu();
        }
        else {
            BattleScreen.showInvalidCommand();
        }
    }
}
enum PageState {
    CHOOSE_NUMBER_OF_PLAYERS,
    SINGLE_PLAYER_GAME_TYPES,
    MULTI_PLAYER_GAME_TYPES,
    STORY,
    CUSTOM,
    NOTHING
}
