package control;

import model.other.Account;
import view.AccountScreen;

class AccountHandler extends Handler {

    AccountHandler(){
        AccountScreen.showWelcomeLine();
        AccountScreen.showHelpMenu();
        handleCommands();
    }

    @Override
    void handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().trim();
            if (command.matches("create account \\w+")) {
                createNewAccount();
            } else if (command.matches("login \\w+")) {
                loginUser();
            } else if (command.matches("show leaderboard")) {
                showLeaderBoard();
            } else if (command.matches("logout")) {
                logout();
            }else if (command.matches("help")) {
                AccountScreen.showHelpMenu();
            } else {
                AccountScreen.showWrongCommand();
                AccountScreen.showHelpMenu();
            }
        }
    }

    private void logout() {
        if (Account.getCurrentAccount() == null)
            return;
        Account.setCurrentAccount(null);
        AccountScreen.showLogoutConfirm();
        Account.saveAccountDetails();
    }

    private void showLeaderBoard() {
        for (int i = 1; i <= Account.getAccounts().size(); ++i) {
            AccountScreen.showAccountDetail(Account.getAccounts().get(i - 1), i);
        }
    }

    private void loginUser() {
        String username = command.split(" ")[1];
        if (Account.doesAccountExist(username)) {
            AccountScreen.showScanPassword();
            String password = scanner.nextLine().trim();
            if (Account.checkIfPasswordIsCorrect(username, password)) {
                Account.setCurrentAccount(Account.findAccount(username));

                new MenuHandler();
            } else {
                AccountScreen.showWrongPassword();
            }
        } else {
            AccountScreen.showWrongUsername();
        }
    }

    private void createNewAccount() {
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
                new MenuHandler();
            }
            else
                AccountScreen.showConfirmPasswordFail();
        }
    }

}
