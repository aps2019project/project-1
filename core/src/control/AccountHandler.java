package control;

import model.other.Account;
import view.AccountScreen;

import static control.HandlerType.MENU;

class AccountHandler extends Handler {

    AccountHandler(){
        AccountScreen.showWelcomeLine();
        AccountScreen.showHelpMenu();
    }

    @Override
    HandlerType handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().trim();
            if (command.matches("create account \\w+")) {
                if (createNewAccount())
                    return MENU;
            } else if (command.matches("login \\w+")) {
                if (loginUser())
                    return MENU;
            } else if (command.matches("show leaderboard")) {
                showLeaderBoard();
            }else if (command.matches("help")) {
                AccountScreen.showHelpMenu();
            } else if (command.matches("exit")) {
                Account.updateAccount();
                System.exit(0);
            } else {
                AccountScreen.showWrongCommand();
                AccountScreen.showHelpMenu();
            }
        }
        return null;
    }

    private void showLeaderBoard() {
        for (int i = 1; i <= Account.getAccounts().size(); ++i) {
            AccountScreen.showAccountDetail(Account.getAccounts().get(i - 1), i);
        }
    }

    private boolean loginUser() {
        String username = command.split(" ")[1];
        if (Account.doesAccountExist(username)) {
            AccountScreen.showScanPassword();
            String password = scanner.nextLine().trim();
            if (Account.checkIfPasswordIsCorrect(username, password)) {
                Account.setCurrentAccount(Account.findAccount(username));

                return true;
            } else {
                AccountScreen.showWrongPassword();
            }
        } else {
            AccountScreen.showWrongUsername();
        }
        return false;
    }

    private boolean createNewAccount() {
        String username = command.split(" ")[2];
        if (Account.doesAccountExist(username)) {
            AccountScreen.showAccountCreationError();
        } else {
            AccountScreen.showScanPassword();
            String password1 = scanner.nextLine().trim();
            AccountScreen.showConfirmPassword();
            String password2 = scanner.nextLine().trim();
            if (password1.equals(password2)) {
                Account.setCurrentAccount(new Account(username, password1));
                return true;
            }
            else
                AccountScreen.showConfirmPasswordFail();
        }
        return false;
    }

}
