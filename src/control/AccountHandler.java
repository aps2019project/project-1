package control;

class AccountHandler extends Handler {

    @Override
    void handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine();
        }
    }

}
