package control;

import model.other.Account;
import view.AccountScreen;

class AccountHandler extends Handler {
    private static final AccountHandler accountHandler = new AccountHandler();

    public static AccountHandler getInstance(){
        return accountHandler;
    }

    private AccountHandler(){}

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
            } else if (command.matches("save")) {

            } else if (command.matches("logout")) {

            }else if (command.matches("help")) {

            } else {

            }
        }
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
            String password = scanner.nextLine().trim();
            Account.setCurrentAccount(new Account(username, password));
        }
    }

}
