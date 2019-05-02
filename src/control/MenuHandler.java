package control;

import model.other.Account;
import view.MenuScreen;

import java.util.regex.*;

public class MenuHandler extends Handler {
    private static final MenuHandler menuHandler = new MenuHandler();
    private Account account = Account.getCurrentAccount();

    public static MenuHandler getInstance(){
        return menuHandler;
    }

    MenuHandler(){ }

    @Override
    public void handleCommands() {
        MenuScreen.options();
        while(scanner.hasNext()) {
            command = scanner.nextLine().toLowerCase().trim();
            Pattern pattern = Pattern.compile("(enter (\\w+))|(\\w+)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                switch (matcher.group(0)){
                    case "save":
                        continue;
                    case "logout":
//                        AccountHandler.getInstance().handleCommands();
                    case "exit":
                        System.exit(0);
                    case "help":
                        MenuScreen.options();
                        continue;
                }
                if(matcher.group(2) != null) {
                    switch (matcher.group(2)) {
                        case "collection":
                            continue;
                        case "shop":
                            ShopHandler.getInstance().handleCommands();
                            continue;
                        case "battle":
                            continue;
                    }
                }
            }
            MenuScreen.invalidCommand();
        }
    }
}
