package control;

import model.other.Account;
import java.util.regex.*;

public class MenuHandler extends Handler {
    private static final MenuHandler menuHandler = new MenuHandler();
    private Account account = Account.getCurrentAccount();

    public static MenuHandler getInstance(){
        return menuHandler;
    }

    private MenuHandler(){ }

    @Override
    public void handleCommands() {
        showOptions();
        while(scanner.hasNext()) {
            String command = scanner.nextLine().toLowerCase().trim();
            Pattern pattern = Pattern.compile("(enter (\\w+))|(\\w+)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                switch (matcher.group(0)){
                    case "save":
                        continue;
                    case "logout":
                        AccountHandler.getInstance().handleCommands();
                    case "exit":
                        System.exit(0);
                    case "help":
                        showOptions();
                        continue;
                }
                if(matcher.group(2) != null) {
                    switch (matcher.group(2)) {
                        case "collection":
                            continue;
                        case "shop":
                            continue;
                        case "battle":
                            continue;
                    }
                }
            }
            System.out.println("Invalid command");
        }
    }

    private void showOptions() {
        System.out.println("Collection\nShop\nBattle\nSave\nLogout\nExit\nHelp");
    }
}
