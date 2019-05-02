package control;

public class BattleHandler extends Handler{
    public static BattlesOrderType getPlayingOrder() {
        return null;
    }
    @Override
    void handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().trim();
            if (command.matches("game info")) {
                showGameInfo();
            } else if (command.matches("show my minions")) {
                loginUser();
            } else if (command.matches("show opponent minions")) {
                showLeaderBoard();
            } else if (command.matches("show Card info \\d+")) {
                logout();
            }else if (command.matches("select \\d+")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("move to(\\d+,\\d+)")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("attack \\d+")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("attack combo \\d+( \\d+)+")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("use special power(\\d+,\\d+)")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("show hand")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("insert \\w+ in (\\d+,\\d+)")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("end turn")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("show collectables")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("show info")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("use (\\d+,\\d+)")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("show next card")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("enter graveyard")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("show info \\d+")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("show cards")) {
                AccountScreen.showHelpMenu();
            }else if (command.matches("help")) {
                AccountScreen.showHelpMenu();
            } else {
                AccountScreen.showWrongCommand();
                AccountScreen.showHelpMenu();
            }
        }
    }

}
