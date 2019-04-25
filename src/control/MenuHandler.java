package control;

public class MenuHandler extends Handler {

    @Override
    public void handleCommands() {
        showOptions();
        while (scanner.hasNext()) {
            String command = scanner.nextLine().toLowerCase().trim();
            if (getCommandType(command) == null) {
                System.out.println("Invalid Command");
                continue;
            }
            switch (getCommandType(command)) {
                case COLLECTION:
                    break;
                case SHOP:
                    break;
                case BATTLE:
                    break;
                case SAVE:
                    break;
                case LOGOUT:
                    break;
                case HELP:
                    showOptions();
                    break;
                case EXIT:
                    System.exit(0);
            }
        }
    }

    private void showOptions() {
        for (CommandType i : CommandType.values())
            System.out.println(i);
    }

    private CommandType getCommandType(String string) {
        for (CommandType i : CommandType.values()) {
            if (i.getCommand().equals(string))
                return i;
        }
        return null;
    }

}

enum CommandType {
    COLLECTION("enter collection"),
    SHOP("enter shop"),
    BATTLE("enter battle"),
    SAVE("save"),
    LOGOUT("logout"),
    EXIT("exit"),
    HELP("help");

    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }
}
