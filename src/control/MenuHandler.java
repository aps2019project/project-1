package control;

import java.util.regex.*;
public class MenuHandler extends Handler {

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
                        continue;
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
