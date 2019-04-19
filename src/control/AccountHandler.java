package control;

import model.other.Account;
import view.AccountScreen;

class AccountHandler extends Handler {

    @Override
    void handleCommands() {
        while (scanner.hasNext()) {

            command = scanner.nextLine().trim();
            if (command.matches("create account \\w+")) {
                createNewAccount();
            }
        }
    }

    private void createNewAccount() {
        String username = command.split(" ")[2];
        if (Account.doesAccountExist(username)) {
            AccountScreen.showAccountCreationError();
        } else {
            AccountScreen.showScanPassword();
            String password = scanner.nextLine().trim();
            Account account = new Account(username, password);
        }
    }

}
